package com.robotturtles.model;

import static com.robotturtles.model.Board.*;

public class Player {

    private static final int BOARD_INDEX_ZERO = 0;
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
        switch (playerId) {
            case 0:
                this.turtle = new Turtle(new Position(BOARD_INDEX_ZERO, BOARD_INDEX_ZERO), Direction.SOUTH);
                break;
            case 1:
                this.turtle = new Turtle(new Position(BOARD_INDEX_ZERO, NUM_OF_COLS-1), Direction.SOUTH);
                break;
            case 2:
                this.turtle = new Turtle(new Position(NUM_OF_ROWS-1, NUM_OF_COLS-1), Direction.NORTH);
                break;
            case 3:
                this.turtle = new Turtle(new Position(NUM_OF_COLS-1, BOARD_INDEX_ZERO), Direction.NORTH);
        }
    }

    public MovableTile getTurtle() {
        return this.turtle;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Deck getDeck() {
        return this.deck;
    }
}
