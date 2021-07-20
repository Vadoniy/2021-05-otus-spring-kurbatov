package ru.otus.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    void deleteById(long id);
}
