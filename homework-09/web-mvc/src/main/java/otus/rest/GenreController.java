package otus.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import otus.domain.Author;
import otus.domain.Genre;
import otus.service.AuthorService;
import otus.service.GenreService;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/genre")
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/list")
    public String getGenresList(Model model) {
        final var allGenres = genreService.getGenres();
        model.addAttribute("genres", allGenres);
        return "/genre/allGenres";
    }

    @DeleteMapping("/delete")
    public String deleteGenre(@RequestParam("id") long id, Model model) {
        genreService.deleteGenre(id);
        final var allGenres = genreService.getGenres();
        model.addAttribute("genres", allGenres);
        return "redirect:" + "/genre/list";
    }

    @GetMapping("/new")
    public String addGenre(Model model) {
        final var genre = new Genre();
        model.addAttribute("genre", genre);
        return "/genre/addGenre";
    }

    @PostMapping("/save")
    public String addGenre(Genre genre, Model model) {
        genreService.addNewGenre(genre);
        final var allGenres = genreService.getGenres();
        model.addAttribute("genres", allGenres);
        return "/genre/allGenres";
    }
}
