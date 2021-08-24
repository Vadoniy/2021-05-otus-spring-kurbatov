package otus.service;

import otus.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    void addNewGenre(Genre genre);

    void deleteGenre(long id);

    List<Genre> getGenres();

    Optional<Genre> getGenreById(long id);
}
