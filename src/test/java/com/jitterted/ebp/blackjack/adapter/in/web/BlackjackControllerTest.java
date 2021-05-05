package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;

public class BlackjackControllerTest {

    @Test
    void testStartGameInitialCardsAreDealt() {
        Game game = new Game();
        BlackjackController blackjackController = new BlackjackController(game);

        blackjackController.startGame();

        assertThat(game.playerHand().cards()).hasSize(2);
    }

    @Test
    void testGameViewPopulatedViewModel() {
        StubDeck stubDeck = new StubDeck(
            new Card(Suit.CLUBS, Rank.TEN), //play
            new Card(Suit.HEARTS, Rank.TWO), //deal
            new Card(Suit.SPADES, Rank.KING), //play
            new Card(Suit.CLUBS, Rank.THREE));//deal

        Game game = new Game(stubDeck);
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        Model model = new ConcurrentModel();
        blackjackController.viewGame(model);

        GameView gameView = (GameView) model.getAttribute("gameView");

        assertThat(gameView.getDealerCards()).containsExactly("2♥", "3♣");
    }
}
