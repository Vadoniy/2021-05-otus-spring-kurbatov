package otus.service.impl;

import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.domain.Book;
import otus.repository.AuthorRepository;
import otus.repository.BookRepository;
import otus.repository.CommentRepository;
import otus.repository.GenreRepository;
import otus.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public void addNewBook(Book book) {
        final var title = "title";
        final var authorId = 1L;
        final var genreId = 1L;
        final var author = Optional.ofNullable(authorId)
                .map(authorRepository::getById)
                .orElse(null);
        final var genre = Optional.ofNullable(genreId)
                .map(genreRepository::getById)
                .orElse(null);
        bookRepository.save(new Book(title, author, genre));
    }

    @Override
    @Transactional
    public void updateBook() {
        final var bookId = 1L;
        final var title = "title";
        final var authorId = 1L;
        final var genreId = 1L;
        final var author = Optional.ofNullable(authorId)
                .map(authorRepository::getById)
                .orElse(null);
        final var genre = Optional.ofNullable(genreId)
                .map(genreRepository::getById)
                .orElse(null);
        bookRepository.save(new Book(bookId, title, author, genre));
    }

    @Override
    @Transactional
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}
