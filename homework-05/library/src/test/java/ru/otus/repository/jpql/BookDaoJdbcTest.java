package ru.otus.repository.jpql;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.repository.BookRepository;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BookRepositoryJpql.class})
class BookDaoJdbcTest {

    @Autowired
    private BookRepository bookRepositoryJpql;

    @Autowired
    private TestEntityManager em;

    @Test
    void insert() {
        final var expectedAuthor = new Author(RandomString.make());
        final var expectedGenre = new Genre(RandomString.make());
        final var expectedBook = new Book(RandomString.make(), expectedAuthor, expectedGenre);
        final var allBooksBefore = bookRepositoryJpql.getAll();
        assertEquals(0, allBooksBefore.stream()
                .filter(book -> book.getTitle().equals(expectedBook.getTitle()))
                .count());
        bookRepositoryJpql.insert(expectedBook);
        final var allBooksAfter = bookRepositoryJpql.getAll();
        assertEquals(1, allBooksAfter.stream()
                .filter(book -> book.getTitle().equals(expectedBook.getTitle()))
                .count());
    }

    @Test
    void getById() {
        final var expectedAuthor = new Author(RandomString.make());
        final var expectedGenre = new Genre(RandomString.make());
        final var expectedBook = new Book(RandomString.make(), expectedAuthor, expectedGenre);
        assertTrue(bookRepositoryJpql.getById(1).isPresent());
        assertTrue(bookRepositoryJpql.getAll().stream()
                .map(Book::getId)
                .filter(id -> id != 1)
                .findFirst()
                .isEmpty());
        bookRepositoryJpql.insert(expectedBook);
        assertTrue(bookRepositoryJpql.getById(1).isPresent());
        final var newId = bookRepositoryJpql.getAll().stream()
                .map(Book::getId)
                .filter(id -> id != 1)
                .findFirst();
        assertNotNull(bookRepositoryJpql.getById(newId.orElse(-1L)));
    }

    @Test
    void getAll() {
        assertEquals(1, bookRepositoryJpql.getAll().size());
    }

    @Test
    void deleteById() {
        assertEquals(1, bookRepositoryJpql.getAll().size());
        bookRepositoryJpql.deleteById(1);
        assertEquals(0, bookRepositoryJpql.getAll().size());
    }

    @Test
    void update() {
        final var newTitle = "Title";
        final var bookToUpdate = em.find(Book.class, 1L);
        assertNotEquals(newTitle, bookToUpdate.getTitle());
        bookToUpdate.setTitle(newTitle);
        bookRepositoryJpql.update(bookToUpdate);
        final var updatedBook = em.find(Book.class, 1L);
        assertEquals(newTitle, updatedBook.getTitle());
    }
}