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

        assertThat(game.determineOutcome())
            .isEqualTo(GameOutcome.PLAYER_BUSTED);
    }

    //player beats dealer and player goes bust
    @Test
    void playerBeatsDealer() {
        StubDeck deck = new StubDeck(
            Rank.TEN, //Player
            Rank.TEN, //Dealer
            Rank.TEN, //Player
            Rank.SIX);//Dealer

        Game game = new Game(deck);
        game.initialDeal();

        assertThat(game.determineOutcome())
            .isEqualTo(GameOutcome.PLAYER_WINS);
    }

    @Test
    void playerBeatsDealerWithAHit() {
        StubDeck deck = new StubDeck(
            Rank.EIGHT, //Player
            Rank.TEN, //Dealer
            Rank.TWO, //Player
            Rank.SIX, //Dealer
            Rank.ACE);//Player

        Game game = new Game(deck);
        game.initialDeal();
        game.playerHits();

        assertThat(game.determineOutcome())
            .isEqualTo(GameOutcome.PLAYER_WINS);
    }

}
