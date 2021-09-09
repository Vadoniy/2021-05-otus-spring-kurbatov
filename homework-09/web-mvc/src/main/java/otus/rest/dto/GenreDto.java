package otus.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import otus.domain.Genre;

@Getter
@Setter
@Accessors(chain = true)
public class GenreDto {

    private Long id;

    private String genreName;

    public static GenreDto toDto(Genre genre) {
        return new GenreDto()
                .setId(genre.getId())
                .setGenreName(genre.getGenreName());
    }
}
