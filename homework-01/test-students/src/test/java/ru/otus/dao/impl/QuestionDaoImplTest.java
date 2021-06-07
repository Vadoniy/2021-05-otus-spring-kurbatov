package ru.otus.dao.impl;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.PathResource;
import ru.otus.exception.BusinessException;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuestionDaoImplTest {

    @Test
    public void questionsAreExist() {
        final var questionDao = new QuestionDaoImpl(new PathResource(Path.of("src/test/resources/test.csv")));
        assertNotNull(questionDao.readQuestions());
    }

    @Test
    public void emptyResourceThrowsException() {
        assertThrows(BusinessException.class, () -> new QuestionDaoImpl(null).readQuestions());
    }
}