package ru.otus.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Party {

    private ChristmasTree christmasTree;

    private Present present;

    private ChristmasGoose christmasGoose;

    private boolean champagne;

    private int luck;

    @Override
    public String toString() {

        final var sb = new StringBuilder("Your party has ");

        if (christmasTree != null) {
            if (christmasTree.isDecorated()) {
                sb.append("well decorated ");
            }
            sb.append(christmasTree.getDescription()).append(", ");
        } else {
            sb.append("no any xmas tree, ");
        }

        if (present != null) {
            sb.append("you have bought ").append(present.getDescription());
            if (present.isPacked()) {
                sb.append(" and you even have enough time to pack all of them!");
            }
        } else {
            sb.append("you stuck in traffic jam and all stores were empty :(");
        }

        sb.append('\n');

        if (christmasGoose != null) {
            sb.append("Your table is served with ").append(christmasGoose.getDescription());
        } else {
            sb.append("Oh, you didn't even try to cook something! New years holidays are not best for diet!");
        }

        sb.append('\n');

        if (champagne) {
            sb.append("Nice, that champagne is already in the fridge!");
        } else {
            sb.append("How could you forget, that alcohol is available only till 22:00? Call your friends, let them grab some beverages!");
        }

        sb.append('\n');
        sb.append("Happy new year!");

        return sb.toString();
    }
}
