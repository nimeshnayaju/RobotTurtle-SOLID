package com.robotturtles.model.game;

import com.robotturtles.model.tile.TileInfo;
import com.robotturtles.model.tile.*;

import java.util.ArrayList;

public class GameBoardInfo {
    private Game game;
    private int numOfPlayers;

    public GameBoardInfo(Game game) {
        this.game = game;
        this.numOfPlayers = game.getNumOfPlayers();
    }

    public ArrayList<TileInfo> getAllTurtlesInfo() {
        ArrayList<TileInfo> info = new ArrayList<>(numOfPlayers);
        for (Player player: game.getPlayers().values()) {
            Position position = player.getTurtle().getPosition();
            Direction direction = player.getTurtle().getDirection();
            boolean active = player.getPlayerId() == game.getTurn();
            info.add(new TileInfo(position, direction, active));
        }
        return info;
    }


    public ArrayList<TileInfo> getAllStoneWallInfo() {
        ArrayList<TileInfo> info = new ArrayList<>(numOfPlayers);
        for (StoneWall stoneWall: game.getStoneWalls()) {
            Position position = stoneWall.getPosition();
            info.add(new TileInfo(position));
        }
        return info;
    }

    public ArrayList<TileInfo> getAllPortalInfo() {
        ArrayList<TileInfo> info = new ArrayList<>(Game.NUM_OF_INDIVIDUAL_PORTAL);
        info.add(new TileInfo(game.getPortalPair().getPosition()));
        info.add(new TileInfo(game.getPortalPair().getOtherPair().getPosition()));
        return info;
    }

    public ArrayList<TileInfo> getAllCrateInfo() {
        ArrayList<TileInfo> info = new ArrayList<>(numOfPlayers);
        for (Crate crate : game.getCrates()) {
            Position position = crate.getPosition();
            info.add(new TileInfo(position));
        }
        return info;
    }

    public ArrayList<TileInfo> getAllIceWallInfo() {
        ArrayList<TileInfo> info = new ArrayList<>(numOfPlayers);
        for (IceWall iceWall: game.getIceWalls()) {
            if (iceWall.getPosition() != null) {
                info.add(new TileInfo(iceWall.getPosition()));
            }
        }
        return info;
    }

    public ArrayList<TileInfo> getAllJewelInfo() {
        ArrayList<TileInfo> info = new ArrayList<>(numOfPlayers);
        for (Player player: game.getPlayers().values()) {
            Position position = player.getJewel().getPosition();
            boolean active = player.getPlayerId() == game.getTurn();
            info.add(new TileInfo(position, null, active));
        }
        return info;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }
}


