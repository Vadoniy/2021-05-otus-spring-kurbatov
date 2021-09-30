package otus.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import otus.domain.Book;
import otus.repository.AuthorRepository;
import otus.repository.BookRepository;
import otus.repository.GenreRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class BookController {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    @GetMapping("/book")
    public String getBooksList(Model model) {
        final var allBooks = bookRepository.findAll()
                .subscribe(book -> model.addAttribute("books", book));
        model.addAttribute("books", allBooks);
        return "/book/allBooks";
    }

    @GetMapping("/book/{id}")
    public String editBook(@PathVariable("id") String id, Model model) {
        final var bookToEdit = bookRepository.findById(id);
        final var allAuthors = authorRepository.findAll();
        final var allGenres = genreRepository.findAll();
        model.addAttribute("book", bookToEdit);
        model.addAttribute("authors", allAuthors);
        model.addAttribute("genres", allGenres);
        return "/book/editBook";
    }

    @PostMapping("/book")
    public String addBook(Model model) {
        final var book = new Book();
        final var allAuthors = authorRepository.findAll();
        final var allGenres = genreRepository.findAll();
        model.addAttribute("authors", allAuthors);
        model.addAttribute("genres", allGenres);
        model.addAttribute("book", book);
        return "/book/addBook";
    }
}
