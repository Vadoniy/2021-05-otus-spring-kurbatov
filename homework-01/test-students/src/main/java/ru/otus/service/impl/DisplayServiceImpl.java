package ru.otus.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.configuration.BusinessConfigurationProperties;
import ru.otus.exception.ShowTextException;
import ru.otus.service.DisplayService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DisplayServiceImpl implements DisplayService {

    private final BusinessConfigurationProperties businessConfigurationProperties;

    private final LocalizationServiceImpl localizationService;

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
            if (businessConfigurationProperties.getStopWord().equals(input)) {
                log.info("User decided to interrupt exam.");
                System.out.println(localizationService.getLocalizedMessage("info.result.interrupt"));
                System.exit(0);
            }
            return input;
        } catch (IOException e) {
            log.error("Wrong input {}", e.getMessage());
            System.err.println(localizationService.getLocalizedMessage("input.error.input"));
        }
        return "0";
    }
}
