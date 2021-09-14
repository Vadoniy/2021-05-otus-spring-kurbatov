package otus.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import otus.domain.Book;
import otus.exception.UnknownBookException;
import otus.service.AuthorService;
import otus.service.BookService;
import otus.service.GenreService;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class BookController {

    private final AuthorService authorService;

    private final BookService bookService;

    private final GenreService genreService;

    @GetMapping("/book")
    public String getBooksList(Model model) {
        final var allBooks = bookService.getBooks();
        model.addAttribute("books", allBooks);
        return "/book/allBooks";
    }

    @GetMapping("/book/{id}")
    public String editBook(@PathVariable("id") long id, Model model) {
        final var bookToEdit = bookService.getBookById(id)
                .orElseThrow(() -> new UnknownBookException("No book with id " + id));
        final var allAuthors = authorService.getAuthors();
        final var allGenres = genreService.getGenres();
        model.addAttribute("book", bookToEdit);
        model.addAttribute("authors", allAuthors);
        model.addAttribute("genres", allGenres);
        return "/book/editBook";
    }

    @PostMapping("/book")
    public String addBook(Model model) {
        final var book = new Book();
        final var allAuthors = authorService.getAuthors();
        final var allGenres = genreService.getGenres();
        model.addAttribute("authors", allAuthors);
        model.addAttribute("genres", allGenres);
        model.addAttribute("book", book);
        return "/book/addBook";
    }
}
