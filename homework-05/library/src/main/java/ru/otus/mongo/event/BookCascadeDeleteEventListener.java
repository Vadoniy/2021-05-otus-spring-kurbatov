package ru.otus.mongo.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.domain.mongo.Book;
import ru.otus.repository.mongo.CommentRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookCascadeDeleteEventListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        final var source = event.getSource();
        Optional.ofNullable(source.get("_id"))
                .map(Object::toString)
                .ifPresent(bookId -> {
                    if (commentRepository.existsByBookId(bookId)) {
                        commentRepository.deleteByBookId(bookId);
                    }
                });
    }
}
