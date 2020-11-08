package com.robotturtles.model;

public abstract class MovableTile extends BasicTile {
    private Direction direction;

    public MovableTile(Position position, Direction direction) {
        super(position);
        this.direction = direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }
}
