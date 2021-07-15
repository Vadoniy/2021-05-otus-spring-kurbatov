package ru.otus.repository.jpql;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;
import ru.otus.repository.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpql implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Book insert(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> getAll() {
        final var entityGraph = em.getEntityGraph("author-genres-to-book-entity-graph");
        final var query = em.createQuery("select b from Book b join b.author join b.genre", Book.class);
        query.setHint(EntityGraph.EntityGraphType.FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public boolean deleteById(long id) {
        final var query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate() == 1;
    }

    @Override
    public boolean update(Book book) {
        final var query = em.createQuery("update Book b " +
                "set b.author = :author," +
                " b.genre = :genre," +
                " b.title = :title " +
                "where b.id = :id");
        query.setParameter("title", book.getTitle());
        query.setParameter("id", book.getId());
        query.setParameter("genre", book.getGenre());
        query.setParameter("author", book.getAuthor());
        return query.executeUpdate() == 1;
    }
}