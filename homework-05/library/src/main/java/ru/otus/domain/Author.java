package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    private long id;

    private String name;

    public Author(long id) {
        this.id = id;
    }

    public Author(String name) {
        this.name = name;
    }
}
