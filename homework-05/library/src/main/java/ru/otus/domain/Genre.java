package ru.otus.domain;

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
@Document("GENRE")
public class Genre {

    @Id
    private String id;

    @NotBlank
    private String genre;

    public Genre(String genre) {
        this.genre = genre;
    }
}
