package ru.otus.dao.jdbc;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@AllArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private static final String ID_FIELD_NAME = "ID";

    private static final String NAME_FIELD_NAME = "NAME";

    private final JdbcOperations jdbc;

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            final var id = resultSet.getLong(ID_FIELD_NAME);
            final var name = resultSet.getString(NAME_FIELD_NAME);
            return new Author(id, name);
        }
    }

}
