package com.jitterted.ebp.blackjack.adapter.out.gamemonitor;

import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.StubDeck;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameResultDTOTest {

    @Test
    void testThatDTOWillProvideHardCodedName() {
        Game game = new Game();
        GameResultDTO gameResultDTO = GameResultDTO.of(game);
        String name = gameResultDTO.getPlayerName();
        assertThat(name).isEqualTo("Dan");
    }

    @Test
    void testThatDTOWillSayBusted() {
        StubDeck stubDeck = new StubDeck(
            Rank.TEN,   //Player
            Rank.EIGHT, //Dealer
            Rank.EIGHT, //Player
            Rank.ACE,   //Dealer
            Rank.QUEEN  //Player Busts
        );
        Game game = new Game(stubDeck);
        game.initialDeal();
        game.playerHits();
        GameResultDTO gameResultDTO = GameResultDTO.of(game);
        String outcome = gameResultDTO.getOutcome();
        assertThat(outcome).isEqualTo("PLAYER_BUSTED");
    }

    @Test
    void testThatDTOWillHavePlayerValueOf28() {
        StubDeck stubDeck = new StubDeck(
            Rank.TEN,   //Player
            Rank.EIGHT, //Dealer
            Rank.EIGHT, //Player
            Rank.ACE,   //Dealer
            Rank.QUEEN  //Player Busts
        );
        Game game = new Game(stubDeck);
        game.initialDeal();
        game.playerHits();
        GameResultDTO gameResultDTO = GameResultDTO.of(game);
        String playerHandValue = gameResultDTO.getPlayerHandValue();
        assertThat(playerHandValue).isEqualTo("28");
    }

    @Test
    void testThatDTOWillHaveDealerValueOf9() {
        StubDeck stubDeck = new StubDeck(
            Rank.TEN,   //Player
            Rank.EIGHT, //Dealer
            Rank.EIGHT, //Player
            Rank.ACE,   //Dealer
            Rank.QUEEN  //Player Busts
        );
        Game game = new Game(stubDeck);
        game.initialDeal();
        game.playerHits();
        GameResultDTO gameResultDTO = GameResultDTO.of(game);
        String dealerHandValue = gameResultDTO.getDealerHandValue();
        assertThat(dealerHandValue).isEqualTo("19");
    }
}
