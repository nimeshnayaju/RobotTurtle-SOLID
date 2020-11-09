package com.robotturtles.model;

import java.util.Stack;

public class Bug implements IMove {

    @Override
    public void execute(MovableTile tile) {
        Stack<Position> positionsVisited = tile.getPositionsVisited();
        Stack<Direction> directionsFaced = tile.getDirectionsFaced();

        if (!positionsVisited.isEmpty() && !directionsFaced.isEmpty()) {
            Position oldPosition = positionsVisited.pop();
            Direction oldDirection = directionsFaced.pop();
            tile.setPosition(oldPosition);
            tile.setDirection(oldDirection);
        } else {
            // otherwise, a bug card is being played before any card is played
            tile.setPosition(new Position(Integer.MIN_VALUE, Integer.MIN_VALUE));
        }
    }
}
