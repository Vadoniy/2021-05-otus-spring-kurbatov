package otus.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import otus.rest.dto.AuthorDto;
import otus.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {

    private final AuthorService authorService;

    @GetMapping("/api/author/list")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAuthors().stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/api/author/delete/{id}")
    public void deleteAuthor(@PathVariable("id") long id) {
        authorService.deleteAuthor(id);
    }
}
