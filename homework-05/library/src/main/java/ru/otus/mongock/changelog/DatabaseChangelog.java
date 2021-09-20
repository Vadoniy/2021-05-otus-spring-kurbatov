package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.otus.domain.mongo.Book;
import ru.otus.domain.mongo.Comment;
import ru.otus.repository.mongo.AuthorRepository;
import ru.otus.repository.mongo.BookRepository;
import ru.otus.repository.mongo.CommentRepository;
import ru.otus.repository.mongo.GenreRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "vadony", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "vadony")
    public void insertAuthors(MongoDatabase mongoDatabase) {
        final var authorCollection = mongoDatabase.getCollection("AUTHOR");
        final var jennings = new Document().append("name", "Jennings Gary");
        final var glukhovsky = new Document().append("name", "Glukhovskiy Dmitriy");
        final var rowling = new Document().append("name", "Joanne Rowling");
        authorCollection.insertMany(List.of(jennings, glukhovsky, rowling));
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "vadony")
    public void insertGenres(MongoDatabase mongoDatabase) {
        final var authorCollection = mongoDatabase.getCollection("GENRE");
        final var novel = new Document().append("genre", "Novel");
        final var fantasy = new Document().append("genre", "Fantasy");
        final var nonFiction = new Document().append("genre", "Non fiction");
        authorCollection.insertMany(List.of(novel, fantasy, nonFiction));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "vadony")
    public void insertBooks(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        final var jennings = authorRepository.findByName("Jennings Gary");
        final var glukhovsky = authorRepository.findByName("Glukhovskiy Dmitriy");
        final var rowling = authorRepository.findByName("Joanne Rowling");
        final var novel = genreRepository.findByGenre("Novel");
        final var fantasy = genreRepository.findByGenre("Fantasy");
        final var nonFiction = genreRepository.findByGenre("Non fiction");
        final var aztec = new Book("Aztec", jennings, novel);
        final var hp = new Book("Harry Potter", rowling, fantasy);
        final var metro2033 = new Book("Metro 2033", glukhovsky, nonFiction);
        bookRepository.saveAll(List.of(aztec, hp, metro2033));
    }

    @ChangeSet(order = "005", id = "insertComments", author = "vadony")
    public void insertComments(BookRepository bookRepository, CommentRepository commentRepository) {
        final var aztec = bookRepository.findByTitle("Aztec");
        final var hp = bookRepository.findByTitle("Harry Potter");
        final var metro2033 = bookRepository.findByTitle("Metro 2033");
        final var comment0 = new Comment("Like it was yesterday", "Mikstly", aztec);
        final var comment1 = new Comment("I wanted to help!", "Black_One", metro2033);
        final var comment2 = new Comment("Mischief Managed!", "'Harry1997'", hp);
        commentRepository.saveAll(List.of(comment0, comment1, comment2));
    }
}
