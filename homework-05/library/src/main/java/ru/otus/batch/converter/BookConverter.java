package ru.otus.batch.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.mongo.Book;
import ru.otus.domain.relative.BookRel;
import ru.otus.repository.relative.AuthorRelRepository;
import ru.otus.repository.relative.BookRelRepository;
import ru.otus.repository.relative.GenreRelRepository;

@Component
@RequiredArgsConstructor
public class BookConverter implements Converter<Book, BookRel> {

    private final AuthorRelRepository authorRelRepository;

    private final BookRelRepository bookRelRepository;

    private final GenreRelRepository genreRelRepository;

    @Override
    public BookRel convert(Book bookInput) {
        if (bookRelRepository.existsByTitle(bookInput.getTitle())) {
            return bookRelRepository.findByTitle(bookInput.getTitle());
        } else {
            final var genreInput = bookInput.getGenre();
            final var genre = genreRelRepository.findByGenre(genreInput.getGenre());
            final var authorInput = bookInput.getAuthor();
            final var author = authorRelRepository.findByName(authorInput.getName());
            final var book = new BookRel();
            book.setAuthor(author);
            book.setGenre(genre);
            book.setTitle(bookInput.getTitle());
            return book;
        }
    }
}
