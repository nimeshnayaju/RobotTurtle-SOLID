package com.robotturtles.model;
import java.util.ArrayList;
import java.util.List;

public class Laser implements IMove {
    private Game game;

    public Laser(Game game){
        this.game = game;
    }
    @Override
    public void execute(Turtle tile){
        Position iceWallPosition = getIceWallPosition(tile);
        List<IceWall> iceWallList = game.getIceWallLst();

        IceWall tobeRemoved = null;
        for (IceWall icewall: iceWallList) {
            if(icewall.getPosition().equals(iceWallPosition)){
                tobeRemoved = icewall;
            }
        }
        iceWallList.remove(tobeRemoved);
    }

    private Position getIceWallPosition(Turtle tile){
        Direction currDirection = tile.getDirection();
        Position currPosition = tile.getPosition();

        int rowNumber = currPosition.getRowNumber();
        int colNumber = currPosition.getColNumber();

        Position newPosition;

        switch (currDirection) {
            case NORTH:
                newPosition = new Position(rowNumber-1, colNumber);
                break;
            case SOUTH:
                newPosition = new Position(rowNumber+1, colNumber);
                break;
            case EAST:
                newPosition = new Position(rowNumber, colNumber+1);
                break;
            case WEST:
                newPosition = new Position(rowNumber, colNumber-1);
                break;
            default:
                newPosition = null;
        }
        return newPosition;
    }
}
