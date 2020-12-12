package com.robotturtles.model;

public class Card {
    private IMove associatedMove; // Move associated with the Card
    private IShoot laser;

    public Card(IMove move) {
        this.associatedMove = move;
    }

    public Card(IShoot laser) { this.laser = laser; }

    public void play(Turtle turtle) {
        associatedMove.execute(turtle);
    }

    public void play(IceWall iceWall) {
        laser.execute(iceWall);
    }

    public boolean isLaserCard() {
        return laser != null && associatedMove == null;
    }
}
