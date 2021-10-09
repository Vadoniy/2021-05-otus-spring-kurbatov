package ru.otus.repository;

import ru.otus.domain.ExamQuestion;

import java.util.List;

public interface QuestionDao {

    List<ExamQuestion> getQuestions(String fileNameWithPath);
}
