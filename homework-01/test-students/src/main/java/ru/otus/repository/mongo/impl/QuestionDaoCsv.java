package ru.otus.repository.mongo.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import ru.otus.domain.mongo.ExamQuestion;
import ru.otus.domain.mongo.FileQuestion;
import ru.otus.exception.ReadFileQuestionsException;
import ru.otus.repository.mongo.QuestionDao;
import ru.otus.service.impl.FileQuestionToExamQuestionConverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class QuestionDaoCsv implements QuestionDao {

    private final ResourceLoader resourceLoader;

    private final FileQuestionToExamQuestionConverter fileQuestionToExamQuestionConverter;

    private final String fileName;

    public QuestionDaoCsv(ResourceLoader resourceLoader, FileQuestionToExamQuestionConverter fileQuestionToExamQuestionConverter, @Value("${file-name}") String fileName) {
        this.resourceLoader = resourceLoader;
        this.fileQuestionToExamQuestionConverter = fileQuestionToExamQuestionConverter;
        this.fileName = fileName;
    }

    @Override
    public List<ExamQuestion> getQuestions() {
        try (final var reader = new BufferedReader(new InputStreamReader(resourceLoader.getResource(fileName).getInputStream()))) {
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
