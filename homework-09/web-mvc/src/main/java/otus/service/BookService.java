package otus.service;

import otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    void addNewBook(Book book);

    void updateBook();

    void deleteBook(long id);

    List<Book> getBooks();

    Optional<Book> getBookById(long id);

    Book saveBook(Book book);

}
