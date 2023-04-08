package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.ChristmasGoose;
import ru.otus.domain.Party;

@Service
public class KitchenService {

    public Party setTable(Party party) {
        final var luck = party.getLuck();
        final var xmasGoose = tryToCookGoose(luck);
        party.setChristmasGoose(xmasGoose);
        return party;
    }

    private ChristmasGoose tryToCookGoose(int luck) {
        if (luck > 8) {
            return new ChristmasGoose("delicious baked goose with crust!");
        } else if (luck > 5) {
            return new ChristmasGoose("slightly burnt goose, but it is still tasty inside!");
        } else if (luck > 3) {
            return new ChristmasGoose("some chicken from microwave...");
        } else if (luck > 1) {
            return new ChristmasGoose("hot boiled doshirak!");
        }
        return null;
    }
}
