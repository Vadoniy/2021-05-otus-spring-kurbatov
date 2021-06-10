package ru.otus.dao;

import ru.otus.domain.FileQuestion;

import java.util.List;

public interface QuestionDao {

    List<FileQuestion> readQuestions();
}
