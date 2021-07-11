package ru.otus.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuthorMapper implements RowMapper<Author> {

    private static final String ID_FIELD_NAME = "ID";

    private static final String NAME_FIELD_NAME = "NAME";

    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        final var id = resultSet.getLong(ID_FIELD_NAME);
        final var name = resultSet.getString(NAME_FIELD_NAME);
        return new Author(id, name);
    }
}