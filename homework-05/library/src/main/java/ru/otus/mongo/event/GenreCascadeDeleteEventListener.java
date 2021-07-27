package ru.otus.mongo.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.domain.Genre;
import ru.otus.repository.BookRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GenreCascadeDeleteEventListener extends AbstractMongoEventListener<Genre> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Genre> event) {
        final var source = event.getSource();
        Optional.ofNullable(source.get("_id"))
                .map(Object::toString)
                .ifPresent(genreId -> {
                    if (bookRepository.existsByGenreId(genreId)) {
                        bookRepository.deleteByGenreId(genreId);
                    }
                });
    }
}
