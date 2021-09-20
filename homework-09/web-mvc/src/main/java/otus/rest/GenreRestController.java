package otus.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otus.domain.Genre;
import otus.rest.dto.GenreDto;
import otus.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreRestController {

    private final GenreService genreService;

    @GetMapping("/api/genre")
    public List<GenreDto> getAllGenres() {
        return genreService.getGenres().stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/api/genre/{id}")
    public void deleteGenre(@PathVariable("id") long id) {
        genreService.deleteGenre(id);
    }

    @PostMapping(value = "/api/genre", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAuthor(@RequestBody Genre genre) {
        genreService.addNewGenre(genre);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
