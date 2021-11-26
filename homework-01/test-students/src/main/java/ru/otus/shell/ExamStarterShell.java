package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.service.ExamService;

@ShellComponent
@RequiredArgsConstructor
public class ExamStarterShell {

    private final ExamService examService;

    @ShellMethod(value = "Start exam", key = {"start", "se"})
    public String startExam() {
        return examService.startExam();
    }
}
