package otus.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import otus.domain.Author;
import otus.service.AuthorService;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/author")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/list")
    public String getAuthorsList(Model model) {
        final var allAuthors = authorService.getAuthors();
        model.addAttribute("authors", allAuthors);
        return "/author/allAuthors";
    }

    @DeleteMapping("/delete")
    public String deleteAuthor(@RequestParam("id") long id, Model model) {
        authorService.deleteAuthor(id);
        final var allAuthors = authorService.getAuthors();
        model.addAttribute("authors", allAuthors);
        return "redirect:" + "/author/list";
    }

    @GetMapping("/new")
    public String addAuthor(Model model) {
        final var author = new Author();
        model.addAttribute("author", author);
        return "/author/addAuthor";
    }

    @PostMapping("/save")
    public String addAuthor(Author author, Model model) {
        authorService.addNewAuthor(author);
        final var allAuthors = authorService.getAuthors();
        model.addAttribute("authors", allAuthors);
        return "/author/allAuthors";
    }
}
