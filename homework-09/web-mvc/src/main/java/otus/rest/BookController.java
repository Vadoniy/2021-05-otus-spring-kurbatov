package otus.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import otus.domain.Book;
import otus.exception.UnknownAuthorException;
import otus.exception.UnknownBookException;
import otus.exception.UnknownGenreException;
import otus.service.AuthorService;
import otus.service.BookService;
import otus.service.GenreService;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "book")
public class BookController {

    private final AuthorService authorService;

    private final BookService bookService;

    private final GenreService genreService;

    @GetMapping("/list")
    public String getBooksList(Model model) {
        final var allBooks = bookService.getBooks();
        model.addAttribute("books", allBooks);
        return "/book/allBooks";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") long id, Model model) {
        final var bookToEdit = bookService.getBookById(id)
                .orElseThrow(() -> new UnknownBookException("No book with id " + id));
        final var allAuthors = authorService.getAuthors();
        final var allGenres = genreService.getGenres();
        model.addAttribute("book", bookToEdit);
        model.addAttribute("authors", allAuthors);
        model.addAttribute("genres", allGenres);
        return "/book/editBook";
    }

    @GetMapping("/new")
    public String addBook(Model model) {
        final var book = new Book();
        final var allAuthors = authorService.getAuthors();
        final var allGenres = genreService.getGenres();
        model.addAttribute("authors", allAuthors);
        model.addAttribute("genres", allGenres);
        model.addAttribute("book", book);
        return "/book/addBook";
    }

    @DeleteMapping("/delete")
    public String deleteBook(@RequestParam("id") long id, Model model) {
        bookService.deleteBook(id);
        final var allBooks = bookService.getBooks();
        model.addAttribute("books", allBooks);
        return "redirect:" + "/book/list";
    }

    @PostMapping("/save")
    public String saveBook(Book book, Model model) {
        final var author = authorService.getAuthorById(book.getAuthor().getId())
                .orElseThrow(() -> new UnknownAuthorException("There is no author with id " + book.getAuthor().getId()));
        final var genre = genreService.getGenreById(book.getGenre().getId())
                .orElseThrow(() -> new UnknownGenreException("There is no genre with id " + book.getGenre().getId()));
        book.setAuthor(author);
        book.setGenre(genre);
        final var savedBook = bookService.saveBook(book);
        final var allBooks = bookService.getBooks();
        model.addAttribute(savedBook);
        model.addAttribute("books", allBooks);
        return "/book/allBooks";
    }
}
