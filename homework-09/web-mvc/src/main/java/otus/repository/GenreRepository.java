package otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.domain.Genre;
import reactor.core.publisher.Mono;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

    Mono<Genre> findByGenreName(String genreName);
}
