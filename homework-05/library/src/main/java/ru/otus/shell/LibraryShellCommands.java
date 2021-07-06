package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.LibraryService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class LibraryShellCommands {

    private final LibraryService libraryServiceImpl;

    @ShellMethod(value = "Add new book to the library", key = {"addBook", "ab"})
    public void addBook() {
        libraryServiceImpl.addNewBook();
    }

    @ShellMethod(value = "Update book in the library", key = {"updateBook", "ub"})
    public void updateBook() {
        libraryServiceImpl.updateBook();
    }

    @ShellMethod(value = "Delete book from the library", key = {"deleteBook", "db"})
    public void deleteBook(@ShellOption long bookId) {
        libraryServiceImpl.deleteBook(bookId);
    }

    @ShellMethod(value = "Get list of books", key = {"listBooks", "lb"})
    public List<Book> getBookList() {
        return libraryServiceImpl.getBooks();
    }

    @ShellMethod(value = "Add new author to the library's list", key = {"addAuthor", "aa"})
    public void addAuthor() {
        libraryServiceImpl.addNewAuthor();
    }

    @ShellMethod(value = "Delete author from the library's list", key = {"deleteAuthor", "da"})
    public void deleteAuthor(@ShellOption long authorId) {
        libraryServiceImpl.deleteAuthor(authorId);
    }

    @ShellMethod(value = "Get list of authors", key = {"listAuthors", "la"})
    public List<Author> getAuthorsList() {
        return libraryServiceImpl.getAuthors();
    }

    @ShellMethod(value = "Add new genre to the library's list", key = {"addGenre", "ag"})
    public void addGenre() {
        libraryServiceImpl.addNewGenre();
    }

    @ShellMethod(value = "Delete genre from the library's list", key = {"deleteGenre", "dg"})
    public void deleteGenre(@ShellOption long genreId) {
        libraryServiceImpl.deleteGenre(genreId);
    }

    @ShellMethod(value = "Get list of genres", key = {"listGenres", "lg"})
    public List<Genre> getGenresList() {
        return libraryServiceImpl.getGenres();
    }
}
