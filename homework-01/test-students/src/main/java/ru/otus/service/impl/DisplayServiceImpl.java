package ru.otus.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.exception.ShowTextException;
import ru.otus.service.DisplayService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

@Service
@Slf4j
public class DisplayServiceImpl implements DisplayService {

    @Value("${stop-word}")
    private String stopWord;

    @Override
    public void showText(String textToShow, OutputStream outputStream) {
        try {
            outputStream.write(textToShow.getBytes());
            outputStream.write('\n');
        } catch (IOException e) {
            throw new ShowTextException(e);
        }
    }

    @Override
    public String getInputString() {
        final var br = new BufferedReader(new InputStreamReader(System.in));
        try {
            final var input = br.readLine();
            if (stopWord.equals(input)) {
                log.info("User decided to interrupt exam.");
                System.out.println("You have typed exit, we are sorry that you are going.");
                System.exit(0);
            }
            return input;
        } catch (IOException e) {
            log.error("Wrong input {}", e.getMessage());
            System.err.println("Wrong input, try again please");
        }
        return "0";
    }
}
