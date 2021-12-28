package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Present {

    private String description;

    private boolean isPacked;
}
