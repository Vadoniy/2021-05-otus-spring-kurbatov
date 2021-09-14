package otus.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otus.domain.Book;
import otus.exception.UnknownAuthorException;
import otus.exception.UnknownGenreException;
import otus.rest.dto.BookDto;
import otus.service.AuthorService;
import otus.service.BookService;
import otus.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final AuthorService authorService;

    private final BookService bookService;

    private final GenreService genreService;

    @GetMapping("/api/book")
    public List<BookDto> getAllBooks() {
        return bookService.getBooks().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/api/book/{id}")
    public void deleteBook(@PathVariable("id") long id) {
        bookService.deleteBook(id);
    }

    @PostMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveBook(@RequestBody Book book) {
        final var author = authorService.getAuthorById(book.getAuthor().getId())
                .orElseThrow(() -> new UnknownAuthorException("There is no author with id " + book.getAuthor().getId()));
        final var genre = genreService.getGenreById(book.getGenre().getId())
                .orElseThrow(() -> new UnknownGenreException("There is no genre with id " + book.getGenre().getId()));
        book.setAuthor(author);
        book.setGenre(genre);
        bookService.saveBook(book);
        return ResponseEntity.ok(HttpStatus.MOVED_PERMANENTLY);
    }
}
