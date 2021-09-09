package otus.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import otus.rest.dto.AuthorDto;
import otus.rest.dto.GenreDto;
import otus.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreRestController {

    private final GenreService genreService;

    @GetMapping("/api/genre/list")
    public List<GenreDto> getAllGenres() {
        return genreService.getGenres().stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/api/genre/delete/{id}")
    public void deleteGenre(@PathVariable("id") long id) {
        genreService.deleteGenre(id);
    }
}
