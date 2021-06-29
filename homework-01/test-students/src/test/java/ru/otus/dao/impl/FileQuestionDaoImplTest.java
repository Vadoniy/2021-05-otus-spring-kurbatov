package ru.otus.dao.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.DefaultResourceLoader;
import ru.otus.domain.ExamQuestion;
import ru.otus.exception.ReadFileQuestionsException;
import ru.otus.service.impl.FileQuestionToExamQuestionConverter;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FileQuestionDaoImplTest {

    @Mock
    private FileQuestionToExamQuestionConverter fileQuestionToExamQuestionConverter;

    @Test
    public void questionsAreExist() throws NoSuchFieldException, IllegalAccessException {
        given(fileQuestionToExamQuestionConverter.convert(any()))
                .willReturn(
                        new ExamQuestion()
                                .setQuestion(RandomStringUtils.random(5))
                                .setAnswers(List.of(RandomStringUtils.random(5), RandomStringUtils.random(5)))
                                .setCorrectAnswer(new Random().nextInt())
                                .setQuestionNumber(new Random().nextInt())
                );
        final var questionDao = new QuestionDaoImpl(new DefaultResourceLoader(), fileQuestionToExamQuestionConverter, "test.csv");
        final var fileNameField = questionDao.getClass().getDeclaredField("fileName");
        fileNameField.setAccessible(true);
        fileNameField.set(questionDao, "test.csv");
        assertNotNull(questionDao.getQuestions());
    }

    @Test
    public void emptyResourceThrowsException() {
        assertThrows(ReadFileQuestionsException.class, () -> new QuestionDaoImpl(null, fileQuestionToExamQuestionConverter, "test.csv")
                .getQuestions());
    }
}