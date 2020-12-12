package com.robotturtles.model;

import java.util.Random;

public class StoneWall implements Movable {
    public static final int UPPERBOUND = 5;
    public static final int LOWERBOUND = 1;

    private Position position;

    public StoneWall(){
        this.setPosition( generate_stone_wall() );
    }

    private static Position generate_stone_wall(){
        int row_number = generate_exclude(UPPERBOUND,LOWERBOUND);
        int col_number = generate_exclude(UPPERBOUND,LOWERBOUND);

        Position position = new Position(row_number, col_number);
        return position;
    }

    private static int generate_exclude(int upperbound, int lowerbound){
        Random rand= new Random();
        int generate_num;
        do{
            generate_num = rand.nextInt((upperbound - lowerbound) + 1) + lowerbound;
        } while(generate_num == 3 || generate_num == 4 || generate_num == 1); // stonewalls should not overlap jewels or other tiles
        return generate_num;
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
