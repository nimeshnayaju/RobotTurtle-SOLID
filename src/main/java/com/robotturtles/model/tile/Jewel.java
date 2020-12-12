package com.robotturtles.model.tile;

import java.util.Stack;

public class Jewel implements Placeable {

    private Position position;
    private Stack<Position> positionsVisited;
    public Jewel(Position position) {
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
