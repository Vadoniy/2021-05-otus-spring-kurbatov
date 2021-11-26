package otus.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.domain.Author;
import reactor.core.publisher.Mono;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

    Mono<Author> findByName(String name);
}
