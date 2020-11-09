package com.robotturtles.model;

import java.util.Stack;

public abstract class MovableTile extends BasicTile {
    private Direction direction;
    private Stack<Position> positionsVisited;
    private Stack<Direction> directionsFaced;

    public MovableTile(Position position, Direction direction) {
        super(position);
        this.direction = direction;
        this.positionsVisited = new Stack<>();
        this.directionsFaced = new Stack<>();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Adds a new position to the positionsVisited stack
     * @param position Position object to be added
     */
    public void addToPositionsVisited(Position position) {
        this.positionsVisited.add(position);
    }

    /**
     * Adds a new position to the directionsFaced stack
     * @param direction Direction object to be added
     */
    public void addToDirectionsFaced(Direction direction) {
        this.directionsFaced.add(direction);
    }

    /**
     * Returns the directionsFaced stack of the turtle
     */
    public Stack<Direction> getDirectionsFaced() {
        return directionsFaced;
    }

    /**
     * Returns the positionsVisited stack of the turtle
     */
    public Stack<Position> getPositionsVisited() {
        return positionsVisited;
    }

    public void setDirectionsFaced(Stack<Direction> directionsFaced) {
        this.directionsFaced = directionsFaced;
    }

    public void setPositionsVisited(Stack<Position> positionsVisited) {
        this.positionsVisited = positionsVisited;
    }
}
