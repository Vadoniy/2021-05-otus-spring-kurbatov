package ru.otus.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.mongo.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {

    void deleteById(long id);

    Genre findByGenre(String genre);
}
