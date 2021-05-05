package com.jitterted.ebp.blackjack.domain;

public class GameService {
    private Game game;
    private Deck deck;

    public Game createNewGame() {
        this.game = new Game(deck);
        return game;
    }

    public GameService() {
        this(new Deck());
    }

    public GameService(Deck deck) {
        this.deck = deck;
    }

    public Game currentGame() {
        if (game == null) {
            throw new IllegalStateException("Game not created");
        }
        return game;
    }
}
