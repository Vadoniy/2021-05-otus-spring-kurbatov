package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.ExamQuestion;
import ru.otus.exception.BusinessException;
import ru.otus.service.ExamService;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final QuestionDao questionDao;

    private final FileQuestionToExamQuestionConverter fileQuestionToExamQuestionConverter;

    private final DisplayServiceImpl displayServiceImpl;

    @Value("${stop-word}")
    private String stopWord;

    @Override
    public void startExam() {
        try {
            final var questions = questionDao.readQuestions();
            var correctAnswerCounter = 0;
            displayServiceImpl.showText(String.format("Let's begin the exam. Type %s to go away or press enter to continue", stopWord));
            final var amountOfQuestions = questions.size();
            final var amountOfCorrectAnswers = questions.stream()
                    .map(fileQuestionToExamQuestionConverter::convert)
                    .filter(Objects::nonNull)
                    .map(this::processTheQuestion)
                    .filter(Boolean::booleanValue)
                    .count();
            if (amountOfCorrectAnswers <= amountOfQuestions / 2) {
                System.out.println("You are the weakest link, good bye.");
            } else if (amountOfCorrectAnswers == amountOfQuestions) {
                System.out.println("Congratulations! You are the strongest link!");
            } else {
                System.out.println("Not bad, but try harder in the future.");
            }
        } catch (BusinessException ex) {
            System.err.println("Can't read input resource! Description: " + ex.getMessage());
        }
    }

    private boolean processTheQuestion(ExamQuestion question) {
        displayServiceImpl.showText(question.toString());
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
                    " it is always digit, don't use anything else. Try another time");
        }
        return 0;
    }
}
