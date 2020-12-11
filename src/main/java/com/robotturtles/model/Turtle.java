package com.robotturtles.model;

import java.util.Stack;

public class Turtle implements Movable, Turnable, Undoable {
    private Position position;
    private Direction direction;
    private Stack<Position> positionsVisited;
    private Stack<Direction> directionsFaced;

    public Turtle(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
        this.positionsVisited = new Stack<>();
        this.directionsFaced = new Stack<>();
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void addToPositionsVisited(Position position) {
        this.positionsVisited.add(position);
    }

    @Override
    public Stack<Position> getPositionsVisited() {
        return this.positionsVisited;
    }

    @Override
    public void setPositionsVisited(Stack<Position> positionsVisited) {
        this.positionsVisited = positionsVisited;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public void addToDirectionsFaced(Direction direction) {
        this.directionsFaced.add(direction);
    }

    @Override
    public Stack<Direction> getDirectionsFaced() {
        return this.directionsFaced;
    }

    @Override
    public void setDirectionsFaced(Stack<Direction> directionsFaced) {
        this.directionsFaced = directionsFaced;
    }
}
