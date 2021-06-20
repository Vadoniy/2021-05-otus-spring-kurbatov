package ru.otus.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileQuestion {

    @CsvBindByPosition(position = 0)
    private String question;

    @CsvBindByPosition(position = 1)
    private String answers;

    @CsvBindByPosition(position = 2)
    private Integer correctAnswer;
}