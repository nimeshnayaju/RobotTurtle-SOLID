package com.robotturtles.model;

import static com.robotturtles.model.Board.*;

public class Player {

    private static final int BOARD_INDEX_ZERO = 0;

    private String playerName;
    private int playerId;
    private Turtle turtle;
    private Movable jewel;


    public Player(String playerName, int playerId) {
        this.playerName = playerName;
        this.playerId = playerId;
        initTurtle();
        initJewel();
    }

    private void initJewel() {
        switch (playerId) {
            case 0: case 1:
                this.jewel = new Jewel(new Position((NUM_OF_COLS-1)/2,(NUM_OF_COLS-1)/2+playerId));
                break;
            case 2: case 3:
                this.jewel = new Jewel(new Position(NUM_OF_COLS/2,(NUM_OF_COLS-1)/2+(playerId-2)));
                break;
        }
    }

    private void initTurtle() {
        switch (playerId) {
            case 0:
                this.turtle = new Turtle(new Position(BOARD_INDEX_ZERO, BOARD_INDEX_ZERO), Direction.SOUTH);
                break;
            case 1:
                this.turtle = new Turtle(new Position(BOARD_INDEX_ZERO,NUM_OF_COLS-1), Direction.SOUTH);
                break;
            case 2:
                this.turtle = new Turtle(new Position(NUM_OF_ROWS-1, BOARD_INDEX_ZERO), Direction.NORTH);
                break;
            case 3:
                this.turtle = new Turtle(new Position(NUM_OF_COLS-1, NUM_OF_COLS-1), Direction.NORTH);
        }
    }

    public Turtle getTurtle() {
        return this.turtle;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Movable getJewel() {
        return this.jewel;
    }

    public int getPlayerId() {
        return playerId;
    }
}
