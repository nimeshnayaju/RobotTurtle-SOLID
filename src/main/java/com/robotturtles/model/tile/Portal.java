package com.robotturtles.model.tile;

public class Portal implements Placeable, Pairable {
    private Position position;
    private Portal portalTwo;

    public Portal(Position position) {
        this.position = position;
    };

    public Portal(Position position, Portal portalTwo) {
        this.position = position;
        setOtherPair(portalTwo);

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
    public Portal getOtherPair() {
        return this.portalTwo;
    }

    @Override
    public void setOtherPair(Portal otherPair) {
        this.portalTwo = otherPair;
    }
}
