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
@Table(name = "GENRE")
public class GenreRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "GENRE", nullable = false, unique = true)
    private String genre;

    public GenreRel(long id) {
        this.id = id;
    }

    public GenreRel(String genre) {
        this.genre = genre;
    }
}