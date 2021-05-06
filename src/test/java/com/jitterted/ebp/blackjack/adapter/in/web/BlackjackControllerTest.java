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

    @Test
    public void standResultsInGamePlayerIsDone() throws Exception {
        GameService gameService = new GameService();
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();

        String redirectPage = blackjackController.stand();

        assertThat(redirectPage)
            .isEqualTo("redirect:/done");

        assertThat(gameService.currentGame().isPlayerDone())
            .isTrue();
    }

    @Test
    public void standResultsInPlayerLosingToDealerAfterDealerTakesTurn() throws Exception {
        Deck dealerBeatsPlayerAfterDrawingAdditionalCardDeck =
            new StubDeck(
                Rank.TEN, //player
                Rank.QUEEN, //dealer
                Rank.EIGHT, //player
                Rank.FIVE,  //dealer
                Rank.SIX);  //dealer
        GameService gameService = new GameService(dealerBeatsPlayerAfterDrawingAdditionalCardDeck);
        BlackjackController blackjackController = new BlackjackController(gameService);
        blackjackController.startGame();

        blackjackController.stand();

        assertThat(gameService.currentGame().determineOutcome())
            .isEqualByComparingTo(GameOutcome.PLAYER_LOSES);
    }

    @Test
    public void dealerDrawsAdditionalCardAfterPlayerStands() throws Exception {
        Deck dealerDrawsAdditionalCardDeck = new StubDeck(Rank.TEN, Rank.QUEEN,
            Rank.EIGHT, Rank.FIVE,
            Rank.SIX);
        Game game = new Game(dealerDrawsAdditionalCardDeck);
        game.initialDeal();

        game.playerStands();

        assertThat(game.dealerHand().cards())
            .hasSize(3);
    }
}
