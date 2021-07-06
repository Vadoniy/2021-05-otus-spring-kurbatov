package ru.otus.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookMapper implements RowMapper<Book> {

    private static final String ID_FIELD_NAME = "ID";

    private static final String TITLE_FIELD_NAME = "TITLE";

    private static final String NAME_FIELD_NAME = "NAME";

    private static final String GENRE_FIELD_NAME = "GENRE";

    private static final String AUTHOR_ID_FIELD_NAME = "AUTHOR_ID";

    private static final String GENRE_ID_FIELD_NAME = "GENRE_ID";

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        final var id = resultSet.getLong(ID_FIELD_NAME);
        final var title = resultSet.getString(TITLE_FIELD_NAME);
        final var authorId = resultSet.getLong(AUTHOR_ID_FIELD_NAME);
        final var authorName = resultSet.getString(NAME_FIELD_NAME);
        final var genreId = resultSet.getLong(GENRE_ID_FIELD_NAME);
        final var genreTitle = resultSet.getString(GENRE_FIELD_NAME);
        return new Book(id, title, new Author(authorId, authorName), new Genre(genreId, genreTitle));
    }
}