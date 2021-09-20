package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.batch.MigrationService;
import ru.otus.domain.mongo.Author;
import ru.otus.domain.mongo.Book;
import ru.otus.domain.mongo.Comment;
import ru.otus.domain.mongo.Genre;
import ru.otus.service.LibraryService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class LibraryShellCommands {

    private final LibraryService libraryService;

    private final MigrationService migrationService;

    @ShellMethod(value = "Migrate DB from mongo to relative DB", key = {"migrate", "mdb"})
    public void migrateDB() throws Exception {
        migrationService.startMigration();
    }

    @ShellMethod(value = "Restart failed job by id", key = {"restartJob", "rj"})
    public void restartJob(long jobExecutionId) throws Exception {
        migrationService.restartJob(jobExecutionId);
    }

    @ShellMethod(value = "Add new book to the library", key = {"addBook", "ab"})
    public void addBook() {
        libraryService.addNewBook();
    }

    @ShellMethod(value = "Update book in the library", key = {"updateBook", "ub"})
    public void updateBook() {
        libraryService.updateBook();
    }

    @ShellMethod(value = "Delete book from the library", key = {"deleteBook", "db"})
    public void deleteBook(@ShellOption String bookId) {
        libraryService.deleteBook(bookId);
    }

    @ShellMethod(value = "Get list of books", key = {"listBooks", "lb"})
    public List<Book> getBookList() {
        return libraryService.getBooks();
    }

    @ShellMethod(value = "Add new author to the library's list", key = {"addAuthor", "aa"})
    public void addAuthor() {
        libraryService.addNewAuthor();
    }

    @ShellMethod(value = "Delete author from the library's list", key = {"deleteAuthor", "da"})
    public void deleteAuthor(@ShellOption String authorId) {
        libraryService.deleteAuthor(authorId);
    }

    @ShellMethod(value = "Get list of authors", key = {"listAuthors", "la"})
    public List<Author> getAuthorsList() {
        return libraryService.getAuthors();
    }

    @ShellMethod(value = "Add new genre to the library's list", key = {"addGenre", "ag"})
    public void addGenre() {
        libraryService.addNewGenre();
    }

    @ShellMethod(value = "Delete genre from the library's list", key = {"deleteGenre", "dg"})
    public void deleteGenre(@ShellOption String genreId) {
        libraryService.deleteGenre(genreId);
    }

    @ShellMethod(value = "Get list of genres", key = {"listGenres", "lg"})
    public List<Genre> getGenresList() {
        return libraryService.getGenres();
    }


    @ShellMethod(value = "Get list of comments by bookId", key = {"listCommentsBook", "lcb"})
    public List<Comment> getCommentsByBookId(@ShellOption String bookId) {
        return libraryService.getCommentsByBookId(bookId);
    }

    @ShellMethod(value = "Get list of comments by owner", key = {"listCommentsOwner", "lco"})
    public List<Comment> getCommentsByOwner(@ShellOption String owner) {
        return libraryService.getCommentsByOwner(owner);
    }

    @ShellMethod(value = "Get comment by commentId", key = {"listCommentsId", "lci"})
    public Comment getCommentsById(@ShellOption String commentId) {
        return libraryService.getCommentById(commentId).orElse(null);
    }

    @ShellMethod(value = "Add new comment", key = {"addComment", "ac"})
    public void addNewComment() {
        libraryService.addNewComment();
    }

    @ShellMethod(value = "Delete comment by id", key = {"deleteComment", "dc"})
    public void deleteCommentById(@ShellOption String commentId) {
        libraryService.deleteCommentById(commentId);
    }
}
