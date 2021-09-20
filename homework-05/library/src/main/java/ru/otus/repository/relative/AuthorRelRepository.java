package ru.otus.repository.relative;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.relative.AuthorRel;

public interface AuthorRelRepository extends JpaRepository<AuthorRel, Long> {

    boolean existsByName(String name);

    AuthorRel findByName(String name);
}
