package com.robotturtles.model;

public class Laser implements IShoot {

    @Override
    public void execute(IceWall iceWall) {
        iceWall.setPosition(null);
    }
}
