package otus.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import otus.domain.Author;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class AuthorController {

    @GetMapping("/author")
    public String getAuthorsList() {
        return "/author/allAuthors";
    }

    @PostMapping("/author")
    public String addAuthor(Model model) {
        final var author = new Author();
        model.addAttribute("author", author);
        return "/author/addAuthor";
    }
}
