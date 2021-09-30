package otus.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import otus.domain.Genre;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class GenreController {

    @GetMapping("/genre")
    public String getGenresList() {
        return "/genre/allGenres";
    }

    @PostMapping("/genre")
    public String addGenre(Model model) {
        final var genre = new Genre();
        model.addAttribute("genre", genre);
        return "/genre/addGenre";
    }
}
