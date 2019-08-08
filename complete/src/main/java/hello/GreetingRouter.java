package hello;

import hello.service.BlogService;
import hello.dao.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.web.reactive.function.server.RouterFunction;

import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class GreetingRouter {

	@Autowired
	private BlogService blogService;


/*	@Bean
	RouterFunction<ServerResponse> getAllEmployeesRoute() {
		return route(GET("/api/v1/blog"),
				req -> ok().body(
						blogService.findAll(), Blog.class));
	}*/


	@Bean
	RouterFunction<ServerResponse> updateEmployeeRoute() {
		return route(POST("/api/v1/blog"),
				req -> req.body(toMono(Blog.class))
						.flatMap(blogService::createBlog)
						.then(ok().build()));
	}

	@Bean
	RouterFunction<ServerResponse> createBlogRouter(GreetingHandler greetingHandler) {
		return route(POST("/api/v1/blog"),
				greetingHandler::createBlog);
	}

	/*@Bean
	public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {

		return RouterFunctions
			.route(RequestPredicates.POST("/api/v1/blog").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), greetingHandler::hello);
	}*/
}
