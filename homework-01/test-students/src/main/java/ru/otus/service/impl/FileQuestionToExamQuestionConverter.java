package ru.otus.service.impl;

import org.springframework.core.convert.converter.Converter;
import ru.otus.domain.ExamQuestion;
import ru.otus.domain.FileQuestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class FileQuestionToExamQuestionConverter implements Converter<FileQuestion, ExamQuestion> {

    @Override
    public ExamQuestion convert(FileQuestion fileQuestion) {
        final var answers = Optional.ofNullable(fileQuestion.getAnswers())
                .map(answerString -> answerString.split(";"))
                .map(Arrays::asList)
                .orElse(new ArrayList<>());
        return new ExamQuestion()
                .setQuestion(fileQuestion.getQuestion())
                .setAnswers(answers)
                .setCorrectAnswer(fileQuestion.getCorrectAnswer());
    }
}
