package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.exception.UnknownBookException;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentRepository;
import ru.otus.repository.GenreRepository;
import ru.otus.service.DisplayService;
import ru.otus.service.LibraryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final DisplayService displayServiceConsole;

    private final BookRepository bookRepositoryJpql;

    private final AuthorRepository authorRepositoryJpql;

    private final GenreRepository genreRepositoryJpql;

    private final CommentRepository commentRepositoryJpql;

    @Override
    @Transactional
    public void addNewBook() {
        displayServiceConsole.showText("Please, enter a book title:");
        final var title = displayServiceConsole.getInputString();
        displayServiceConsole.showText("Please, enter ID of the author (you can find out authors ids by command):");
        final var authorId = getId(displayServiceConsole.getInputString());
        displayServiceConsole.showText("Please, enter ID of genre of your book (you can find out genres ids by command):");
        final var genreId = getId(displayServiceConsole.getInputString());
        final var author = Optional.ofNullable(authorId)
                .map(authorRepositoryJpql::getById)
                .orElse(null);
        final var genre = Optional.ofNullable(genreId)
                .map(genreRepositoryJpql::getById)
                .orElse(null);
        bookRepositoryJpql.insert(new Book(title, author, genre));
    }

    @Override
    @Transactional
    public void updateBook() {
        displayServiceConsole.showText("Please, enter an ID of the book you want to update:");
        final var bookId = getId(displayServiceConsole.getInputString());
        displayServiceConsole.showText("Please, enter a new book title:");
        final var title = displayServiceConsole.getInputString();
        displayServiceConsole.showText("Please, enter ID of the new author (you can find out authors ids by command):");
        final var authorId = getId(displayServiceConsole.getInputString());
        displayServiceConsole.showText("Please, enter ID of new genre of your book (you can find out genres ids by command):");
        final var genreId = getId(displayServiceConsole.getInputString());
        final var author = Optional.ofNullable(authorId)
                .map(authorRepositoryJpql::getById)
                .orElse(null);
        final var genre = Optional.ofNullable(genreId)
                .map(genreRepositoryJpql::getById)
                .orElse(null);
        bookRepositoryJpql.update(new Book(bookId, title, author, genre));
    }

    @Override
    @Transactional
    public void deleteBook(long id) {
        bookRepositoryJpql.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooks() {
        return bookRepositoryJpql.getAll();
    }

    @Override
    public void addNewAuthor() {
        displayServiceConsole.showText("Please, enter author's name:");
        final var name = displayServiceConsole.getInputString();
        authorRepositoryJpql.insert(new Author(name));
    }

    @Override
    public void deleteAuthor(@ShellOption long id) {
        authorRepositoryJpql.deleteById(id);
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepositoryJpql.getAll();
    }

    @Override
    public void addNewGenre() {
        displayServiceConsole.showText("Please, enter author's genre:");
        final var genre = displayServiceConsole.getInputString();
        genreRepositoryJpql.insert(new Genre(genre));
    }

    @Override
    public void deleteGenre(long id) {
        genreRepositoryJpql.deleteById(id);
    }

    @Override
    public List<Genre> getGenres() {
        return genreRepositoryJpql.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByBookId(long bookId) {
        return commentRepositoryJpql.getByBookId(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByOwner(String owner){
        return commentRepositoryJpql.getByOwner(owner);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getCommentsById(long id){
        return commentRepositoryJpql.getById(id);
    }

    @Override
    @Transactional
    public void addNewComment() {
        displayServiceConsole.showText("Please, enter an ID of the book you want to comment:");
        final var bookId = getId(displayServiceConsole.getInputString());
        final var book = bookRepositoryJpql.getById(bookId)
                .orElseThrow(() -> new UnknownBookException("Error to find book with id " + bookId));
        displayServiceConsole.showText("Please, enter your nickname:");
        final var nickname = displayServiceConsole.getInputString();
        displayServiceConsole.showText("What can you want to tell about the book?");
        final var comment = displayServiceConsole.getInputString();
        commentRepositoryJpql.insert(new Comment(comment, nickname, book));
    }

    @Override
    @Transactional
    public void deleteCommentById(long id) {
        commentRepositoryJpql.deleteById(id);
    }

    private Long getId(String input) {
        return Optional.ofNullable(input)
                .filter(s -> !Strings.isBlank(s))
                .map(Long::parseLong)
                .orElse(null);
    }
}
