package ru.otus.dao;


import ru.otus.domain.Author;
import ru.otus.domain.Book;

import java.util.List;

public interface AuthorDao {

    List<Author> getAll();

    Author getById(long id);

    boolean insert(Author author);

    boolean deleteById(long id);
}
