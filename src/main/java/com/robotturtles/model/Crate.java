package com.robotturtles.model;

public class Crate implements Movable, Turnable {

    private Position position;
    private Direction direction;

    public Crate(Position position) {
        this.position = position;
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
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }
}
