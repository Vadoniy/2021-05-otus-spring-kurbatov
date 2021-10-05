package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.configuration.BusinessConfigurationProperties;
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

    private final BusinessConfigurationProperties businessConfigurationProperties;

    private final LocalizationServiceImpl localizationService;

    @Override
    public void startExam() {
        try {
            final var questions = questionDao.getQuestions();

            displayServiceImpl.showText(String.format(localizationService.getLocalizedMessage("info.greeting"), businessConfigurationProperties.getStopWord()),
                    System.out);
            final var amountOfQuestions = questions.size();
            final var amountOfCorrectAnswers = questions.stream()
                    .filter(Objects::nonNull)
                    .map(this::processTheQuestion)
                    .filter(Boolean::booleanValue)
                    .count();
            if (amountOfCorrectAnswers <= amountOfQuestions / 2) {
                displayServiceImpl.showText(localizationService.getLocalizedMessage("info.result.bad"), System.out);
            } else if (amountOfCorrectAnswers == amountOfQuestions) {
                displayServiceImpl.showText(localizationService.getLocalizedMessage("info.result.perfect"), System.out);
            } else {
                displayServiceImpl.showText(localizationService.getLocalizedMessage("info.result.so-so"), System.out);
            }
        } catch (ReadFileQuestionsException ex) {
            displayServiceImpl.showText(localizationService.getLocalizedMessage("input.error.file") + ex.getMessage(), System.err);
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
            displayServiceImpl.showText(localizationService.getLocalizedMessage("input.error.user"), System.err);
        }
        return 0;
    }
}
