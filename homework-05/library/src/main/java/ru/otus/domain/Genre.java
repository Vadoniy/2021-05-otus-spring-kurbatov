package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
