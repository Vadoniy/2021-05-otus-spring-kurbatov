package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.Book;

public interface BookRepository extends MongoRepository<Book, String> {
    Book findByTitle(String title);
}
