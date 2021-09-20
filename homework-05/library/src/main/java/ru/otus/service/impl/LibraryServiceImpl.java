package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.mongo.Author;
import ru.otus.domain.mongo.Book;
import ru.otus.domain.mongo.Comment;
import ru.otus.domain.mongo.Genre;
import ru.otus.exception.UnknownBookException;
import ru.otus.repository.mongo.AuthorRepository;
import ru.otus.repository.mongo.BookRepository;
import ru.otus.repository.mongo.CommentRepository;
import ru.otus.repository.mongo.GenreRepository;
import ru.otus.service.DisplayService;
import ru.otus.service.LibraryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final DisplayService displayServiceConsole;

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final CommentRepository commentRepository;

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
                .map(authorRepository::findById)
                .orElse(null);
        final var genre = Optional.ofNullable(genreId)
                .map(genreRepository::findById)
                .orElse(null);
        bookRepository.save(new Book(title, author.orElse(null), genre.orElse(null)));
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
                .map(authorRepository::findById)
                .orElse(null);
        final var genre = Optional.ofNullable(genreId)
                .map(genreRepository::findById)
                .orElse(null);
        bookRepository.save(new Book(bookId, title, author.orElse(null), genre.orElse(null)));
    }

    @Override
    @Transactional
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void addNewAuthor() {
        displayServiceConsole.showText("Please, enter author's name:");
        final var name = displayServiceConsole.getInputString();
        authorRepository.save(new Author(name));
    }

    @Override
    @Transactional
    public void deleteAuthor(String id) {
        authorRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional
    public void addNewGenre() {
        displayServiceConsole.showText("Please, enter author's genre:");
        final var genre = displayServiceConsole.getInputString();
        genreRepository.save(new Genre(genre));
    }

    @Override
    @Transactional
    public void deleteGenre(String id) {
        genreRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByBookId(String bookId) {
        return commentRepository.findByBookId(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByOwner(String owner) {
        return commentRepository.findByOwner(owner);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> getCommentById(String id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public void addNewComment() {
        displayServiceConsole.showText("Please, enter an ID of the book you want to comment:");
        final var bookId = getId(displayServiceConsole.getInputString());
        final var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new UnknownBookException("Error to find book with id " + bookId));
        displayServiceConsole.showText("Please, enter your nickname:");
        final var nickname = displayServiceConsole.getInputString();
        displayServiceConsole.showText("What can you want to tell about the book?");
        final var comment = displayServiceConsole.getInputString();
        commentRepository.save(new Comment(comment, nickname, book));
    }

    @Override
    @Transactional
    public void deleteCommentById(String id) {
        commentRepository.deleteById(id);
    }

    private String getId(String input) {
        return input;
    }
}
