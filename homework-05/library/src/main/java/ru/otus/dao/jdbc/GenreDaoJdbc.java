package ru.otus.dao.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private static final String ID_FIELD_NAME = "ID";

    private static final String GENRE_FIELD_NAME = "GENRE";

    private final JdbcOperations jdbc;

    @Override
    public List<Genre> getAll() {
        return jdbc.query("SELECT * FROM GENRE G", new GenreMapper());
    }

    @Override
    public Genre getById(long id) {
        return jdbc.queryForObject("SELECT * FROM GENRE G WHERE G.ID = ?", new GenreMapper(), id);
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

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            final var id = resultSet.getLong(ID_FIELD_NAME);
            final var name = resultSet.getString(GENRE_FIELD_NAME);
            return new Genre(id, name);
        }
    }
}
