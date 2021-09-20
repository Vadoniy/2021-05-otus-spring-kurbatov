package ru.otus.repository.mongo;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.mongo.Author;
import ru.otus.domain.mongo.Book;
import ru.otus.domain.mongo.Genre;
import ru.otus.exception.UnknownBookException;
import ru.otus.mongo.event.BookCascadeDeleteEventListener;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Import(BookCascadeDeleteEventListener.class)
class BookRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void insert() {
        final var expectedAuthor = new Author(RandomString.make());
        final var expectedGenre = new Genre(RandomString.make());
        final var expectedBook = new Book(RandomString.make(), expectedAuthor, expectedGenre);
        final var allBooksBefore = bookRepository.findAll();
        assertEquals(0, allBooksBefore.stream()
                .filter(book -> book.getTitle().equals(expectedBook.getTitle()))
                .count());
        bookRepository.save(expectedBook);
        final var allBooksAfter = bookRepository.findAll();
        assertEquals(1, allBooksAfter.stream()
                .filter(book -> book.getTitle().equals(expectedBook.getTitle()))
                .count());
    }

    @Test
    void getById() {
        final var expectedAuthor = new Author(RandomString.make());
        final var expectedGenre = new Genre(RandomString.make());
        final var expectedBook = new Book(RandomString.make(), expectedAuthor, expectedGenre);
        final var idsBefore = bookRepository.findAll().stream()
                .map(Book::getId)
                .collect(Collectors.toSet());
        bookRepository.save(expectedBook);
        final var newId = expectedBook.getId();
        assertFalse(idsBefore.contains(newId));
        assertNotNull(bookRepository.findById(newId));
    }

    @Test
    void getAll() {
        assertEquals(3, bookRepository.findAll().size());
    }

    @Test
    void deleteById() {
        final var repoSizeBefore = bookRepository.findAll().size();
        final var randomBookId = bookRepository.findAll().get(new Random().nextInt(3)).getId();
        assertNotNull(bookRepository.findById(randomBookId));
        assertTrue(commentRepository.existsByBookId(randomBookId));
        bookRepository.deleteById(randomBookId);
        final var repoSizeAfter = bookRepository.findAll().size();
        assertFalse(commentRepository.existsByBookId(randomBookId));
        assertFalse(bookRepository.existsById(randomBookId));
        assertEquals(1, repoSizeBefore - repoSizeAfter);
        assertThrows(NoSuchElementException.class, () -> bookRepository.findById(randomBookId).get());
    }

    @Test
    void update() {
        final var newTitle = "Title";
        final var randomId = bookRepository.findAll().get(new Random().nextInt(3)).getId();
        final var bookToUpdate = bookRepository.findById(randomId);
        bookToUpdate.ifPresentOrElse(
                b -> {
                    assertNotEquals(newTitle, b.getTitle());
                    b.setTitle(newTitle);
                    bookRepository.save(b);
                },
                () -> {
                    throw new UnknownBookException("There is no such book");
                });
        final var updatedBook = bookRepository.findById(randomId);
        updatedBook.ifPresentOrElse(
                b -> {
                    assertEquals(newTitle, b.getTitle());
                },
                () -> {
                    throw new UnknownBookException("There is no such book");
                });
    }
}