package com.robotturtles.model;

public class Portal implements Movable {
    private Position position;

    public Portal(Position position) {
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
}
