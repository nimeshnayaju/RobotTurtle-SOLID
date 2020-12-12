package com.robotturtles.model.game;

import com.robotturtles.model.card.Forward;
import com.robotturtles.model.tile.Crate;
import com.robotturtles.model.tile.Placeable;
import com.robotturtles.model.tile.Position;
import com.robotturtles.model.tile.Turtle;

public class Board {
    public static final int NUM_OF_ROWS = 8;
    public static final int NUM_OF_COLS = 8;

    private Placeable[][] tiles;

    public Board() {
        tiles = new Placeable[NUM_OF_ROWS][NUM_OF_COLS];
    }

    public void setUpTile(Placeable tile) {
        addTileToPosition(tile, tile.getPosition());
    }

    private void addTileToPosition(Placeable tile, Position position) {
        int rowNumber = position.getRowNumber();
        int colNumber = position.getColNumber();
        this.tiles[rowNumber][colNumber] = tile;
    }

    /**
     * Method to check if the specified position in the Board is occupied or not
     * @param row row number
     * @param col column number
     * @return true if the position is occupied; false otherwise
     */
    public boolean isOccupied(int row, int col) {
        return tiles[row][col] != null;
    }

    public boolean isOccupied(Position position) {
        return isOccupied(position.getRowNumber(), position.getColNumber());
    }

    private Placeable getTile(Position position) {
        return getTile(position.getRowNumber(), position.getColNumber());
    }

    /**
     * Returns the Tile in the specified position
     * @param rowNumber row number
     * @param colNumber column number
     */
    public Placeable getTile(int rowNumber, int colNumber) {
        return tiles[rowNumber][colNumber];
    }

    public void makeMove(Position oldPosition, Position destinationPosition, Crate crate, Turtle turtle){
        if(oldPosition.equals(destinationPosition)){
            return;
        }
        setPositionNull(oldPosition);
        setNewPosition(destinationPosition, turtle);
        if (crate != null) {
            crate.setPosition(Forward.determinePosition(turtle.getDirection(), crate.getPosition()));
        }
    }

    public void setPositionNull(Position position){
        tiles[position.getRowNumber()][position.getColNumber()] = null;
    }

    private void setNewPosition(Position position, Turtle turtle){
        tiles[position.getRowNumber()][position.getColNumber()] = turtle;
        turtle.setPosition(new Position(position.getRowNumber(), position.getColNumber()));
    }
}
