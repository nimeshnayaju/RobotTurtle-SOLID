package com.robotturtles.controller;

import com.robotturtles.model.tile.Direction;

public class DisplayFormat {
    private int[] position;
    private Direction direction;
    private boolean active;

    public DisplayFormat(int[] position, Direction direction, boolean active) {
        this.position = position;
        this.direction = direction;
        this.active = active;
    }

    public Direction getDirection() {
        return direction;
    }

    public int[] getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }
}
