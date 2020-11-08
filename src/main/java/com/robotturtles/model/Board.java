package com.robotturtles.model;

public class Board {
    public static final int NUM_OF_ROWS = 8;
    public static final int NUM_OF_COLS = 8;

    private BasicTile[][] tiles;

    public Board() {
        tiles = new BasicTile[NUM_OF_ROWS][NUM_OF_COLS];
    }

    public void setUpTile(BasicTile tile) {
        addTileToPosition(tile, tile.getPosition());
    }

    private void addTileToPosition(BasicTile tile, Position position) {
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
    
    /**
     * Returns the Tile in the specified position
     * @param rowNumber row number
     * @param colNumber column number
     */
    public BasicTile getTile(int rowNumber, int colNumber) {
        return tiles[rowNumber][colNumber];
    }

    public void makeMove(Position oldPosition, Position destinationPosition, MovableTile turtle){
        if(oldPosition.equals(destinationPosition)){
            return;
        }
        setPositionNull(oldPosition);
        setNewPosition(destinationPosition, turtle);
    }

    private void setPositionNull(Position position){
        tiles[position.getRowNumber()][position.getColNumber()] = null;
    }

    private void setNewPosition(Position position, MovableTile turtle){
        tiles[position.getRowNumber()][position.getColNumber()] = turtle;
    }
}
