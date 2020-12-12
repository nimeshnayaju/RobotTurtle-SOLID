package com.robotturtles.model;

import java.util.Random;

public class StoneWall implements Movable {
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
