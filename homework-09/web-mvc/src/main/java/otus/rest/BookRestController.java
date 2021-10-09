package otus.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import otus.domain.Book;
import otus.repository.AuthorRepository;
import otus.repository.BookRepository;
import otus.repository.GenreRepository;
import otus.rest.dto.BookDto;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class BookRestController {

    @Bean
    public RouterFunction<ServerResponse> bookComposedRoutes(AuthorRepository authorRepository, BookRepository bookRepository, GenreRepository genreRepository) {
        final var bookHandler = new BookHandler(bookRepository);
        return route()
                .GET("/api/book", accept(APPLICATION_JSON), bookHandler::list)
                .POST("/api/book", accept(APPLICATION_JSON), bookHandler::save)
                .DELETE("/api/book/{id}", accept(APPLICATION_JSON), bookHandler::delete)
                .build();
    }

    @RequiredArgsConstructor
    static class BookHandler {

        private final BookRepository bookRepository;

        Mono<ServerResponse> list(ServerRequest request) {
            return ok().contentType(APPLICATION_JSON).body(bookRepository.findAll().map(BookDto::toDto), BookDto.class);
        }

        Mono<ServerResponse> save(ServerRequest request) {
            final var bookMono = request.body(toMono(Book.class));
            return bookMono.flatMap(book -> ok().contentType(APPLICATION_JSON).body(bookRepository.save(book).map(BookDto::toDto), BookDto.class));
        }

        Mono<ServerResponse> delete(ServerRequest request) {
            return bookRepository.deleteById(request.pathVariable("id"))
                    .flatMap(aVoid -> ok().contentType(APPLICATION_JSON).body(fromValue(aVoid)));
        }
    }
}
