package ru.otus.dao.jdbc;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import ru.otus.dao.BookDao;
import ru.otus.dao.jdbc.mapper.BookMapper;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import({BookDaoJdbc.class, BookMapper.class})
class BookDaoJdbcTest {

    @Autowired
    public BookDao bookDaoJdbc;

    @Test
    @Rollback
    void insert() {
        final var expectedAuthor = Mockito.mock(Author.class);
        final var expectedGenre = Mockito.mock(Genre.class);
        final var expectedBook = new Book(RandomString.make(), expectedAuthor, expectedGenre);
        final var allBooksBefore = bookDaoJdbc.getAll();
        assertEquals(0, allBooksBefore.stream()
                .filter(book -> book.getTitle().equals(expectedBook.getTitle()))
                .count());
        bookDaoJdbc.insert(expectedBook);
        final var allBooksAfter = bookDaoJdbc.getAll();
        assertEquals(1, allBooksAfter.stream()
                .filter(book -> book.getTitle().equals(expectedBook.getTitle()))
                .count());
    }

    @Test
    @Rollback
    void getById() {
        final var expectedAuthor = Mockito.mock(Author.class);
        final var expectedGenre = Mockito.mock(Genre.class);
        final var expectedBook = new Book(RandomString.make(), expectedAuthor, expectedGenre);
        assertThrows(EmptyResultDataAccessException.class, () -> bookDaoJdbc.getById(1));
        bookDaoJdbc.insert(expectedBook);
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
        final var expectedAuthor = Mockito.mock(Author.class);
        final var expectedGenre = Mockito.mock(Genre.class);
        final var bookToUpdate = new Book(0, newTitle, expectedAuthor, expectedGenre);
        final var beforeUpdate = bookDaoJdbc.getById(0);
        assertNotEquals(newTitle, beforeUpdate.getTitle());
        bookDaoJdbc.update(bookToUpdate);
        final var afterUpdate = bookDaoJdbc.getById(0);
        assertNotEquals(beforeUpdate.getTitle(), afterUpdate.getTitle());
        assertEquals(newTitle, afterUpdate.getTitle());
    }
}