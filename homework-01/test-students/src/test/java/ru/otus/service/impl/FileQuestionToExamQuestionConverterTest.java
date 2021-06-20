package ru.otus.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.PathResource;
import ru.otus.dao.impl.QuestionDaoImpl;

import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileQuestionToExamQuestionConverterTest {

    @Test
    public void questionsAreExist() {
        final var questionDao = new QuestionDaoImpl(new PathResource(Path.of("src/test/resources/test.csv")));
        final var fileQuestions = questionDao.readQuestions();
        final var fileQuestionToExamQuestionConverter = new FileQuestionToExamQuestionConverter();
        final var examQuestions = fileQuestions.stream()
                .map(fileQuestionToExamQuestionConverter::convert)
                .collect(Collectors.toList());
        assertEquals(fileQuestions.size(), examQuestions.size());
    }
}