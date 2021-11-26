package otus.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BookRestController {

    @Bean
    public RouterFunction<ServerResponse> bookComposedRoutes(AuthorRepository authorRepository, BookRepository bookRepository, GenreRepository genreRepository) {
        final var bookHandler = new BookHandler(authorRepository, bookRepository, genreRepository);
        return route()
                .GET("/api/book", accept(APPLICATION_JSON), bookHandler::list)
                .POST("/api/book", accept(APPLICATION_JSON), bookHandler::save)
                .DELETE("/api/book/{id}", accept(APPLICATION_JSON), bookHandler::delete)
                .build();
    }

    @RequiredArgsConstructor
    static class BookHandler {

        private final AuthorRepository authorRepository;

        private final BookRepository bookRepository;

        private final GenreRepository genreRepository;

        Mono<ServerResponse> list(ServerRequest request) {
            log.debug("List of books...");
            return ok().contentType(APPLICATION_JSON).body(bookRepository.findAll().map(BookDto::toDto), BookDto.class);
        }

        Mono<ServerResponse> save(ServerRequest request) {
            log.info("Going to save book...");
            final var newBook = new Book();
            return request.body(toMono(Book.class))
                    .map(book -> {
                        newBook.setTitle(book.getTitle());
                        return book;
                    })
                    .flatMap(book -> authorRepository.findByName(book.getAuthor().getName())
                            .zipWith(genreRepository.findByGenreName(book.getGenre().getGenreName())))
                    .map(tuple -> {
                        newBook.setAuthor(tuple.getT1());
                        newBook.setGenre(tuple.getT2());
                        return newBook;
                    })
                    .flatMap(book -> ok().contentType(APPLICATION_JSON).body(
                            bookRepository.save(book).map(BookDto::toDto),
                            BookDto.class));
        }

        Mono<ServerResponse> delete(ServerRequest request) {
            log.info("Going to delete book...");
            return bookRepository.deleteById(request.pathVariable("id"))
                    .flatMap(aVoid -> ok().contentType(APPLICATION_JSON).body(fromValue(aVoid)));
        }
    }
}
