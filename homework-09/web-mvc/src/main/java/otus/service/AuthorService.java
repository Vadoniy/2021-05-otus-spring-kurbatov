package otus.service;

import otus.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    void addNewAuthor(Author author);

    void deleteAuthor(long id);

    List<Author> getAuthors();

    Optional<Author> getAuthorById(long id);
}
