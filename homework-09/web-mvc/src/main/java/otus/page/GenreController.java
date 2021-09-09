package otus.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import otus.domain.Genre;
import otus.service.GenreService;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genre/list")
    public String getGenresList(Model model) {
        final var allGenres = genreService.getGenres();
        model.addAttribute("genres", allGenres);
        return "/genre/allGenres";
    }

    @GetMapping("/genre/new")
    public String addGenre(Model model) {
        final var genre = new Genre();
        model.addAttribute("genre", genre);
        return "/genre/addGenre";
    }

    @PostMapping("/genre/save")
    public String addGenre(Genre genre) {
        genreService.addNewGenre(genre);
        return "redirect:" + "/genre/list";
    }
}
