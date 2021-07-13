package ru.otus.repository;

import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book insert(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    boolean deleteById(long id);

    boolean update(Book book);
}
