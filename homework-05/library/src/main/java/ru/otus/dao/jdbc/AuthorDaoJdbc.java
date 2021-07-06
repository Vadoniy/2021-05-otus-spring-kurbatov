package ru.otus.dao.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private static final String ID_FIELD_NAME = "ID";

    private static final String NAME_FIELD_NAME = "NAME";

    private final JdbcOperations jdbc;

    @Override
    public List<Author> getAll() {
        return jdbc.query("SELECT * FROM AUTHOR A", new AuthorMapper());
    }

    @Override
    public Author getById(long id) {
        return jdbc.queryForObject("SELECT * FROM AUTHOR A WHERE A.ID = ?", new AuthorMapper(), id);
    }

    @Override
    public boolean insert(Author author) {
        return jdbc.update("INSERT INTO AUTHOR (NAME) VALUES (?)",
                author.getName()) == 1;
    }

    @Override
    public boolean deleteById(long id) {
        return jdbc.update("DELETE FROM AUTHOR WHERE ID = ?", id) == 1;
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            final var id = resultSet.getLong(ID_FIELD_NAME);
            final var name = resultSet.getString(NAME_FIELD_NAME);
            return new Author(id, name);
        }
    }

}
