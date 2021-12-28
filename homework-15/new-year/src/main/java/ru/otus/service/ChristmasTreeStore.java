package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.ChristmasTree;
import ru.otus.domain.Party;

@Service
public class ChristmasTreeStore {

    public Party setUpChristmasTree(Party party) {
        final var luck = party.getLuck();
        final var xmasTree = getChristmasTree(luck);
        party.setChristmasTree(xmasTree);
        return party;
    }

    private ChristmasTree getChristmasTree(int luck) {
        if (luck > 7) {
            return new ChristmasTree("tall beautiful christmas tree", true);
        } else if (luck > 5) {
            return new ChristmasTree("not bad christmas tree", true);
        } else if (luck > 3) {
            return new ChristmasTree("poor christmas tree", true);
        } else if (luck > 1) {
            return new ChristmasTree("poor christmas tree", false);
        }
        return null;
    }
}
