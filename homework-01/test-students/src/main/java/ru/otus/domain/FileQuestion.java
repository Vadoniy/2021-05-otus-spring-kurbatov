package ru.otus.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class FileQuestion {

    @CsvBindByPosition(position = 0)
    private String questionNumber;

    @CsvBindByPosition(position = 1)
    private String question;

    @CsvBindByPosition(position = 2)
    private String answers;

    @CsvBindByPosition(position = 3)
    private Integer correctAnswer;
}