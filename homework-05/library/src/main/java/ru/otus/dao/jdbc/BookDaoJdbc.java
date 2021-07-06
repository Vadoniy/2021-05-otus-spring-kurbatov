package ru.otus.dao.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.dao.BookDao;
import ru.otus.dao.jdbc.mapper.BookMapper;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final JdbcOperations jdbc;

    private final BookMapper bookMapper;

    @Override
    public boolean insert(Book book) {
        return jdbc.update("INSERT INTO BOOK (TITLE, AUTHOR_ID, GENRE_ID) VALUES (?, ?, ?)",
                book.getTitle(),
                book.getAuthor().getId(),
                book.getGenre().getId()) == 1;
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject("SELECT B.ID, B.TITLE, B.AUTHOR_ID, A.NAME, B.GENRE_ID, G.GENRE FROM BOOK B " +
                "JOIN AUTHOR A " +
                "ON B.AUTHOR_ID = A.ID " +
                "JOIN GENRE G " +
                "ON B.GENRE_ID = G.ID WHERE B.ID = ?", bookMapper, id);
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("SELECT B.ID, B.TITLE, B.AUTHOR_ID, A.NAME, B.GENRE_ID, G.GENRE FROM BOOK B " +
                "JOIN AUTHOR A " +
                "ON B.AUTHOR_ID = A.ID " +
                "JOIN GENRE G " +
                "ON B.GENRE_ID = G.ID", bookMapper);
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
}
