package com.robotturtles.model;

public class RightTurn implements IMove{
	@Override
    public void execute(MovableTile tile) {
        Direction currDirection = tile.getDirection();

        Direction newDirection;
        switch (currDirection) {
            case NORTH:
                newDirection = Direction.EAST;
                break;
            case SOUTH:
                newDirection = Direction.WEST;
                break;
            case WEST:
                newDirection = Direction.NORTH;
                break;
            case EAST:
                newDirection = Direction.SOUTH;
                break;
            default:
                newDirection = null;
        }

        tile.setDirection(newDirection);
    }
}
