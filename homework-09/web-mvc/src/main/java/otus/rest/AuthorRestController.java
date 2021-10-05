package otus.rest;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import otus.domain.Author;
import otus.repository.AuthorRepository;
import otus.rest.dto.AuthorDto;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class AuthorRestController {

    @Bean
    public RouterFunction<ServerResponse> authorComposedRoutes(AuthorRepository authorRepository) {
        final var bookHandler = new AuthorHandler(authorRepository);
        return route()
                .GET("/api/author", accept(APPLICATION_JSON),
                        bookHandler::list)
                .POST("/api/author", accept(APPLICATION_JSON),
                        bookHandler::save)
                .DELETE("/api/author/{id}", accept(APPLICATION_JSON),
                        bookHandler::delete)
                .build();
    }

    @AllArgsConstructor
    static class AuthorHandler {

        private final AuthorRepository authorRepository;

        Mono<ServerResponse> list(ServerRequest request) {
            return ok().contentType(APPLICATION_JSON).body(authorRepository.findAll().map(AuthorDto::toDto), AuthorDto.class);
        }

        Mono<ServerResponse> save(ServerRequest request) {
            final var authorMono = request.body(toMono(Author.class));
            return authorMono.flatMap(
                    author -> ok().contentType(APPLICATION_JSON)
                            .body(authorRepository.save(author).map(AuthorDto::toDto), AuthorDto.class)
            );
        }

        Mono<ServerResponse> delete(ServerRequest request) {
            return ok().contentType(APPLICATION_JSON)
                    .body(authorRepository.deleteById(request.pathVariable("id")), Author.class);
        }
    }
}