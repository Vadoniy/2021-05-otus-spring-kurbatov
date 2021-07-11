package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Genre {

    private long id;

    private String genre;

    public Genre(long id) {
        this.id = id;
    }

    public Genre(String genre) {
        this.genre = genre;
    }
}
