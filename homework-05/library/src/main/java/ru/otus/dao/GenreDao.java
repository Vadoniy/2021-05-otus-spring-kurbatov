package ru.otus.dao;

import ru.otus.domain.Author;
import ru.otus.domain.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> getAll();

    Genre getById(long id);

    boolean insert(Genre genre);

    boolean deleteById(long id);
}
