package com.robotturtles.view;

import com.robotturtles.controller.DisplayFormat;
import com.robotturtles.controller.ManipulateModel;
import com.robotturtles.model.game.Board;
import com.robotturtles.model.tile.Direction;

public class GameDisplay {
    private static final int ROW_INDEX = 0;
    private static final int COLUMN_INDEX = 1;

    ManipulateModel controllerModel;

    /**
     * Displays the Board in terminal
     */
    public void displayBoard() {
        String[][] commandLineBoard = new String[Board.NUM_OF_ROWS][Board.NUM_OF_COLS];

        for (DisplayFormat portalInfo : controllerModel.getPortalInfo()) {
            int rowNumber = portalInfo.getPosition()[ROW_INDEX];
            int colNumber = portalInfo.getPosition()[COLUMN_INDEX];
            commandLineBoard[rowNumber][colNumber] = portal();
        }

        for (DisplayFormat crateInfo : controllerModel.getCrateInfo()) {
            int rowNumber = crateInfo.getPosition()[ROW_INDEX];
            int colNumber = crateInfo.getPosition()[COLUMN_INDEX];
            commandLineBoard[rowNumber][colNumber] = crate();
        }

        for (DisplayFormat turtleInfo : controllerModel.getTurtleInfo()) {
            int rowNumber = turtleInfo.getPosition()[ROW_INDEX];
            int colNumber = turtleInfo.getPosition()[COLUMN_INDEX];
            commandLineBoard[rowNumber][colNumber] = turtle(turtleInfo.getDirection(), turtleInfo.isActive());
        }

        for (DisplayFormat jewelInfo : controllerModel.getJewelInfo()) {
            int rowNumber = jewelInfo.getPosition()[ROW_INDEX];
            int colNumber = jewelInfo.getPosition()[COLUMN_INDEX];
            commandLineBoard[rowNumber][colNumber] = jewel(jewelInfo.isActive());
        }

        for (DisplayFormat stoneWallInfo : controllerModel.getStoneWallInfo()) {
            int rowNumber = stoneWallInfo.getPosition()[ROW_INDEX];
            int colNumber = stoneWallInfo.getPosition()[COLUMN_INDEX];
            commandLineBoard[rowNumber][colNumber] = stoneWall();
        }

        for (DisplayFormat iceWallInfo : controllerModel.getIceWallInfo()) {
            int rowNumber = iceWallInfo.getPosition()[ROW_INDEX];
            int colNumber = iceWallInfo.getPosition()[COLUMN_INDEX];
            commandLineBoard[rowNumber][colNumber] = iceWall();
        }

        for (int row = 0; row < commandLineBoard.length; row++) {
            for (int col = 0; col < commandLineBoard[row].length; col++) {
                if (commandLineBoard[row][col] == null) {
                    System.out.print("   ");
                } else {
                    System.out.print(commandLineBoard[row][col]);
                }
                if (col < commandLineBoard[row].length - 1) {
                    System.out.print("|");
                } else {
                    System.out.println();
                }
            }
        }
    }

    public void setControllerModel(ManipulateModel controllerModel) {
        this.controllerModel = controllerModel;
    }

    private String turtle(Direction direction, boolean active) {
        String turtleString = active ? "(" : " ";
        if (direction == Direction.NORTH) {
            turtleString += "↑";
        } else if (direction == Direction.SOUTH) {
            turtleString += "↓";
        } else if (direction == Direction.EAST) {
            turtleString += "→";
        } else {
            turtleString += "←";
        }
        turtleString += active ? ")" : " ";
        return turtleString;
    }

    private String jewel(boolean active) {
        String jewelString = active ? "(" : " ";
        jewelString += "J" + (active ? ")" : " ");
        return jewelString;
    }

    private String stoneWall() {
        String stoneWallString = " S ";
        return stoneWallString;
    }

    private String portal() {
        String portalString = " P ";
        return portalString;
    }
  
    private String iceWall() {
        String iceWallString = " * ";
        return iceWallString;
    }

    private String crate() {
        String crateString = " C ";
        return crateString;
    }

    public void displayCards() {
        System.out.println("Make your choice: [1] Forward; [2] Turn left; [3] Turn Right; [4] Bug; [5] Laser");
    }

    /**
     * Displays the specified message to the terminal
     * @param message message to be displayed
     */
    public void displayMessage(String message) {
        System.out.printf("%s", message);
    }
}
