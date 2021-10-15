package ru.otus.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.domain.ExamQuestion;
import ru.otus.exception.ReadFileQuestionsException;
import ru.otus.repository.QuestionDao;
import ru.otus.service.ExamService;
import ru.otus.service.FileNameProvider;
import ru.otus.service.LocalizationDisplayService;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ExamServiceImpl implements ExamService {

    private final QuestionDao questionDao;

    private final String stopWord;

    private final LocalizationDisplayService localizationDisplayService;

    public ExamServiceImpl(QuestionDao questionDao, @Value("${exam.stop-word}") String stopWord,
                           FileNameProvider fileNameProvider, LocalizationDisplayService localizationDisplayService) {
        this.questionDao = questionDao;
        this.stopWord = stopWord;
        this.localizationDisplayService = localizationDisplayService;
    }

    @Override
    public void startExam() {
        try {
            final var questions = questionDao.getQuestions();
            localizationDisplayService.showLocalizedMessage("info.greeting", stopWord);
            final var amountOfQuestions = questions.size();
            final var amountOfCorrectAnswers = questions.stream()
                    .filter(Objects::nonNull)
                    .map(this::processTheQuestion)
                    .filter(Boolean::booleanValue)
                    .count();
            if (amountOfCorrectAnswers <= amountOfQuestions / 2) {
                localizationDisplayService.showLocalizedMessage("info.result.bad");
            } else if (amountOfCorrectAnswers == amountOfQuestions) {
                localizationDisplayService.showLocalizedMessage("info.result.perfect");
            } else {
                localizationDisplayService.showLocalizedMessage("info.result.so-so");
            }
            System.exit(0);
        } catch (ReadFileQuestionsException ex) {
            localizationDisplayService.showLocalizedMessage("input.error.file", ex.getMessage());
            System.exit(1);
        }
    }

    private boolean processTheQuestion(ExamQuestion question) {
        localizationDisplayService.showText(question.toString());
        final var usersAnswer = localizationDisplayService.getInputString();
        if (stopWord.equals(usersAnswer)) {
            log.info("User decided to interrupt exam.");
            localizationDisplayService.showLocalizedMessage("info.result.interrupt");
            System.exit(0);
        }
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
            localizationDisplayService.showLocalizedMessage("input.error.user");
        }
        return 0;
    }
}
