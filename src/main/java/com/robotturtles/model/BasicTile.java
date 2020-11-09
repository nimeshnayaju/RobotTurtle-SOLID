package com.robotturtles.model;

public class BasicTile {
    private Position position;

    public BasicTile(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
