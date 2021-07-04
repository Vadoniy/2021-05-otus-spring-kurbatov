package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.dao.jdbc.BookDaoJdbc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.SQLException;

@SpringBootApplication
public class AppLibrary {

    public static void main(String[] args) throws SQLException {
        final var context = SpringApplication.run(AppLibrary.class);

        final var bookDao = context.getBean(BookDaoJdbc.class);
        final var books = bookDao.getAll();
        books.forEach(System.out::println);
        System.out.println(bookDao.deleteById(1));
        final var books2 = bookDao.getAll();
        books2.forEach(System.out::println);
        final var newBook = new Book(100, "Title", new Author(0), new Genre(0));
        bookDao.insert(newBook);
        bookDao.getAll().forEach(System.out::println);
        final var newBook2 = new Book(100, "Title2", new Author(1), new Genre(1));
        bookDao.update(newBook2);
        bookDao.getAll().forEach(System.out::println);
    }
}
