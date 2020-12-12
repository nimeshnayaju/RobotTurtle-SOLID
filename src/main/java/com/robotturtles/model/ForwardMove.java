package com.robotturtles.model;

public class ForwardMove implements IMove {
    @Override
    public void execute(Turtle tile) {
        // Get the current position and direction of the tile
        Position currPosition = tile.getPosition();
        Direction currDirection = tile.getDirection();

        tile.addToDirectionsFaced(currDirection);
        tile.addToPositionsVisited(currPosition);

        Position newPosition = determinePosition(currDirection, currPosition);

        tile.setPosition(newPosition);
    }

    public void execute(Crate tile) {
        // Get the current position and direction of the tile
        Position currPosition = tile.getPosition();
        Direction currDirection = tile.getDirection();

        Position newPosition = determinePosition(currDirection, currPosition);
        tile.setPosition(newPosition);
    }

    public static Position determinePosition(Direction direction, Position position) {
        int rowNumber = position.getRowNumber();
        int colNumber = position.getColNumber();

        Position newPosition;

        switch (direction) {
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
        return newPosition;
    }
}
