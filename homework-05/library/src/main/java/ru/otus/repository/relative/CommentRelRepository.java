package ru.otus.repository.relative;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.relative.CommentRel;

public interface CommentRelRepository extends JpaRepository<CommentRel, Long> {

    boolean existsByComment(String comment);

    CommentRel findByComment(String comment);
}
