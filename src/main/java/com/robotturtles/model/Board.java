package com.robotturtles.model;

public class Board {
    public static final int NUM_OF_ROWS = 8;
    public static final int NUM_OF_COLS = 8;

    private Movable[][] tiles;

    public Board() {
        tiles = new Movable[NUM_OF_ROWS][NUM_OF_COLS];
    }

    public void setUpTile(Movable tile) {
        addTileToPosition(tile, tile.getPosition());
    }

    private void addTileToPosition(Movable tile, Position position) {
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

    private Movable getTile(Position position) {
        return getTile(position.getRowNumber(), position.getColNumber());
    }

    /**
     * Returns the Tile in the specified position
     * @param rowNumber row number
     * @param colNumber column number
     */
    public Movable getTile(int rowNumber, int colNumber) {
        return tiles[rowNumber][colNumber];
    }

    public void makeMove(Position oldPosition, Position destinationPosition, Crate crate, Turtle turtle){
        if(oldPosition.equals(destinationPosition)){
            return;
        }
        setPositionNull(oldPosition);
        setNewPosition(destinationPosition, turtle);
        if (crate != null) {
            crate.setPosition(ForwardMove.determinePosition(turtle.getDirection(), crate.getPosition()));
        }
    }

    private void setPositionNull(Position position){
        tiles[position.getRowNumber()][position.getColNumber()] = null;
    }

    private void setNewPosition(Position position, Turtle turtle){
        tiles[position.getRowNumber()][position.getColNumber()] = turtle;
        turtle.setPosition(new Position(position.getRowNumber(), position.getColNumber()));
    }

}
