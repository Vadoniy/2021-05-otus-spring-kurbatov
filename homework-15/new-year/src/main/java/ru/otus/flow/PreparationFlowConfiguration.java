package ru.otus.flow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import ru.otus.service.*;

@Configuration
public class PreparationFlowConfiguration {

    public static final String BUY_PRESENTS_METHOD = "buyPresents";

    public static final String GET_LUCK_METHOD = "getLuck";

    public static final String SET_TABLE_METHOD = "setTable";

    public static final String SET_UP_CHRISTMAS_TREE_METHOD = "setUpChristmasTree";

    public static final String TAKE_SOME_CHAMPAGNE_METHOD = "takeSomeChampagne";

    @Bean
    public QueueChannel partyPreparationChannel() {
        return MessageChannels.queue().get();
    }

    @Bean
    public QueueChannel mollChannel() {
        return MessageChannels.queue().get();
    }

    @Bean
    public QueueChannel kitchenChannel() {
        return MessageChannels.queue().get();
    }

    @Bean
    public QueueChannel champagneChannel() {
        return MessageChannels.queue().get();
    }

    @Bean
    public QueueChannel partyChannel() {
        return MessageChannels.queue().get();
    }

    @Bean
    public IntegrationFlow christmasTreeFlow(LuckService luckService, ChristmasTreeStore christmasTreeStore) {
        return IntegrationFlows
                .from("partyPreparationChannel")
                .handle(luckService, GET_LUCK_METHOD)
                .handle(christmasTreeStore, SET_UP_CHRISTMAS_TREE_METHOD)
                .channel("mollChannel")
                .get();
    }

    @Bean
    public IntegrationFlow mollFlow(LuckService luckService, Moll moll) {
        return IntegrationFlows
                .from("mollChannel")
                .handle(luckService, GET_LUCK_METHOD)
                .handle(moll, BUY_PRESENTS_METHOD)
                .channel("kitchenChannel")
                .get();
    }

    @Bean
    public IntegrationFlow kitchenFlow(LuckService luckService, KitchenService kitchenService) {
        return IntegrationFlows
                .from("kitchenChannel")
                .handle(luckService, GET_LUCK_METHOD)
                .split()
                .handle(kitchenService, SET_TABLE_METHOD)
                .channel("champagneChannel")
                .get();
    }

    @Bean
    public IntegrationFlow champagneFlow(LuckService luckService, ChampagneService champagneService) {
        return IntegrationFlows
                .from("champagneChannel")
                .handle(luckService, GET_LUCK_METHOD)
                .handle(champagneService, TAKE_SOME_CHAMPAGNE_METHOD)
                .channel("partyResultChannel")
                .get();
    }

    @Bean
    public IntegrationFlow partyResultFlow() {
        return IntegrationFlows.from("partyResultChannel")
                .channel("partyChannel")
                .get();
    }
}
