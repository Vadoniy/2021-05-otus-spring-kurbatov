package ru.otus.repository.relative;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.relative.AuthorRel;
import ru.otus.domain.relative.BookRel;
import ru.otus.domain.relative.GenreRel;

public interface BookRelRepository extends JpaRepository<BookRel, Long> {

    boolean existsByTitle(String title);

    BookRel findByTitle(String title);

    BookRel findByTitleAndAuthorAndGenre(String title, AuthorRel authorRel, GenreRel genreRel);

}
