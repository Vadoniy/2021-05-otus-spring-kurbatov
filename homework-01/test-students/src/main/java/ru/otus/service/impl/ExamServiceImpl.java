package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.ExamQuestion;
import ru.otus.exception.ReadFileQuestionsException;
import ru.otus.service.DisplayService;
import ru.otus.service.ExamService;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final QuestionDao questionDao;

    private final DisplayService displayServiceImpl;

    @Value("${stop-word}")
    private String stopWord;

    @Override
    public void startExam() {
        try {
            final var questions = questionDao.getQuestions();

            displayServiceImpl.showText(String.format("Let's begin the exam. Type %s to go away or press enter to continue", stopWord),
                    System.out);
            final var amountOfQuestions = questions.size();
            final var amountOfCorrectAnswers = questions.stream()
                    .filter(Objects::nonNull)
                    .map(this::processTheQuestion)
                    .filter(Boolean::booleanValue)
                    .count();
            if (amountOfCorrectAnswers <= amountOfQuestions / 2) {
                displayServiceImpl.showText("You are the weakest link, good bye.", System.out);
            } else if (amountOfCorrectAnswers == amountOfQuestions) {
                displayServiceImpl.showText("Congratulations! You are the strongest link!", System.out);
            } else {
                displayServiceImpl.showText("Not bad, but try harder in the future.", System.out);
            }
        } catch (ReadFileQuestionsException ex) {
            displayServiceImpl.showText("Can't read input resource! Description: " + ex.getMessage(), System.err);
        }
    }

    private boolean processTheQuestion(ExamQuestion question) {
        displayServiceImpl.showText(question.toString(), System.out);
        final var usersAnswer = displayServiceImpl.getInputString();
        return validateUsersAnswer(usersAnswer).equals(question.getCorrectAnswer());
    }

    private Integer validateUsersAnswer(String usersAnswer) {
        try {
            return Optional.ofNullable(usersAnswer)
                    .filter(StringUtils::hasText)
                    .map(Integer::parseInt)
                    .orElse(0);
        } catch (NumberFormatException ex) {
            log.error("Wrong input from user. {}", ex.getMessage());
            displayServiceImpl.showText("Look, it is just test, you should type number of answer," +
                    " it is always digit, don't use anything else. Try another time", System.err);
        }
        return 0;
    }
}
