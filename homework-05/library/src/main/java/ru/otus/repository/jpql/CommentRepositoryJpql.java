package ru.otus.repository.jpql;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Comment;
import ru.otus.repository.CommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpql implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Comment> getByBookId(long bookId) {
        final var query = em.createQuery("select c from Comment c where c.book.id = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public List<Comment> getByOwner(String owner) {
        final var query = em.createQuery("select c from Comment c where c.owner = :owner", Comment.class);
        query.setParameter("owner", owner);
        return query.getResultList();
    }

    @Override
    public Comment getById(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public Comment insert(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public boolean deleteById(long id) {
        final var query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate() == 1;
    }
}
