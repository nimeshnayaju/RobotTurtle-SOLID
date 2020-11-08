package com.robotturtles.model;

public class Player {

    private String playerName;
    private int playerId;
    private Deck deck;
    private MovableTile turtle;

    public Player(String playerName, int playerId) {
        this.playerName = playerName;
        this.playerId = playerId;
        this.deck = new Deck();
        initTurtle();
    }

    private void initTurtle() {

    }
}
