package com.robotturtles.model;

public class Card {
    private IMove associatedMove; // Move associated with the Card

    public Card(IMove move) {
        this.associatedMove = move;
    }

    public void play(Turtle turtle) {
        associatedMove.execute(turtle);
    }
}
