package otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import otus.repository.AuthorRepository;
import otus.repository.BookRepository;
import otus.repository.GenreRepository;

import java.util.ArrayList;
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
        final var jennings = new Document().append("_id", "1").append("name", "Jennings Gary");
        final var glukhovsky = new Document().append("_id", "2").append("name", "Glukhovskiy Dmitriy");
        final var rowling = new Document().append("_id", "3").append("name", "Joanne Rowling");
        authorCollection.insertMany(List.of(jennings, glukhovsky, rowling));
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "vadony")
    public void insertGenres(MongoDatabase mongoDatabase) {
        final var authorCollection = mongoDatabase.getCollection("GENRE");
        final var novel = new Document().append("_id", "1").append("genreName", "Novel");
        final var fantasy = new Document().append("_id", "2").append("genreName", "Fantasy");
        final var nonFiction = new Document().append("_id", "3").append("genreName", "Non fiction");
        authorCollection.insertMany(List.of(novel, fantasy, nonFiction));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "vadony")
    public void insertBooks(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, MongoDatabase mongoDatabase) {
        final var jennings = new BasicDBObject();
        jennings.put("_id", "1");
        jennings.put("name", "Jennings Gary");
        final var glukhovsky = new BasicDBObject();
        glukhovsky.put("_id", "2");
        glukhovsky.put("name", "Glukhovskiy Dmitriy");
        final var rowling = new BasicDBObject();
        rowling.put("_id", "3");
        rowling.put("name", "Joanne Rowling");
        final var novel = new BasicDBObject();
        novel.put("_id", "1");
        novel.put("genreName", "Novel");
        final var fantasy = new BasicDBObject();
        fantasy.put("_id", "2");
        fantasy.put("genreName", "Fantasy");
        final var nonFiction = new BasicDBObject();
        nonFiction.put("_id", "3");
        nonFiction.put("genreName", "Non fiction");
        final MongoCollection<Document> books = mongoDatabase.getCollection("BOOK");
        final var booksToAdd = new ArrayList<Document>(3);
        booksToAdd.add(new Document()
                .append("_id", "1")
                .append("title", "Aztec")
                .append("author", jennings)
                .append("genre", novel));
        booksToAdd.add(new Document()
                .append("_id", "2")
                .append("title", "Harry Potter")
                .append("author", rowling)
                .append("genre", fantasy));
        booksToAdd.add(new Document()
                .append("_id", "3")
                .append("title", "Metro 2033")
                .append("author", glukhovsky)
                .append("genre", nonFiction));
        books.insertMany(booksToAdd);
    }
}