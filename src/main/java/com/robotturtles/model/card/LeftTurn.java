package com.robotturtles.model.card;

import com.robotturtles.model.tile.Direction;
import com.robotturtles.model.tile.Turtle;

public class LeftTurn implements IMove {
    @Override
    public void execute(Turtle tile) {
        Direction currDirection = tile.getDirection();

        tile.addToDirectionsFaced(currDirection);
        tile.addToPositionsVisited(tile.getPosition());

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
                break;
            default:
                newDirection = null;
        }

        tile.setDirection(newDirection);
    }
}
