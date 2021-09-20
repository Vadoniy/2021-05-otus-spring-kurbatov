package ru.otus.repository.relative;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.relative.GenreRel;

public interface GenreRelRepository extends JpaRepository<GenreRel, Long> {

    boolean existsByGenre(String genre);

    GenreRel findByGenre(String genre);
}
