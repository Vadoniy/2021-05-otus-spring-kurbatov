package otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import otus.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBookId(long bookId);

    List<Comment> findByOwner(String owner);

    void deleteById(long id);
}
