package com.robotturtles.model.tile;

public class IceWall implements Placeable {
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
