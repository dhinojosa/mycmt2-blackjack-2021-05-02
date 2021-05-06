package com.jitterted.ebp.blackjack.domain;

public enum GameOutcome {
    DEALER_BUSTED("Dealer went BUST, Player wins! Yay for you!! 💵"),
    PLAYER_WINS("You beat the Dealer! 💵"),
    PLAYER_PUSHES("Push: Nobody wins, we'll call it even."),
    PLAYER_LOSES("You lost to the Dealer. 💸"),
    PLAYER_BUSTED("You Busted, so you lose.  💸"),
    BLACKJACK("Blackjack! 💸");

    private final String message;

    GameOutcome(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
