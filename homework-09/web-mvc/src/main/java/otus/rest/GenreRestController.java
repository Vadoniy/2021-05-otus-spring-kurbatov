package otus.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import otus.domain.Genre;
import otus.repository.BookRepository;
import otus.repository.GenreRepository;
import otus.rest.dto.GenreDto;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Slf4j
public class GenreRestController {

    @Bean
    public RouterFunction<ServerResponse> genreComposedRoutes(GenreRepository genreRepository, BookRepository bookRepository) {
        final var genreHandler = new GenreHandler(bookRepository, genreRepository);
        return route()
                .GET("/api/genre", accept(APPLICATION_JSON), genreHandler::list)
                .POST("/api/genre", accept(APPLICATION_JSON), genreHandler::save)
                .DELETE("/api/genre/{id}", accept(APPLICATION_JSON), genreHandler::delete)
                .build();
    }

    @RequiredArgsConstructor
    static class GenreHandler {

        private final BookRepository bookRepository;

        private final GenreRepository genreRepository;

        Mono<ServerResponse> list(ServerRequest request) {
            log.debug("List of genres...");
            return ok().contentType(APPLICATION_JSON).body(genreRepository.findAll().map(GenreDto::toDto), GenreDto.class);
        }

        Mono<ServerResponse> save(ServerRequest request) {
            log.info("Going to save genre...");
            final var genreMono = request.body(toMono(Genre.class));
            return genreMono.flatMap(genre -> ok().contentType(APPLICATION_JSON).body(genreRepository.save(genre).map(GenreDto::toDto), GenreDto.class));
        }

        Mono<ServerResponse> delete(ServerRequest request) {
            log.info("Going to delete genre...");
            return genreRepository.deleteById(request.pathVariable("id"))
                    .then(bookRepository.deleteByGenreId(request.pathVariable("id")))
                    .flatMap(aVoid -> ok().contentType(APPLICATION_JSON)
                            .body(fromValue(aVoid)));
        }
    }
}
