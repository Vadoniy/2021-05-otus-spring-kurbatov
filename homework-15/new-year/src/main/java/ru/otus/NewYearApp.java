package ru.otus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;
import ru.otus.domain.Party;
import ru.otus.flow.PartyPreparationGateway;

@SpringBootApplication
@EnableIntegration
@Slf4j
public class NewYearApp {

    public static void main(String[] args) {
        final var context = SpringApplication.run(NewYearApp.class);
        final var flow = context.getBean(PartyPreparationGateway.class);
        final var partyDescription = flow.process(new Party()).stream()
                .map(Party::toString)
                .findAny()
                .orElse("There is no party here, go home.");
        log.info(partyDescription);
        System.exit(0);
    }
}
