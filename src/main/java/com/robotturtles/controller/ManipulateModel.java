package com.robotturtles.controller;

import com.robotturtles.model.game.GameBoardInfo;
import com.robotturtles.model.tile.TileInfo;

import java.util.ArrayList;

public class ManipulateModel {
    private GameBoardInfo gameInfo;

    public ManipulateModel(GameBoardInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public ArrayList<DisplayFormat> getTurtleInfo() {
        return manipulateTileInfos(gameInfo.getAllTurtlesInfo());
    }

    public ArrayList<DisplayFormat> getJewelInfo() {
        return manipulateTileInfos(gameInfo.getAllJewelInfo());
    }

    public ArrayList<DisplayFormat> getStoneWallInfo() {
        return manipulateTileInfos(gameInfo.getAllStoneWallInfo());
    }

    public ArrayList<DisplayFormat> getPortalInfo() {
        return manipulateTileInfos(gameInfo.getAllPortalInfo());
    }

    public ArrayList<DisplayFormat> getCrateInfo() {
        return manipulateTileInfos(gameInfo.getAllCrateInfo());
    }
  
    public ArrayList<DisplayFormat> getIceWallInfo() {
        return manipulateTileInfos(gameInfo.getAllIceWallInfo());
    }

    private ArrayList<DisplayFormat> manipulateTileInfos(ArrayList<TileInfo> infos) {
        ArrayList<DisplayFormat> manipulatedInfos = new ArrayList<>(gameInfo.getNumOfPlayers());
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
