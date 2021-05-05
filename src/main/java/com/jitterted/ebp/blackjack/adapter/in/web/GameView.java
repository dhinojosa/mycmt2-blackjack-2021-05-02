package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Game;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//DTO
public class GameView {
    private List<String> dealerCards;
    private List<String> playerCards;

    public GameView(List<String> playerHandStrings, List<String> dealerHandStrings) {
        this.dealerCards = dealerHandStrings;
        this.playerCards = playerHandStrings;
    }

    //static method
    public static GameView of(Game game) {
        List<String> playerHandStrings =
            game.playerHand().cards().stream().map(c -> c.rank().display() + c.suit().symbol()).collect(Collectors.toList());
        List<String> dealerHandStrings =
            game.dealerHand().cards().stream().map(c -> c.rank().display() + c.suit().symbol()).collect(Collectors.toList());

        return new GameView(playerHandStrings, dealerHandStrings);
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
