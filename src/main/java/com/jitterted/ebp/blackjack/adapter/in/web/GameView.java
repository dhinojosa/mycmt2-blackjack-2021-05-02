package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Game;

import java.util.List;

import static com.jitterted.ebp.blackjack.domain.Hand.convertHandToString;

//DTO
public class GameView {
    private List<String> dealerCards;
    private List<String> playerCards;

    public GameView(List<String> playerHandStrings,
                    List<String> dealerHandStrings) {
        this.dealerCards = dealerHandStrings;
        this.playerCards = playerHandStrings;
    }

    public static GameView of(Game game) {
        return new GameView(
            convertHandToString(game.playerHand()),
            convertHandToString(game.dealerHand()));
    }

    public List<String> getDealerCards() {
        return dealerCards;
    }

    public void setDealerCards(List<String> dealerCards) {
        this.dealerCards = dealerCards;
    }

    public List<String> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(List<String> playerCards) {
        this.playerCards = playerCards;
    }
}
