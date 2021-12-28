package ru.otus.flow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;

@Configuration
public class PreparationFlowConfiguration {

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
    public IntegrationFlow christmasTreeFlow() {
        return IntegrationFlows
                .from("partyPreparationChannel")
                .handle("luckService", "getLuck")
                .split()
                .handle("christmasTreeStore", "setUpChristmasTree")
                .channel("mollChannel")
                .get();
    }

    @Bean
    public IntegrationFlow mollFlow() {
        return IntegrationFlows
                .from("mollChannel")
                .handle("luckService", "getLuck")
                .split()
                .handle("moll", "buyPresents")
                .channel("kitchenChannel")
                .get();
    }

    @Bean
    public IntegrationFlow kitchenFlow() {
        return IntegrationFlows
                .from("kitchenChannel")
                .handle("luckService", "getLuck")
                .split()
                .handle("kitchenService", "setTable")
                .channel("champagneChannel")
                .get();
    }

    @Bean
    public IntegrationFlow champagneFlow() {
        return IntegrationFlows
                .from("champagneChannel")
                .handle("luckService", "getLuck")
                .split()
                .handle("champagneService", "takeSomeChampagne")
                .channel("partyAggregateChannel")
                .get();
    }

    @Bean
    public IntegrationFlow partyAggregateFlow() {
        return IntegrationFlows.from("partyAggregateChannel")
                .aggregate()
                .channel("partyChannel")
                .get();
    }
}
