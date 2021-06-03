package ru.otus.dao.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class QuestionDaoImpl implements QuestionDao {

    @Override
    public List<Question> readQuestions() {

        final var questions = new ArrayList<Question>();

        try (final var reader = new BufferedReader(new InputStreamReader(Question.class.getResourceAsStream("/test.csv")))) {
            questions.addAll(new CsvToBeanBuilder(reader)
                    .withType(Question.class)
                    .build()
                    .parse());
        } catch (Exception e) {
            log.error("Can't read input resource! Description: {}", e.getMessage());
        }

        return questions;
    }
}
