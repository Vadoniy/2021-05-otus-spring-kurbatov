package ru.otus.batch.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.mongo.Comment;
import ru.otus.domain.relative.CommentRel;
import ru.otus.exception.UnknownAuthorException;
import ru.otus.exception.UnknownBookException;
import ru.otus.repository.relative.AuthorRelRepository;
import ru.otus.repository.relative.BookRelRepository;
import ru.otus.repository.relative.CommentRelRepository;
import ru.otus.repository.relative.GenreRelRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentConverter implements Converter<Comment, CommentRel> {

    private final CommentRelRepository commentRelRepository;

    private final AuthorRelRepository authorRelRepository;

    private final BookRelRepository bookRelRepository;

    private final GenreRelRepository genreRelRepository;

    @Override
    public CommentRel convert(Comment comment) {

        if (commentRelRepository.existsByComment(comment.getComment())) {
            return commentRelRepository.findByComment(comment.getComment());
        } else {
            final var book = comment.getBook();
            final var bookAuthor = book.getAuthor();
            final var bookGenre = book.getGenre();
            final var authorRel = Optional.ofNullable(authorRelRepository.findByName(bookAuthor.getName()))
                    .orElseThrow(() -> new UnknownAuthorException("There is no such author " + bookAuthor.getName()));
            final var genreRel = Optional.ofNullable(genreRelRepository.findByGenre(bookGenre.getGenre()))
                    .orElseThrow(() -> new UnknownAuthorException("There is no such genre " + bookGenre.getGenre()));
            final var bookRel = Optional.ofNullable(bookRelRepository.findByTitleAndAuthorAndGenre(book.getTitle(), authorRel, genreRel))
                    .orElseThrow(() -> new UnknownBookException("There is no book with such title, author and genre"));
            final var relComment = new CommentRel();
            relComment.setOwner(comment.getOwner());
            relComment.setBook(bookRel);
            relComment.setComment(comment.getComment());
            return relComment;
        }
    }
}
