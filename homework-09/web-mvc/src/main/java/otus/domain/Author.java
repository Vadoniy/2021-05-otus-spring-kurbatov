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
