package com.jitterted.ebp.blackjack.domain.port;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.StubDeck;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class GameMonitorTest {
    @Test
    public void playerStandsCompletesGameSendsToMonitor() throws Exception {
        // creates the spy based on the interface
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);

        // subject under test
        Game game = new Game(gameMonitorSpy);
        game.initialDeal();

        game.playerStands();

        // verify that the roundCompleted method was called with any instance of a Game class
        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }

    @Test
    void playerHitsAndGoesBustGameSendsToMonitor() {
        // creates the spy based on the interface
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);

        StubDeck stubDeck = new StubDeck(
            Rank.TEN,   //Player
            Rank.EIGHT, //Dealer
            Rank.EIGHT, //Player
            Rank.ACE,   //Dealer
            Rank.QUEEN  //Player Busts
        );
        // subject under test
        Game game = new Game(stubDeck, gameMonitorSpy);
        game.initialDeal();
        game.playerHits();

        // verify that the roundCompleted method was called with any instance of a Game class
        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }

    @Test
    void playerHitsAndDoesNotGoBustGameNeverSendsToMonitor() {
        // creates the spy based on the interface
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);

        StubDeck stubDeck = new StubDeck(
            Rank.TEN,   //Player
            Rank.EIGHT, //Dealer
            Rank.EIGHT, //Player
            Rank.ACE,   //Dealer
            Rank.ACE    //Player Is Good!
        );
        // subject under test
        Game game = new Game(stubDeck, gameMonitorSpy);
        game.initialDeal();
        game.playerHits();

        // verify that the roundCompleted method was not called with any instance of a Game class
        verify(gameMonitorSpy, never()).roundCompleted(any(Game.class));
    }
}
