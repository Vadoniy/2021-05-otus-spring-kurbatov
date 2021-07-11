package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.DisplayService;
import ru.otus.service.LibraryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final DisplayService displayServiceConsole;

    private final BookDao bookDaoJdbc;

    private final AuthorDao authorDaoJdbc;

    private final GenreDao genreDaoJdbc;

    @Override
    public void addNewBook() {
        displayServiceConsole.showText("Please, enter a book title:");
        final var title = displayServiceConsole.getInputString();
        displayServiceConsole.showText("Please, enter ID of the author (you can find out authors ids by command):");
        final var authorId = Integer.parseInt(displayServiceConsole.getInputString());
        displayServiceConsole.showText("Please, enter ID of genre of your book (you can find out genres ids by command):");
        final var genreId = Integer.parseInt(displayServiceConsole.getInputString());
        final var author = authorDaoJdbc.getById(authorId);
        final var genre = genreDaoJdbc.getById(genreId);
        bookDaoJdbc.insert(new Book(title, author, genre));
    }

    @Override
    public void updateBook() {
        displayServiceConsole.showText("Please, enter an ID of the book you want to update:");
        final var bookId = Long.parseLong(displayServiceConsole.getInputString());
        displayServiceConsole.showText("Please, enter a new book title:");
        final var title = displayServiceConsole.getInputString();
        displayServiceConsole.showText("Please, enter ID of the new author (you can find out authors ids by command):");
        final var authorId = Long.parseLong(displayServiceConsole.getInputString());
        displayServiceConsole.showText("Please, enter ID of new genre of your book (you can find out genres ids by command):");
        final var genreId = Long.parseLong(displayServiceConsole.getInputString());
        final var author = authorDaoJdbc.getById(authorId);
        final var genre = genreDaoJdbc.getById(genreId);
        bookDaoJdbc.update(new Book(bookId, title, author, genre));
    }

    @Override
    public void deleteBook(long id) {
        bookDaoJdbc.deleteById(id);
    }

    @Override
    public List<Book> getBooks() {
        return bookDaoJdbc.getAll();
    }

    @Override
    public void addNewAuthor() {
        displayServiceConsole.showText("Please, enter author's name:");
        final var name = displayServiceConsole.getInputString();
        authorDaoJdbc.insert(new Author(name));
    }

    @Override
    public void deleteAuthor(@ShellOption long id) {
        authorDaoJdbc.deleteById(id);
    }

    @Override
    public List<Author> getAuthors() {
        return authorDaoJdbc.getAll();
    }

    @Override
    public void addNewGenre() {
        displayServiceConsole.showText("Please, enter author's genre:");
        final var genre = displayServiceConsole.getInputString();
        genreDaoJdbc.insert(new Genre(genre));
    }

    @Override
    public void deleteGenre(long id) {
        genreDaoJdbc.deleteById(id);
    }

    @Override
    public List<Genre> getGenres() {
        return genreDaoJdbc.getAll();
    }
}
