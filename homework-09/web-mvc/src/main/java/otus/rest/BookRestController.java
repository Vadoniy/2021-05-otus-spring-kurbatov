package otus.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import otus.rest.dto.BookDto;
import otus.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/api/book/list")
    public List<BookDto> getAllBooks() {
        return bookService.getBooks().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/api/book/delete/{id}")
    public void deleteAuthor(@PathVariable("id") long id) {
        bookService.deleteBook(id);
    }
}
