package ru.otus.service.impl;

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
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExamServiceImpl implements ExamService {

    private final QuestionDao questionDao;

    private final DisplayService displayService;

    private final String stopWord;

    public ExamServiceImpl(QuestionDao questionDao, DisplayService displayService, @Value("${stop-word}") String stopWord) {
        this.questionDao = questionDao;
        this.displayService = displayService;
        this.stopWord = stopWord;
    }

    @Override
    public void startExam() {
        try {
            displayService.showText("Let's begin the exam. Any moment you can type \"%s\" to leave.", stopWord);

            final var studentsName = findOutStudentsName();
            final var questions = questionDao.getQuestions();
            final var amountOfQuestions = questions.size();
            final var questionsWithAnswers = questions.stream()
                    .filter(Objects::nonNull)
                    .map(this::processTheQuestion)
                    .collect(Collectors.toList());
            final var amountOfCorrectAnswers = questionsWithAnswers.stream()
                    .filter(Objects::nonNull)
                    .filter(question -> question.getCorrectAnswer().equals(question.getUsersAnswer()))
                    .count();
            displayService.showText(getResult(amountOfQuestions, amountOfCorrectAnswers));
            log.info("Student {}, correct answers {}/{}}", studentsName, amountOfCorrectAnswers, amountOfQuestions);
        } catch (ReadFileQuestionsException ex) {
            displayService.showText("Can't read input resource! Description: " + ex.getMessage());
        }
    }

    private String findOutStudentsName() {
        final var studentsName = getStudentsInfo("name");
        final var studentsLastName = getStudentsInfo("surname");
        final var stringJoiner = new StringJoiner(" ");
        return stringJoiner.add(studentsName).add(studentsLastName).toString();
    }

    private String getResult(int amountOfQuestions, long amountOfCorrectAnswers) {
        if (amountOfCorrectAnswers <= amountOfQuestions / 2) {
            return "You are the weakest link, good bye.";
        } else if (amountOfCorrectAnswers == amountOfQuestions) {
            return "Congratulations! You are the strongest link!";
        } else {
            return "Not bad, but try harder in the future.";
        }
    }

    private ExamQuestion processTheQuestion(ExamQuestion question) {
        displayService.showText(question.toString());
        final var usersAnswer = displayService.getInputString();
        checkInputForInterruptionOfExam(usersAnswer);
        question.setUsersAnswer(validateUsersAnswer(usersAnswer));
        return question;
    }

    private void checkInputForInterruptionOfExam(String usersAnswer) {
        if (stopWord.equals(usersAnswer)) {
            log.info("User decided to interrupt exam.");
            displayService.showText("You have typed \"exit\", we are sorry that you are going.");
            System.exit(0);
        }
    }

    private int validateUsersAnswer(String usersAnswer) {
        try {
            return Optional.ofNullable(usersAnswer)
                    .filter(StringUtils::hasText)
                    .map(Integer::parseInt)
                    .orElse(0);
        } catch (NumberFormatException ex) {
            log.error("Wrong input from user. {}", ex.getMessage());
            displayService.showText("Look, it is just test, you should type number of answer," +
                    " it is always digit, don't use anything else.");
        }
        return 0;
    }

    private String getStudentsInfo(String whatWeWantToFindOut) {
        displayService.showText("Please, type your %s", whatWeWantToFindOut);
        final var studentsName = new StringBuilder(displayService.getInputString());
        while (!StringUtils.hasText(studentsName)) {
            displayService.showText("Are you kidding? Type your %s and let's go on.", whatWeWantToFindOut);
            studentsName.append(displayService.getInputString());
        }
        checkInputForInterruptionOfExam(studentsName.toString());
        return studentsName.toString();
    }
}
