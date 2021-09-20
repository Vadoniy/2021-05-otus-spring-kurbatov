package otus.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otus.domain.Author;
import otus.rest.dto.AuthorDto;
import otus.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {

    private final AuthorService authorService;

    @GetMapping("/api/author")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAuthors().stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/api/author/{id}")
    public void deleteAuthor(@PathVariable("id") long id) {
        authorService.deleteAuthor(id);
    }

    @PostMapping(value = "/api/author", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAuthor(@RequestBody Author author) {
        authorService.addNewAuthor(author);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
