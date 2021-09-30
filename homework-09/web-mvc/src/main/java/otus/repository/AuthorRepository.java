package otus.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
