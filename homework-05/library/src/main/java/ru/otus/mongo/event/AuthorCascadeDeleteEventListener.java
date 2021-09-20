package ru.otus.mongo.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.domain.mongo.Author;
import ru.otus.repository.mongo.BookRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorCascadeDeleteEventListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Author> event) {
        final var source = event.getSource();
        Optional.ofNullable(source.get("_id"))
                .map(Object::toString)
                .ifPresent(authorId -> {
                    if (bookRepository.existsByAuthorId(authorId)) {
                        bookRepository.deleteByAuthorId(authorId);
                    }
                });
    }
}
