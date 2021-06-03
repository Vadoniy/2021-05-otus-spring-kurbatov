package ru.otus.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question {

    @CsvBindByPosition(position = 0)
    private String question;

    @CsvBindByPosition(position = 1)
    private String answer1;

    @CsvBindByPosition(position = 2)
    private String answer2;

    @CsvBindByPosition(position = 3)
    private Integer correctAnswer;
}