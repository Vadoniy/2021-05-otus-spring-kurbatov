package ru.otus.dao.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.jdbc.mapper.AuthorMapper;
import ru.otus.domain.Author;

import java.util.List;

@Repository
@AllArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final JdbcOperations jdbc;

    private final AuthorMapper authorMapper;

    @Override
    public List<Author> getAll() {
        return jdbc.query("SELECT * FROM AUTHOR A", authorMapper);
    }

    @Override
    public Author getById(long id) {
        return jdbc.queryForObject("SELECT * FROM AUTHOR A WHERE A.ID = ?", authorMapper, id);
    }

    @Override
    public boolean insert(Author author) {
        return jdbc.update("INSERT INTO AUTHOR (NAME) VALUES (?)",
                author.getName()) == 1;
    }

    @Override
    public boolean deleteById(long id) {
        return jdbc.update("DELETE FROM AUTHOR WHERE ID = ?", id) == 1;
    }
}
