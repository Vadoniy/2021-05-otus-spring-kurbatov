package ru.otus.repository.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import ru.otus.domain.ExamQuestion;
import ru.otus.domain.FileQuestion;
import ru.otus.exception.ReadFileQuestionsException;
import ru.otus.repository.QuestionDao;
import ru.otus.service.impl.FileQuestionToExamQuestionConverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionDaoCsv implements QuestionDao {

    private final FileQuestionToExamQuestionConverter fileQuestionToExamQuestionConverter;

    private final ResourceLoader resourceLoader;

    @Override
    public List<ExamQuestion> getQuestions(String fileNameWithPath) {
        try (final var reader = new BufferedReader(
                new InputStreamReader(resourceLoader.getResource(fileNameWithPath).getInputStream(), StandardCharsets.UTF_8))
        ) {
            return new CsvToBeanBuilder<FileQuestion>(reader)
                    .withType(FileQuestion.class)
                    .build()
                    .parse()
                    .stream()
                    .map(fileQuestionToExamQuestionConverter::convert)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to read csv file: {}", e.getMessage());
            throw new ReadFileQuestionsException(e);
        }
    }
}
