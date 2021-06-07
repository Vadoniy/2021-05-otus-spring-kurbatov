package ru.otus.dao.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;
import ru.otus.exception.BusinessException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class QuestionDaoImpl implements QuestionDao {

    private final Resource resource;

    public QuestionDaoImpl(Resource resource) {
        this.resource = resource;
    }

    @Override
    public List<Question> readQuestions() {

        final var questions = new ArrayList<Question>();

        try (final var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            questions.addAll(new CsvToBeanBuilder<Question>(reader)
                    .withType(Question.class)
                    .build()
                    .parse());
        } catch (Exception e) {
            log.error("Failed to read csv file: {}", e.getMessage());
            throw new BusinessException(e.getMessage());
        }

        return questions;
    }
}
