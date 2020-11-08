package com.robotturtles.model;

public class Board {
    public static final int NUM_OF_ROWS = 8;
    public static final int NUM_OF_COLS = 8;

    private BasicTile[][] tiles;

    public Board() {
        tiles = new BasicTile[NUM_OF_ROWS][NUM_OF_COLS];
    }
}
