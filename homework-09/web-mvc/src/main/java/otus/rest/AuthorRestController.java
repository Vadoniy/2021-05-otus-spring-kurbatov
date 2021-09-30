package otus.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otus.domain.Author;
import otus.repository.AuthorRepository;
import otus.rest.dto.AuthorDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {

    private final AuthorRepository authorRepository;

    @GetMapping("/api/author")
    public Flux<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .map(AuthorDto::toDto);
    }

    @DeleteMapping("/api/author/{id}")
    public Mono<Void> deleteAuthor(@PathVariable("id") String id) {
        return authorRepository.deleteById(id);
    }

    @PostMapping(value = "/api/author", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAuthor(@RequestBody Author author) {
        authorRepository.save(author)
                .subscribe();
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
