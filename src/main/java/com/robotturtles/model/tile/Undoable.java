package com.robotturtles.model.tile;

import java.util.Stack;

public interface Undoable {
    public void addToPositionsVisited(Position position);

    public Stack<Position> getPositionsVisited();

    public void setPositionsVisited(Stack<Position> positionsVisited);

    public void addToDirectionsFaced(Direction direction);

    public Stack<Direction> getDirectionsFaced();

    public void setDirectionsFaced(Stack<Direction> directionsFaced);
}
