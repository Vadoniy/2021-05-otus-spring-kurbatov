package ru.otus.dao.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@AllArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private static final String ID_FIELD_NAME = "ID";

    private static final String TITLE_FIELD_NAME = "TITLE";

    private final JdbcOperations jdbc;

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            final var id = resultSet.getLong(ID_FIELD_NAME);
            final var name = resultSet.getString(TITLE_FIELD_NAME);
            return new Genre(id, name);
        }
    }
}
