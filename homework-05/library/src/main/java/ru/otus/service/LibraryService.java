package ru.otus.service;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

public interface LibraryService {

    void addNewBook();

    void updateBook();

    void deleteBook(long id);

    List<Book> getBooks();

    void addNewAuthor();

    void deleteAuthor(long id);

    List<Author> getAuthors();

    void addNewGenre();

    void deleteGenre(long id);

    List<Genre> getGenres();
}