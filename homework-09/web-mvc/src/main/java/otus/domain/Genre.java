package otus.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Document("GENRE")
public class Genre {

    @Id
    private String id;

    @NotBlank
    private String genreName;

    public Genre(String genreName) {
        this.genreName = genreName;
    }
}
