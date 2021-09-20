package ru.otus.repository.mongo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

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
        final var randomId = commentRepository.findAll().get(new Random().nextInt(3)).getId();
        assertNotNull(commentRepository.findById(randomId));
        commentRepository.deleteById(randomId);
        final var repoSizeAfter = commentRepository.findAll().size();
        assertEquals(1, repoSizeBefore - repoSizeAfter);
        assertThrows(NoSuchElementException.class, () -> commentRepository.findById(randomId).get());
    }
}