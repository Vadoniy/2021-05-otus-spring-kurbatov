package ru.otus.dao.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.FileQuestion;
import ru.otus.exception.BusinessException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private final ResourceLoader resourceLoader;

    @Value("${file-name}")
    private String fileName;

    @Override
    public List<FileQuestion> readQuestions() {
        final var questions = new ArrayList<FileQuestion>();

        try (final var reader = new BufferedReader(new InputStreamReader(resourceLoader.getResource(fileName).getInputStream()))) {
            questions.addAll(new CsvToBeanBuilder<FileQuestion>(reader)
                    .withType(FileQuestion.class)
                    .build()
                    .parse());
        } catch (Exception e) {
            log.error("Failed to read csv file: {}", e.getMessage());
            throw new BusinessException(e.getMessage());
        }

        return questions;
    }
}
