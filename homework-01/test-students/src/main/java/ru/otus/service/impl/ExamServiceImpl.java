package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.ExamQuestion;
import ru.otus.exception.BusinessException;
import ru.otus.service.ExamService;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final QuestionDao questionDao;

    private final FileQuestionToExamQuestionConverter fileQuestionToExamQuestionConverter;

    private final DisplayServiceImpl displayServiceImpl;

    @Override
    public void startExam() {
        try {
            final var questions = questionDao.readQuestions();
            displayServiceImpl.showText("Let's begin the exam.");
            questions.stream()
                    .map(fileQuestionToExamQuestionConverter::convert)
                    .filter(Objects::nonNull)
                    .map(ExamQuestion::toString)
                    .forEach(displayServiceImpl::showText);
        } catch (BusinessException ex) {
            System.err.println("Can't read input resource! Description: " + ex.getMessage());
        }
    }
}
