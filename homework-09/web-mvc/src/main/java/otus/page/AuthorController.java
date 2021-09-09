package otus.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import otus.domain.Author;
import otus.service.AuthorService;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author/list")
    public String getAuthorsList() {
        return "/author/allAuthors";
    }

    @GetMapping("/author/new")
    public String addAuthor(Model model) {
        final var author = new Author();
        model.addAttribute("author", author);
        return "/author/addAuthor";
    }

    @PostMapping("/author/save")
    public String addAuthor(Author author) {
        authorService.addNewAuthor(author);
        return "redirect:" + "/author/list";
    }
}
