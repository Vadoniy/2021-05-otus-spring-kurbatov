package ru.otus.repository;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.exception.UnknownBookException;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
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
        assertNotNull(bookRepository.getById(newId));
    }

    @Test
    void getAll() {
        assertEquals(2, bookRepository.findAll().size());
    }

    @Test
    void deleteById() {
        final var repoSizeBefore = bookRepository.findAll().size();
        assertNotNull(bookRepository.findById(1L));
        bookRepository.deleteById(1L);
        final var repoSizeAfter = bookRepository.findAll().size();
        assertEquals(1, repoSizeBefore - repoSizeAfter);
        assertThrows(NoSuchElementException.class, () -> bookRepository.findById(1L).get());
    }

    @Test
    void update() {
        final var newTitle = "Title";
        final var bookToUpdate = bookRepository.findById(1L);
        bookToUpdate.ifPresentOrElse(
                b -> {
                    assertNotEquals(newTitle, b.getTitle());
                    b.setTitle(newTitle);
                    bookRepository.save(b);
                },
                () -> {
                    throw new UnknownBookException("There is no such book");
                });
        final var updatedBook = bookRepository.findById(1L);
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
        assertNotNull(commentRepository.findById(1L));
        commentRepository.deleteById(1L);
        final var repoSizeAfter = commentRepository.findAll().size();
        assertEquals(1, repoSizeBefore - repoSizeAfter);
        assertThrows(NoSuchElementException.class, () -> commentRepository.findById(1L).get());
    }
}