package ru.otus.dao.jdbc;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.dao.jdbc.mapper.AuthorMapper;
import ru.otus.dao.jdbc.mapper.BookMapper;
import ru.otus.dao.jdbc.mapper.GenreMapper;
import ru.otus.domain.Book;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import({AuthorDaoJdbc.class, BookDaoJdbc.class, GenreDaoJdbc.class, AuthorMapper.class, BookMapper.class, GenreMapper.class})
class BookDaoJdbcTest {

    @Autowired
    public AuthorDao authorDaoJdbc;

    @Autowired
    public BookDao bookDaoJdbc;

    @Autowired
    public GenreDao genreDaoJdbc;

    @Test
    @Rollback
    void insert() {
        final var author1 = authorDaoJdbc.getById(0);
        final var genre1 = genreDaoJdbc.getById(0);
        final var book1 = new Book(RandomString.make(), author1, genre1);
        final var allBooksBefore = bookDaoJdbc.getAll();
        assertEquals(0, allBooksBefore.stream()
                .filter(book -> book.getTitle().equals(book1.getTitle()))
                .count());
        bookDaoJdbc.insert(book1);
        final var allBooksAfter = bookDaoJdbc.getAll();
        assertEquals(1, allBooksAfter.stream()
                .filter(book -> book.getTitle().equals(book1.getTitle()))
                .count());
        assertEquals(1, allBooksAfter.stream()
                .filter(book ->
                        book.getAuthor().equals(book1.getAuthor()) &&
                                book.getGenre().equals(book1.getGenre()) &&
                                book.getTitle().equals(book1.getTitle())
                )
                .count());
    }

    @Test
    @Rollback
    void getById() {
        final var author1 = authorDaoJdbc.getById(0);
        final var genre1 = genreDaoJdbc.getById(0);
        final var book1 = new Book(RandomString.make(), author1, genre1);
        assertThrows(EmptyResultDataAccessException.class, () -> bookDaoJdbc.getById(1));
        bookDaoJdbc.insert(book1);
        final var newId = bookDaoJdbc.getAll().stream()
                .filter(book -> book.getId() != 0)
                .map(Book::getId)
                .findFirst();
        assertNotNull(bookDaoJdbc.getById(newId.orElse(-1L)));
    }

    @Test
    void getAll() {
        assertEquals(1, bookDaoJdbc.getAll().size());
    }

    @Test
    @Rollback
    void deleteById() {
        assertEquals(1, bookDaoJdbc.getAll().size());
        bookDaoJdbc.deleteById(0);
        assertEquals(0, bookDaoJdbc.getAll().size());
    }

    @Test
    @Rollback
    void update() {
        final var newTitle = "Title";
        final var author1 = authorDaoJdbc.getById(0);
        final var genre1 = genreDaoJdbc.getById(0);
        final var bookToUpdate = new Book(0, newTitle, author1, genre1);
        final var beforeUpdate = bookDaoJdbc.getById(0);
        assertNotEquals(newTitle, beforeUpdate.getTitle());
        bookDaoJdbc.update(bookToUpdate);
        final var afterUpdate = bookDaoJdbc.getById(0);
        assertNotEquals(beforeUpdate.getTitle(), afterUpdate.getTitle());
        assertEquals(newTitle, afterUpdate.getTitle());
    }
}