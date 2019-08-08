package hello;

import hello.service.BlogService;
import hello.dao.entity.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.URI;

import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import reactor.core.publisher.Mono;

@Component
public class GreetingHandler {

	@Autowired
	private BlogService blogService;

	public Mono<ServerResponse> createBlog(ServerRequest request) {
		return request.bodyToMono(Blog.class) //
				.flatMap(blogService::createBlog) //
				.flatMap(saved -> created(URI.create("/api/v1/blog/" + saved.getId()))
						.build());
	}
}
