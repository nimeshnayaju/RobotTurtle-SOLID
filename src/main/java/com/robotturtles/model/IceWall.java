package com.robotturtles.model;

import java.util.Random;

public class IceWall extends BasicTile{
    public static final int UPPERBOUND = 5;
    public static final int LOWERBOUND = 1;

    public IceWall(Position position){
        super(position);
        this.setPosition( generate_IceWall() );
    }

    public static Position generate_IceWall(){
        int row_number = generate_exclude(UPPERBOUND,LOWERBOUND);
        int col_number = generate_exclude(UPPERBOUND,LOWERBOUND);

        Position position = new Position(row_number, col_number);
        return position;
    }

    public static int generate_exclude(int upperbound, int lowerbound){
        Random rand= new Random();
        int generate_num;
        do{
            generate_num = rand.nextInt((upperbound - lowerbound) + 1) + lowerbound;
        }while(generate_num == 3 || generate_num == 4);
        return generate_num;
    }
}
