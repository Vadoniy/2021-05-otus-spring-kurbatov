package otus.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otus.domain.Genre;
import otus.repository.GenreRepository;
import otus.rest.dto.GenreDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class GenreRestController {

    private final GenreRepository genreRepository;

    @GetMapping("/api/genre")
    public Flux<GenreDto> getAllGenres() {
        return genreRepository.findAll()
                .map(GenreDto::toDto);
    }

    @DeleteMapping("/api/genre/{id}")
    public Mono<Void> deleteGenre(@PathVariable("id") String id) {
        return genreRepository.deleteById(id);
    }

    @PostMapping(value = "/api/genre", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveGenre(@RequestBody Genre genre) {
        genreRepository.save(genre).subscribe();;
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
