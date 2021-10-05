package ru.otus.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.mongo.event.AuthorCascadeDeleteEventListener;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Import(AuthorCascadeDeleteEventListener.class)
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void deleteById() {
        final var repoSizeBefore = authorRepository.findAll().size();
        final var randomAuthorId = authorRepository.findAll().get(new Random().nextInt(3)).getId();
        assertTrue(bookRepository.findAll().stream()
                .anyMatch(book -> randomAuthorId.equals(book.getAuthor().getId())));
        assertTrue(authorRepository.existsById(randomAuthorId));
        authorRepository.deleteById(randomAuthorId);
        final var repoSizeAfter = authorRepository.findAll().size();
        assertFalse(authorRepository.existsById(randomAuthorId));
        assertFalse(bookRepository.findAll().stream()
                .anyMatch(book -> randomAuthorId.equals(book.getAuthor().getId())));
        assertEquals(1, repoSizeBefore - repoSizeAfter);
        assertThrows(NoSuchElementException.class, () -> authorRepository.findById(randomAuthorId).get());
    }

    @Test
    void findByName() {
        final var randomAuthorId = authorRepository.findAll().get(new Random().nextInt(3)).getId();
        final var randomAuthor = authorRepository.findById(randomAuthorId);
        final var authorByName = authorRepository.findByName(randomAuthor.map(Author::getName).orElseThrow());
        assertEquals(randomAuthor.get(), authorByName);
    }
}