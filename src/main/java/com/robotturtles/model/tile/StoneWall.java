package com.robotturtles.model.tile;

public class StoneWall implements Placeable {
    private Position position;

    public StoneWall(Position position){
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
