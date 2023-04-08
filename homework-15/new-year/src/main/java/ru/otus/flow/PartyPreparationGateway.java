package ru.otus.flow;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.domain.Party;

import java.util.Collection;

@MessagingGateway
public interface PartyPreparationGateway {

    @Gateway(requestChannel = "partyPreparationChannel", replyChannel = "partyChannel")
    Collection<Party> process(Party party);
}
