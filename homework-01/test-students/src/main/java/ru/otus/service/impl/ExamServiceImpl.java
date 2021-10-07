package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.configuration.BusinessConfigurationProperties;
import ru.otus.domain.ExamQuestion;
import ru.otus.exception.ReadFileQuestionsException;
import ru.otus.repository.QuestionDao;
import ru.otus.service.ExamService;
import ru.otus.service.LocalizationDisplayFacadeService;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final QuestionDao questionDao;

    private final BusinessConfigurationProperties businessConfigurationProperties;

    private final LocalizationDisplayFacadeService localizationDisplayFacadeService;

    @Override
    public void startExam() {
        try {
            final var questions = questionDao.getQuestions();
            localizationDisplayFacadeService.showLocalizedText("info.greeting", businessConfigurationProperties.getStopWord());
            final var amountOfQuestions = questions.size();
            final var amountOfCorrectAnswers = questions.stream()
                    .filter(Objects::nonNull)
                    .map(this::processTheQuestion)
                    .filter(Boolean::booleanValue)
                    .count();
            if (amountOfCorrectAnswers <= amountOfQuestions / 2) {
                localizationDisplayFacadeService.showLocalizedText("info.result.bad");
            } else if (amountOfCorrectAnswers == amountOfQuestions) {
                localizationDisplayFacadeService.showLocalizedText("info.result.perfect");
            } else {
                localizationDisplayFacadeService.showLocalizedText("info.result.so-so");
            }
            System.exit(0);
        } catch (ReadFileQuestionsException ex) {
            localizationDisplayFacadeService.showLocalizedText("input.error.file", ex.getMessage());
        }
    }

    private boolean processTheQuestion(ExamQuestion question) {
        localizationDisplayFacadeService.showText(question.toString());
        final var usersAnswer = localizationDisplayFacadeService.getInputString();
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
            localizationDisplayFacadeService.showLocalizedText("input.error.user");
        }
        return 0;
    }
}
