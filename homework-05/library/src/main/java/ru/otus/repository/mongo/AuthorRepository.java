package ru.otus.repository.mongo;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.mongo.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

    void deleteById(long id);

    Author findByName(String name);
}
