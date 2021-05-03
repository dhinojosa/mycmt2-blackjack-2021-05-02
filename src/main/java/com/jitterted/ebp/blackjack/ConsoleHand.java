package com.jitterted.ebp.blackjack;

import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleHand {
    public static String displayFirstCard(Hand hand) {
        return ConsoleCard.display(hand.cards().get(0));
    }

    static void display(Hand hand) {
        System.out.println(cardsAsString(hand));
    }

    protected static String cardsAsString(Hand hand) {
        return hand.cards().stream()
                   .map(ConsoleCard::display)
                   .collect(Collectors.joining(
                          ansi().cursorUp(6).cursorRight(1).toString()));
    }
}