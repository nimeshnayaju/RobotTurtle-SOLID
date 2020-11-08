package com.robotturtles.model;

public class ForwardMove implements IMove {
    @Override
    public void execute(MovableTile tile) {
        // Get the current position and direction of the tile
        Position currPosition = tile.getPosition();
        Direction currDirection = tile.getDirection();

        int rowNumber = currPosition.getRowNumber();
        int colNumber = currPosition.getColNumber();

        Position newPosition;

        switch (currDirection) {
            case NORTH:
                newPosition = new Position(rowNumber-1, colNumber);
                break;
            case SOUTH:
                newPosition = new Position(rowNumber+1, colNumber);
                break;
            case EAST:
                newPosition = new Position(rowNumber, colNumber+1);
                break;
            case WEST:
                newPosition = new Position(rowNumber, colNumber-1);
                break;
            default:
                newPosition = null;
        }

        tile.setPosition(newPosition);
    }
}
