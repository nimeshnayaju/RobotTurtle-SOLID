package com.robotturtles.model;

public class Board {
    public static final int NUM_OF_ROWS = 8;
    public static final int NUM_OF_COLS = 8;

    private Movable[][] tiles;
    private Portal[] portals;

    public Board() {
        tiles = new Movable[NUM_OF_ROWS][NUM_OF_COLS];
        portals = new Portal[2];
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

    public void makeMove(Position oldPosition, Position destinationPosition, Turtle turtle){
        if(oldPosition.equals(destinationPosition)){
            return;
        }
        setPositionNull(oldPosition);
        // If the turtle lands in one of the pairs of a Portal tile, teleport it to the position of the other tile
        if (isPortalTile(destinationPosition)) {
            destinationPosition = getPortalTile(destinationPosition).getOtherPair().getPosition();
        }
        setNewPosition(destinationPosition, turtle);
    }

    private Portal getPortalTile(Position position) {
        for (Portal portal : portals) {
            if (position.equals(portal.getPosition())) return portal;
        }
        return null;
    }

    private boolean isPortalTile(Position position) {
        for (Portal portal : portals) {
            if (position.equals(portal.getPosition())) return true;
        }
        return false;
    }

    private void setPositionNull(Position position){
        tiles[position.getRowNumber()][position.getColNumber()] = null;
    }

    private void setNewPosition(Position position, Turtle turtle){
        tiles[position.getRowNumber()][position.getColNumber()] = turtle;
        turtle.setPosition(new Position(position.getRowNumber(), position.getColNumber()));
    }

    public void setUpPortal(Portal portal) {
        portals[0] = portal;
        portals[1] = portal.getOtherPair();
    }
}
