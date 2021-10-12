package ru.otus.repository.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.DefaultResourceLoader;
import ru.otus.domain.ExamQuestion;
import ru.otus.exception.ReadFileQuestionsException;
import ru.otus.service.FileNameProvider;
import ru.otus.service.impl.FileQuestionToExamQuestionConverter;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileQuestionDaoCsvTest {

    @Mock
    private FileQuestionToExamQuestionConverter fileQuestionToExamQuestionConverter;

    @Mock
    private FileNameProvider fileNameProvider;

    @Test
    public void questionsAreExist() {
        when(fileNameProvider.getFileNameWithPath()).thenReturn("data/test.csv");
        given(fileQuestionToExamQuestionConverter.convert(any()))
                .willReturn(
                        new ExamQuestion()
                                .setQuestion(RandomStringUtils.random(5))
                                .setAnswers(List.of(RandomStringUtils.random(5), RandomStringUtils.random(5)))
                                .setCorrectAnswer(new Random().nextInt())
                                .setQuestionNumber(new Random().nextInt())
                );
        final var questionDao = new QuestionDaoCsv(fileNameProvider, fileQuestionToExamQuestionConverter, new DefaultResourceLoader());
        assertNotNull(questionDao.getQuestions());
    }

    @Test
    public void emptyResourceThrowsException() {
        when(fileNameProvider.getFileNameWithPath()).thenReturn(RandomStringUtils.random(10));
        assertThrows(ReadFileQuestionsException.class, () -> new QuestionDaoCsv(fileNameProvider, fileQuestionToExamQuestionConverter,
                new DefaultResourceLoader())
                .getQuestions());
    }
}