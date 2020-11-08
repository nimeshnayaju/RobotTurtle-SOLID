package com.robotturtles.model;

public class LeftTurn implements IMove {
    @Override
    public void execute(MovableTile tile) {
        Direction currDirection = tile.getDirection();

        Direction newDirection;
        switch (currDirection) {
            case NORTH:
                newDirection = Direction.WEST;
                break;
            case SOUTH:
                newDirection = Direction.EAST;
                break;
            case WEST:
                newDirection = Direction.SOUTH;
                break;
            case EAST:
                newDirection = Direction.NORTH;
            default:
                newDirection = null;
        }

        tile.setDirection(newDirection);
    }
}
