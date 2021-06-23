package ru.otus.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.exception.ShowTextException;
import ru.otus.service.DisplayService;

import java.io.PrintStream;
import java.util.Scanner;

@Service
@Slf4j
public class DisplayServiceImpl implements DisplayService {

    private final String stopWord;

    private final PrintStream printStreamOut;

    private final Scanner scanner;

    public DisplayServiceImpl(@Value("${stop-word}") String stopWord) {
        this.stopWord = stopWord;
        this.scanner = new Scanner(System.in);
        this.printStreamOut = System.out;
    }

    @Override
    public void showText(String textToShow) {
        try {
            printStreamOut.println(textToShow);
        } catch (Exception e) {
            throw new ShowTextException(e);
        }
    }

    @Override
    public void showText(String textToShow, String... args) {
        try {
            printStreamOut.println(String.format(textToShow, args));
        } catch (Exception e) {
            throw new ShowTextException(e);
        }
    }

    @Override
    public String getInputString() {
        final var stringBuilder = new StringBuilder();
        try {
            stringBuilder.append(scanner.nextLine());
        } catch (Exception e) {
            log.error("Wrong input {}", e.getMessage());
            showText("Wrong input, try again please");
        }
        return stringBuilder.toString();
    }
}
