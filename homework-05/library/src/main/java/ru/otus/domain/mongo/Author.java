package ru.otus.domain.mongo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Document("AUTHOR")
public class Author {

    @Id
    private String id;

    @NotBlank
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
