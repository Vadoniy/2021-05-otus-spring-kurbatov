package ru.otus.repository.mongo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.mongo.Genre;
import ru.otus.mongo.event.GenreCascadeDeleteEventListener;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Import(GenreCascadeDeleteEventListener.class)
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void deleteById() {
        final var repoSizeBefore = genreRepository.findAll().size();
        final var randomGenreId = genreRepository.findAll().get(new Random().nextInt(3)).getId();
        assertTrue(bookRepository.findAll().stream()
                .anyMatch(book -> randomGenreId.equals(book.getGenre().getId())));
        assertTrue(genreRepository.existsById(randomGenreId));
        genreRepository.deleteById(randomGenreId);
        final var repoSizeAfter = genreRepository.findAll().size();
        assertFalse(genreRepository.existsById(randomGenreId));
        assertFalse(bookRepository.findAll().stream()
                .anyMatch(book -> randomGenreId.equals(book.getGenre().getId())));
        assertEquals(1, repoSizeBefore - repoSizeAfter);
        assertThrows(NoSuchElementException.class, () -> genreRepository.findById(randomGenreId).get());
    }

    @Test
    void findByGenre() {
        final var randomGenreId = genreRepository.findAll().get(new Random().nextInt(3)).getId();
        final var randomGenre = genreRepository.findById(randomGenreId);
        final var genreByName = genreRepository.findByGenre(randomGenre.map(Genre::getGenre).orElseThrow());
        assertEquals(randomGenre.get(), genreByName);
    }
}