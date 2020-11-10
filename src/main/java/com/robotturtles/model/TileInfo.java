package com.robotturtles.model;

public class TileInfo {
    private Position position;
    private Direction direction;
    private boolean isActive;

    public TileInfo(Position position, Direction direction, boolean active) {
        this.position = position;
        this.direction = direction;
        this.isActive = active;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isActive() {
        return this.isActive;
    }
}