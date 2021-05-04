package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameOutcomeTest {
    @Test
    void playerBustsAndLoses() {
        StubDeck deck = new StubDeck(
            Rank.TEN, Rank.ACE, Rank.SIX, Rank.SIX, Rank.QUEEN);

        Game game = new Game(deck);
        game.initialDeal();
        game.playerHits();

        assertThat(game.determineOutcome()).isEqualTo("You Busted, so you " +
            "lose.  💸");
    }
}
