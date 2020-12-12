package com.robotturtles.model.card;

import com.robotturtles.model.card.IShoot;
import com.robotturtles.model.tile.IceWall;

public class Laser implements IShoot {

    @Override
    public void execute(IceWall iceWall) {
        iceWall.setPosition(null);
    }
}
