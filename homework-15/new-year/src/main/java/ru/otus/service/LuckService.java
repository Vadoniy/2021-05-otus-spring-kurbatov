package ru.otus.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.domain.Party;

import java.util.Random;

@Service
@Slf4j
public class LuckService {

    final Random random = new Random();

    public Party getLuck(Party party) {
        party.setLuck(random.nextInt(10));
        log.info("Luck level: " + party.getLuck());
        return party;
    }
}
