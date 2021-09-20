package ru.otus.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.domain.mongo.FileQuestion;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class FileQuestionToExamQuestionConverterTest {

    @Test
    public void questionsAreExist() {
        final var fileQuestionToExamQuestionConverter = new FileQuestionToExamQuestionConverter();
        final var fileQuestion = new FileQuestion()
                .setQuestion(RandomStringUtils.random(5))
                .setQuestionNumber(String.valueOf(new Random().nextInt()))
                .setAnswers(RandomStringUtils.random(5))
                .setCorrectAnswer(new Random().nextInt());
        final var examQuestion = fileQuestionToExamQuestionConverter.convert(fileQuestion);

        assertNotNull(examQuestion);
        assertEquals(fileQuestion.getQuestion(), examQuestion.getQuestion());
        assertEquals(fileQuestion.getQuestionNumber(), String.valueOf(examQuestion.getQuestionNumber()));
        assertEquals(fileQuestion.getCorrectAnswer(), examQuestion.getCorrectAnswer());
    }
}