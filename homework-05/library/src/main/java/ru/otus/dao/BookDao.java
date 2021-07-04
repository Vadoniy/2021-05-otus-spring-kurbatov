package ru.otus.dao;

import ru.otus.domain.Book;

import java.util.List;

public interface BookDao {

    boolean insert(Book book);

    Book getById(long id);

    List<Book> getAll();

    boolean deleteById(long id);

    boolean update(Book book);
}
