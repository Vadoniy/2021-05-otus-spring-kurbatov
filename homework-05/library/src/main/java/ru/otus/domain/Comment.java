package ru.otus.domain;

import lombok.*;

import javax.persistence.*;

@ToString(exclude = {"book"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "COMMENT", nullable = false)
    private String comment;

    @Column(name = "OWNER", nullable = false)
    private String owner;

    @ManyToOne(targetEntity = Book.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    public Comment(String comment, String owner, Book book) {
        this.comment = comment;
        this.owner = owner;
        this.book = book;
    }
}
