package ru.otus.service;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

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

    List<Comment> getCommentsByBookId(long bookId);

    List<Comment> getCommentsByOwner(String owner);

    Optional<Comment> getCommentById(long id);

    void addNewComment();

    void deleteCommentById(long id);
}