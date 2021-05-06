package com.jitterted.ebp.blackjack.domain;


public class GameService {
    private Game game;
    private Deck deck;

    public void createNewGame() {
        this.game = new Game(deck);
    }

    public GameService() {
        this(new Deck());
    }

    public GameService(Deck deck) {
        this.deck = deck;
    }

    public Game currentGame() {
        if (game == null) {
            this.game = new Game();
            return game;
        }
        return game;
    }
}
