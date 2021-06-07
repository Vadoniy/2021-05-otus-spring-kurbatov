package ru.otus.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.otus.dao.QuestionDao;
import ru.otus.exception.BusinessException;
import ru.otus.service.ExamService;

@Slf4j
public class ExamServiceImpl implements ExamService {

    private final QuestionDao questionDao;

    public ExamServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public void startExam() {
        try {
            final var questions = questionDao.readQuestions();
            System.out.println("Let's begin the exam.");
            questions.forEach(q -> {
                System.out.println(q.getQuestion());
                System.out.println(q.getAnswer1() + " OR " + q.getAnswer2());
            });
        } catch (BusinessException ex) {
            System.err.println("Can't read input resource! Description: " + ex.getMessage());
        }
    }
}
