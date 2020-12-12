package com.robotturtles.controller;

import com.robotturtles.model.Game;
import com.robotturtles.model.Position;
import com.robotturtles.model.TileInfo;

import java.util.ArrayList;

public class ManipulateModel {
    private Game game;

    public ManipulateModel(Game game) {
        this.game = game;
    }

    public ArrayList<DisplayFormat> getTurtleInfo() {
        return manipulateTileInfos(game.getAllTurtlesInfo());
    }

    public ArrayList<DisplayFormat> getJewelInfo() {
        return manipulateTileInfos(game.getAllJewelInfo());
    }

    public ArrayList<DisplayFormat> getStoneWallInfo() {
        return manipulateTileInfos(game.getAllStoneWallInfo());
    }

    public ArrayList<DisplayFormat> getIceWallInfo() {
        return manipulateTileInfos(game.getAllIceWallInfo());
    }

    private ArrayList<DisplayFormat> manipulateTileInfos(ArrayList<TileInfo> infos) {
        ArrayList<DisplayFormat> manipulatedInfos = new ArrayList<>(game.getNumOfPlayers());
        for (TileInfo info : infos) {
            manipulatedInfos.add(manipulateInfo(info));
        }
        return manipulatedInfos;
    }

    private DisplayFormat manipulateInfo(TileInfo info) {
        int[] position = new int[]{info.getPosition().getRowNumber(), info.getPosition().getColNumber()};
        return new DisplayFormat(position, info.getDirection(), info.isActive());
    }


}
