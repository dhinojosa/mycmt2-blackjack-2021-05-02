package com.jitterted.ebp.blackjack.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StubDeck extends Deck {

    public static final Suit DUMMY_SUIT = Suit.SPADES;
    private final Iterator<Card> iterator;

    public StubDeck(Rank... ranks) {
        List<Card> cards = new ArrayList<>();
        for (Rank rank : ranks){
            cards.add(new Card(DUMMY_SUIT, rank));
        }
        this.iterator = cards.iterator();
    }

    public StubDeck(Card... cards) {
        this.iterator = Arrays.asList(cards).iterator();
    }

    @Override
    public Card draw() {
        return iterator.next();
    }
}
