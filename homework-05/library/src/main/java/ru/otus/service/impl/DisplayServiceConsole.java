package ru.otus.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.exception.DisplayServiceException;
import ru.otus.service.DisplayService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
@Slf4j
public class DisplayServiceConsole implements DisplayService {

    private final PrintStream printStreamOut;

    private final Scanner scanner;

    public DisplayServiceConsole(@Value("#{T(java.lang.System).out}") PrintStream out,
                                  @Value("#{T(java.lang.System).in}") InputStream in) {
        this.scanner = new Scanner(in);
        this.printStreamOut = out;
    }

    @Override
    public void showText(String textToShow) {
        try {
            printStreamOut.println(textToShow);
        } catch (Exception e) {
            throw new DisplayServiceException(e);
        }
    }

    @Override
    public void showText(String textToShow, String... args) {
        try {
            printStreamOut.println(String.format(textToShow, args));
        } catch (Exception e) {
            throw new DisplayServiceException(e);
        }
    }

    @Override
    public String getInputString() {
        try {
            return scanner.nextLine();
        } catch (Exception e) {
            throw new DisplayServiceException(e);
        }
    }
}
