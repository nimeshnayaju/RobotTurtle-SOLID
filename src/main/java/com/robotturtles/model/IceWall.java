package com.robotturtles.model;

public class IceWall implements Movable {
    private Position position;

    public IceWall(Position position) {
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
