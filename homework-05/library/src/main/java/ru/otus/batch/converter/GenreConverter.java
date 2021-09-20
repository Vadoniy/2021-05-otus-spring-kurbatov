package ru.otus.batch.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.domain.mongo.Genre;
import ru.otus.domain.relative.GenreRel;
import ru.otus.repository.relative.GenreRelRepository;

@Component
@RequiredArgsConstructor
public class GenreConverter implements Converter<Genre, GenreRel> {

    private final GenreRelRepository genreRelRepository;

    @Override
    public GenreRel convert(Genre genre) {

        if (genreRelRepository.existsByGenre(genre.getGenre())) {
            return genreRelRepository.findByGenre(genre.getGenre());
        } else {
            final var genreRel = new GenreRel();
            genreRel.setGenre(genre.getGenre());
            return genreRel;
        }
    }
}
