package otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.domain.Book;
import reactor.core.publisher.Mono;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Mono<Void> deleteByAuthorId(String authorId);

    Mono<Void> deleteByGenreId(String genreId);

}
