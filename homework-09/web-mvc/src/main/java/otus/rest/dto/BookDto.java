package otus.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import otus.domain.Author;
import otus.domain.Book;
import otus.domain.Genre;
import otus.exception.UnknownAuthorException;

import java.util.Optional;

@Getter
@Setter
@Accessors(chain = true)
public class BookDto {

    private String id;

    private String title;

    private AuthorDto author;

    private GenreDto genre;

    public static BookDto toDto(Book book) {
        final var authorDto = new AuthorDto()
                .setId(Optional.ofNullable(book.getAuthor()).map(Author::getId).orElseThrow(() -> new UnknownAuthorException("Unknown author.")))
                .setName(Optional.ofNullable(book.getAuthor()).map(Author::getName).orElse(""));
        final var genreDto = new GenreDto()
                .setId(Optional.ofNullable(book.getGenre()).map(Genre::getId).orElseThrow(() -> new UnknownAuthorException("Unknown genre.")))
                .setGenreName(Optional.ofNullable(book.getGenre()).map(Genre::getGenreName).orElse(""));
        return new BookDto()
                .setId(book.getId())
                .setTitle(book.getTitle())
                .setAuthor(authorDto)
                .setGenre(genreDto);
    }
}
