package ru.otus.service;

import ru.otus.domain.mongo.Author;
import ru.otus.domain.mongo.Book;
import ru.otus.domain.mongo.Comment;
import ru.otus.domain.mongo.Genre;

import java.util.List;
import java.util.Optional;

public interface LibraryService {

    void addNewBook();

    void updateBook();

    void deleteBook(String id);

    List<Book> getBooks();

    void addNewAuthor();

    void deleteAuthor(String id);

    List<Author> getAuthors();

    void addNewGenre();

    void deleteGenre(String id);

    List<Genre> getGenres();

    List<Comment> getCommentsByBookId(String bookId);

    List<Comment> getCommentsByOwner(String owner);

    Optional<Comment> getCommentById(String id);

    void addNewComment();

    void deleteCommentById(String id);
}