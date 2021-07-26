package ru.otus.repository;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.exception.UnknownBookException;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class LibraryTest {

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
        final var randomId = bookRepository.findAll().get(new Random().nextInt(3)).getId();
        assertNotNull(bookRepository.findById(randomId));
        bookRepository.deleteById(randomId);
        final var repoSizeAfter = bookRepository.findAll().size();
        assertEquals(1, repoSizeBefore - repoSizeAfter);
        assertThrows(NoSuchElementException.class, () -> bookRepository.findById(randomId).get());
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

    @Test
    void findCommentByBookId() {
        final var allComments = commentRepository.findAll();
        allComments.stream()
                .findFirst()
                .ifPresentOrElse(
                        comment -> {
                            final var commentsByBookId = commentRepository.findByBookId(comment.getBook().getId());
                            assertEquals(commentsByBookId.get(0), comment);
                        },
                        () -> {
                            throw new RuntimeException("There are no comments in DB");
                        });
    }

    @Test
    void findCommentByOwner() {
        final var allComments = commentRepository.findAll();
        allComments.stream()
                .findFirst()
                .ifPresentOrElse(
                        comment -> {
                            final var commentsByBookOwner = commentRepository.findByOwner(comment.getOwner());
                            assertEquals(commentsByBookOwner.get(0), comment);
                        },
                        () -> {
                            throw new RuntimeException("There are no comments in DB");
                        });
    }

    @Test
    void deleteCommentById() {
        final var repoSizeBefore = commentRepository.findAll().size();
        final var randomId = commentRepository.findAll().get(new Random().nextInt(4)).getId();
        assertNotNull(commentRepository.findById(randomId));
        commentRepository.deleteById(randomId);
        final var repoSizeAfter = commentRepository.findAll().size();
        assertEquals(1, repoSizeBefore - repoSizeAfter);
        assertThrows(NoSuchElementException.class, () -> commentRepository.findById(randomId).get());
    }
}