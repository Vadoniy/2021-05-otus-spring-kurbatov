package ru.otus.domain.mongo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Document("COMMENT")
public class Comment {

    @Id
    private String id;

    @NotBlank
    private String comment;

    @NotBlank
    private String owner;

    @NotNull
    private Book book;

    public Comment(String comment, String owner, Book book) {
        this.comment = comment;
        this.owner = owner;
        this.book = book;
    }
}
