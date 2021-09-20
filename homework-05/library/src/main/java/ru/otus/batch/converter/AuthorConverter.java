package ru.otus.batch.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.mongo.Author;
import ru.otus.domain.relative.AuthorRel;
import ru.otus.repository.relative.AuthorRelRepository;

@Component
@RequiredArgsConstructor
public class AuthorConverter implements Converter<Author, AuthorRel> {

    private final AuthorRelRepository authorRelRepository;

    @Override
    public AuthorRel convert(Author author) {

        if (authorRelRepository.existsByName(author.getName())) {
            return authorRelRepository.findByName(author.getName());
        } else {
            final var authorRel = new AuthorRel();
            authorRel.setName(author.getName());
            return authorRel;
        }
    }
}
