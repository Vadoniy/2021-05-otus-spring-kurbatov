package ru.otus.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.DefaultResourceLoader;
import ru.otus.dao.impl.QuestionDaoImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class FileQuestionToExamQuestionConverterTest {

    @Mock
    private FileQuestionToExamQuestionConverter fileQuestionToExamQuestionConverter;

    @Test
    public void questionsAreExist() throws NoSuchFieldException, IllegalAccessException {
        final var questionDao = new QuestionDaoImpl(new DefaultResourceLoader(), fileQuestionToExamQuestionConverter, "test.csv");
        final var fileNameField = questionDao.getClass().getDeclaredField("fileName");
        fileNameField.setAccessible(true);
        fileNameField.set(questionDao, "test.csv");
        final var fileQuestions = questionDao.getQuestions();
        assertEquals(fileQuestions.size(), fileQuestions.size());
    }
}