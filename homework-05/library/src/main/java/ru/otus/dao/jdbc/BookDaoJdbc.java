package ru.otus.dao.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.dao.BookDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class BookDaoJdbc implements BookDao {

    private static final String ID_FIELD_NAME = "ID";

    private static final String TITLE_FIELD_NAME = "TITLE";

    private static final String NAME_FIELD_NAME = "NAME";

    private static final String AUTHOR_ID_FIELD_NAME = "AUTHOR_ID";

    private static final String GENRE_ID_FIELD_NAME = "GENRE_ID";

    private final JdbcOperations jdbc;

    @Override
    public boolean insert(Book book) {
        return jdbc.update("INSERT INTO BOOK (ID, TITLE, AUTHOR_ID, GENRE_ID) VALUES (?, ?, ?, ?)",
                book.getId(),
                book.getTitle(),
                book.getAuthor().getId(),
                book.getGenre().getId()) == 1;
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject("SELECT B.ID, B.TITLE, B.AUTHOR_ID, A.NAME, B.GENRE_ID, G.TITLE FROM BOOK B " +
                "JOIN AUTHOR A " +
                "ON B.AUTHOR_ID = A.ID " +
                "JOIN GENRE G " +
                "ON B.GENRE_ID = G.ID WHERE B.ID = ?", new BookMapper(), id);
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("SELECT B.ID, B.TITLE, B.AUTHOR_ID, A.NAME, B.GENRE_ID, G.TITLE FROM BOOK B " +
                "JOIN AUTHOR A " +
                "ON B.AUTHOR_ID = A.ID " +
                "JOIN GENRE G " +
                "ON B.GENRE_ID = G.ID", new BookMapper());
    }

    @Override
    public boolean deleteById(long id) {
        return jdbc.update("DELETE FROM BOOK WHERE ID = ?", id) == 1;
    }

    @Override
    public boolean update(Book book) {
        return jdbc.update("UPDATE BOOK SET TITLE = ?, AUTHOR_ID = ?, GENRE_ID = ? WHERE ID = ?",
                book.getTitle(),
                book.getAuthor().getId(),
                book.getGenre().getId(),
                book.getId()) == 1;
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            final var id = resultSet.getLong(ID_FIELD_NAME);
            final var title = resultSet.getString(TITLE_FIELD_NAME);
            final var authorId = resultSet.getLong(AUTHOR_ID_FIELD_NAME);
            final var authorName = resultSet.getString(NAME_FIELD_NAME);
            final var genreId = resultSet.getLong(GENRE_ID_FIELD_NAME);
            final var genreTitle = resultSet.getString(TITLE_FIELD_NAME);
            return new Book(id, title, new Author(authorId, authorName), new Genre(genreId, genreTitle));
        }
    }
}
