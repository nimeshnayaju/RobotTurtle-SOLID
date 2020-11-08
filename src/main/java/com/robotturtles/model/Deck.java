package com.robotturtles.model;

import java.util.ArrayList;

public class Deck {
    private static final int NUM_OF_FORWARD_CARDS = 18;
    private static final int NUM_OF_LEFT_TURN_CARDS = 8;

    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        initDeck();
    }

    private void initDeck() {
        initForwardMoveCards();
        initLeftTurnCards();
//        initRightTurnCards();
    }

    private void initLeftTurnCards() {
        for (int i = 0; i < NUM_OF_LEFT_TURN_CARDS; i++) {
            cards.add(new Card(new LeftTurn()));
        }
    }

    private void initForwardMoveCards() {
        for (int i = 0; i < NUM_OF_FORWARD_CARDS; i++) {
            cards.add(new Card(new ForwardMove()));
        }
    }
}
