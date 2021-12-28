package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Party;
import ru.otus.domain.Present;

@Service
public class Moll {

    public Party buyPresents(Party party) {
        final var luck = party.getLuck();
        final var present = getPresentsBuyLuck(luck);
        party.setPresent(present);
        return party;
    }

    private Present getPresentsBuyLuck(int luck) {
        if (luck > 7) {
            return new Present("a lot of presents for all relatives and friends", true);
        } else if (luck > 5) {
            return new Present("a lot of presents for friends, but you forgot your relatives", true);
        } else if (luck > 3) {
            return new Present("poor presents for couple of friends", true);
        } else if (luck > 1) {
            return new Present("poor and cheap present for yourself", false);
        }
        return null;
    }
}
