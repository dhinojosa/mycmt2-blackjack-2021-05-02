package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;

public class BlackjackControllerTest {


    @Test
    void testStartGameInitialCardsAreDealt() {
        GameService gameService = new GameService();
        BlackjackController blackjackController = new BlackjackController(gameService);

        blackjackController.startGame();

        assertThat(gameService.currentGame().playerHand().cards()).hasSize(2);
    }

    @Test
    void testGameViewPopulatedViewModel() {
        StubDeck stubDeck = new StubDeck(
            new Card(Suit.CLUBS, Rank.TEN), //play
            new Card(Suit.HEARTS, Rank.TWO), //deal
            new Card(Suit.SPADES, Rank.KING), //play
            new Card(Suit.CLUBS, Rank.THREE));//deal

        GameService gameService = new GameService(stubDeck);
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();

        Model model = new ConcurrentModel();
        blackjackController.viewGame(model);

        GameView gameView = (GameView) model.getAttribute("gameView");

        assertThat(gameView.getDealerCards()).containsExactly("2♥", "3♣");
    }

    @Test
    void testThatWhenHitTheTotalNumberOfCardsIsThree() {
        GameService gameService = new GameService();
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();
        blackjackController.hit();
        assertThat(gameService.currentGame().playerHand().cards()).hasSize(3);
    }

    @Test
    void testThatWeGoToTheDonePageWhenThePlayerGoesBust() {
        StubDeck stubDeck = new StubDeck(
            new Card(Suit.CLUBS, Rank.TEN), //play
            new Card(Suit.HEARTS, Rank.TEN), //deal
            new Card(Suit.SPADES, Rank.KING), //play
            new Card(Suit.CLUBS, Rank.JACK),
            new Card(Suit.DIAMONDS, Rank.EIGHT));//deal


        BlackjackController blackjackController = new BlackjackController(new GameService(stubDeck));
        blackjackController.startGame();
        String page = blackjackController.hit();
        assertThat(page).isEqualTo("redirect:/done");
    }

    @Test
    void testDonePageShowsFinalGameViewWithOutcome() {
        BlackjackController blackjackController = new BlackjackController(new GameService());
        blackjackController.startGame();
        Model model = new ConcurrentModel();
        blackjackController.done(model);

        assertThat(model.getAttribute("gameView"))
            .isNotNull()
            .isInstanceOf(GameView.class);

        String outcome = (String) model.getAttribute("outcome");
        assertThat(outcome).isNotBlank();

    }
}
