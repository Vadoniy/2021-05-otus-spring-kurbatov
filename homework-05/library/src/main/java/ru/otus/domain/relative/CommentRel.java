package ru.otus.domain.relative;

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
public class CommentRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "COMMENT", nullable = false)
    private String comment;

    @Column(name = "OWNER", nullable = false)
    private String owner;

    @ManyToOne(targetEntity = BookRel.class, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "BOOK_ID")
    private BookRel book;

    public CommentRel(String comment, String owner, BookRel book) {
        this.comment = comment;
        this.owner = owner;
        this.book = book;
    }
}