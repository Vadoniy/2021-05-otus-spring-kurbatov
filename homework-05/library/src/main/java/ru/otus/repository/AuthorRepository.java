package ru.otus.repository;


import ru.otus.domain.Author;

import java.util.List;

public interface AuthorRepository {

    List<Author> getAll();

    Author getById(long id);

    Author insert(Author author);

    boolean deleteById(long id);
}
