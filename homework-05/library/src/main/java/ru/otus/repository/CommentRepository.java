package ru.otus.repository;

import ru.otus.domain.Author;
import ru.otus.domain.Comment;

import java.util.List;

public interface CommentRepository {

    List<Comment> getByBookId(long bookId);

    List<Comment> getByOwner(String owner);

    Comment getById(long id);

    Comment insert(Comment comment);

    boolean deleteById(long id);
}
