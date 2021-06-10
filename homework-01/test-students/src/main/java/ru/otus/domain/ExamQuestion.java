package ru.otus.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ExamQuestion {

    private String question;

    private List<String> answers;

    private Integer correctAnswer;

    @Override
    public String toString() {
        final var question = new StringBuffer(this.question);
        for (int numberOfAnswer = 0; numberOfAnswer < answers.size(); numberOfAnswer++) {
            question.append('\n').append(numberOfAnswer + 1).append(") ").append(answers.get(numberOfAnswer)).append(";");
        }
        return question.toString();
    }
}
