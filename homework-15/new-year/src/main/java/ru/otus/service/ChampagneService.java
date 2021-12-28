package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Party;

@Service
public class ChampagneService {

    public Party takeSomeChampagne(Party party) {
        final var luck = party.getLuck();
        party.setChampagne(luck > 2);
        return party;
    }
}
