package otus.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otus.domain.Book;
import otus.exception.UnknownAuthorException;
import otus.exception.UnknownGenreException;
import otus.repository.AuthorRepository;
import otus.repository.BookRepository;
import otus.repository.GenreRepository;
import otus.rest.dto.BookDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    @GetMapping("/api/book")
    public Flux<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .map(BookDto::toDto);
    }

    @DeleteMapping("/api/book/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
        return bookRepository.deleteById(id);
    }

    @PostMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveBook(@RequestBody Book book) {
        final var author = authorRepository.findById(book.getAuthor().getId()).blockOptional()
                .orElseThrow(() -> new UnknownAuthorException("There is no author with id " + book.getAuthor().getId()));
        final var genre = genreRepository.findById(book.getGenre().getId()).blockOptional()
                .orElseThrow(() -> new UnknownGenreException("There is no genre with id " + book.getGenre().getId()));
        book.setAuthor(author);
        book.setGenre(genre);
        bookRepository.save(book).subscribe();;
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
