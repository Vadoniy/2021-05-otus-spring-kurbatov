package ru.otus.dao.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dao.GenreDao;
import ru.otus.dao.jdbc.mapper.GenreMapper;
import ru.otus.domain.Genre;

import java.util.List;

@Repository
@AllArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final JdbcOperations jdbc;

    private final GenreMapper genreMapper;

    @Override
    public List<Genre> getAll() {
        return jdbc.query("SELECT * FROM GENRE G", genreMapper);
    }

    @Override
    public Genre getById(long id) {
        return jdbc.queryForObject("SELECT * FROM GENRE G WHERE G.ID = ?", genreMapper, id);
    }

    @Override
    public boolean insert(Genre genre) {
        return jdbc.update("INSERT INTO GENRE (GENRE) VALUES (?)",
                genre.getGenre()) == 1;
    }

    @Override
    public boolean deleteById(long id) {
        return jdbc.update("DELETE FROM GENRE WHERE ID = ?", id) == 1;
    }
}
