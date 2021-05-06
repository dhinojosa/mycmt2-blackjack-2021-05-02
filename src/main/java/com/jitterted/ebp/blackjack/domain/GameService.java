package com.jitterted.ebp.blackjack.domain;


import com.jitterted.ebp.blackjack.adapter.out.gamemonitor.HttpGameMonitor;

public class GameService {
    private Game game;
    private Deck deck;

    public void createNewGame() {
        this.game = new Game(deck, new HttpGameMonitor()); //was deck
    }

    public GameService() {
        this(new Deck());
    }

    public GameService(Deck deck) {
        this.deck = deck;
    }

    public Game currentGame() {
        if (game == null) {
            this.game = new Game(); //probably used for testing
            return game;
        }
        return game;
    }
}
