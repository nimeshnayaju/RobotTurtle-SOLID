package com.robotturtles.model;

import java.util.Random;

import static com.robotturtles.model.StoneWall.generate_exclude;

public class IceWall implements Movable {
    public static final int UPPERBOUND = 5;
    public static final int LOWERBOUND = 1;

    private Position position;

    public IceWall(){
        this.setPosition( generate_IceWall() );
    }

    public static Position generate_IceWall(){
        int row_number = generate_exclude(UPPERBOUND,LOWERBOUND);
        int col_number = generate_exclude(UPPERBOUND,LOWERBOUND);

        Position position = new Position(row_number, col_number);
        return position;
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
