package otus.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import otus.domain.Author;

@Getter
@Setter
@Accessors(chain = true)
public class AuthorDto {

    private String id;

    private String name;

    public static AuthorDto toDto(Author author) {
        return new AuthorDto()
                .setId(author.getId())
                .setName(author.getName());
    }
}
