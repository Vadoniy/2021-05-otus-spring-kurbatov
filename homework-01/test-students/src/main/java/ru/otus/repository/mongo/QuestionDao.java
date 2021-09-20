package ru.otus.repository.mongo;

import ru.otus.domain.mongo.ExamQuestion;

import java.util.List;

public interface QuestionDao {

    List<ExamQuestion> getQuestions();
}
