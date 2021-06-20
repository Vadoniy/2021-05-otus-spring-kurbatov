package ru.otus.dao.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.ExamQuestion;
import ru.otus.domain.FileQuestion;
import ru.otus.exception.ReadFileQuestionsException;
import ru.otus.service.impl.FileQuestionToExamQuestionConverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private final ResourceLoader resourceLoader;

    private final FileQuestionToExamQuestionConverter fileQuestionToExamQuestionConverter;

    @Value("${file-name}")
    private String fileName;

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
