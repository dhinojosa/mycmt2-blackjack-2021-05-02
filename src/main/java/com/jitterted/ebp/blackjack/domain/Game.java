package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.domain.port.GameMonitor;

public class Game {

    private final Deck deck;

    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();
    private final GameMonitor gameMonitor;
    private boolean playerDone;

    public Game() {
        this(new Deck());
    }

    public Game(Deck deck) {
        this(deck, new DoNothingGameMonitor());
    }

    public Game(Deck deck, GameMonitor gameMonitor) {
        this.deck = deck;
        this.gameMonitor = gameMonitor;
    }

    public Game(GameMonitor gameMonitor) {
        this(new Deck(), gameMonitor);
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
    }

    private void dealRoundOfCards() {
        // why: players first because this is the rule
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    //GOAL: Use this and rename to determineOutcome
    public GameOutcome determineOutcome() {
        if (playerHand.hasBlackjack()) {
            return GameOutcome.BLACKJACK;
        } else if (playerHand.isBusted()) {
            return GameOutcome.PLAYER_BUSTED;
        } else if (dealerHand.isBusted()) {
            return GameOutcome.DEALER_BUSTED;
        } else if (playerHand.beats(dealerHand)) {
            return GameOutcome.PLAYER_WINS;
        } else if (playerHand.pushes(dealerHand)) {
            return GameOutcome.PLAYER_PUSHES;
        } else {
            return GameOutcome.PLAYER_LOSES;
        }
    }

    public void dealerTurn() {
        // Dealer makes its choice automatically based on a simple heuristic
        // (<=16 must hit, =>17 must stand)
        if (!playerHand.isBusted()) {
            while (dealerHand.dealerMustDrawCard()) {
                dealerHand.drawFrom(deck);
            }
        }
    }

    public Hand playerHand() {
        return playerHand;
    }

    public Hand dealerHand() {
        return dealerHand;
    }

    public void playerHits() {
        playerHand.drawFrom(deck);
        playerDone = playerHand.isBusted();
        if (playerDone) gameMonitor.roundCompleted(this);
    }

    public void playerStands() {
        dealerTurn();
        playerDone = true;
        gameMonitor.roundCompleted(this);
    }

    public boolean isPlayerDone() {
        return playerDone;
    }
}
