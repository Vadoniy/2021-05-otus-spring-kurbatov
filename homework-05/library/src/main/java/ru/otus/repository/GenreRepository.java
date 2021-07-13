package ru.otus.repository;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenreRepository {

    List<Genre> getAll();

    Genre getById(long id);

    Genre insert(Genre genre);

    boolean deleteById(long id);
}
