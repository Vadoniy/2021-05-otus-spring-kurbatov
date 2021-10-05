package otus.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import otus.domain.Genre;
import otus.repository.GenreRepository;
import otus.rest.dto.GenreDto;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class GenreRestController {

    @Bean
    public RouterFunction<ServerResponse> genreComposedRoutes(GenreRepository genreRepository) {
        final var genreHandler = new GenreHandler(genreRepository);
        return route()
                .GET("/api/genre", accept(APPLICATION_JSON), genreHandler::list)
                .POST("/api/genre", accept(APPLICATION_JSON), genreHandler::save)
                .DELETE("/api/genre/{id}", accept(APPLICATION_JSON), genreHandler::delete)
                .build();
    }

    @RequiredArgsConstructor
    static class GenreHandler {

        private final GenreRepository genreRepository;

        Mono<ServerResponse> list(ServerRequest request) {
            return ok().contentType(APPLICATION_JSON).body(genreRepository.findAll().map(GenreDto::toDto), GenreDto.class);
        }

        Mono<ServerResponse> save(ServerRequest request) {
            final var genreMono = request.body(toMono(Genre.class));
            return genreMono.flatMap(genre -> ok().contentType(APPLICATION_JSON).body(genreRepository.save(genre).map(GenreDto::toDto), GenreDto.class));
        }

        Mono<ServerResponse> delete(ServerRequest request) {
            return ok().contentType(APPLICATION_JSON).body(genreRepository.deleteById(request.pathVariable("id")), GenreDto.class);
        }
    }
}
