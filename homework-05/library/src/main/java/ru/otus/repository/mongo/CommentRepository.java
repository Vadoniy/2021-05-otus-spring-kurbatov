package ru.otus.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.mongo.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByBookId(String bookId);

    List<Comment> findByOwner(String owner);

    void deleteById(long id);

    boolean existsByBookId(String id);

    void deleteByBookId(String id);
}
