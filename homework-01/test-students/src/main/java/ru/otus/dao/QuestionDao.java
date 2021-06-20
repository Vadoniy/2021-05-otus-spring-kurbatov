package ru.otus.dao;

import ru.otus.domain.ExamQuestion;

import java.util.List;

public interface QuestionDao {

    List<ExamQuestion> getQuestions();
}
