package otus.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@ToString(exclude = "bookList")
@Table(name = "GENRE")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GENRE", nullable = false, unique = true)
    private String genreName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "genre", cascade = CascadeType.REMOVE)
    private List<Book> bookList;

    public Genre(long id) {
        this.id = id;
    }

    public Genre(String genreName) {
        this.genreName = genreName;
    }
}
