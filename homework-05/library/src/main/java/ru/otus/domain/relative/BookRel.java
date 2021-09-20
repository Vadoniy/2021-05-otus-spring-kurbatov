package ru.otus.domain.relative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "BOOK")
@NamedEntityGraph(name = "author-genres-to-book-entity-graph",
        attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
public class BookRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "TITLE", nullable = false, unique = true)
    private String title;

    @ManyToOne(targetEntity = AuthorRel.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_ID")
    private AuthorRel author;

    @ManyToOne(targetEntity = GenreRel.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "GENRE_ID")
    private GenreRel genre;

    public BookRel(String title, AuthorRel author, GenreRel genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }
}