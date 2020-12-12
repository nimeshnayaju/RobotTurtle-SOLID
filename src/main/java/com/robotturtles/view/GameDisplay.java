package com.robotturtles.view;

import com.robotturtles.controller.DisplayFormat;
import com.robotturtles.controller.ManipulateModel;
import com.robotturtles.model.game.Board;
import com.robotturtles.model.tile.Direction;

import java.util.ArrayList;

public class GameDisplay {
    private static final int ROW_INDEX = 0;
    private static final int COLUMN_INDEX = 1;

    ManipulateModel controllerModel;
    String[][] commandLineBoard;

    /**
     * Displays the Board in terminal
     */
    public void displayBoard() {
        commandLineBoard = new String[Board.NUM_OF_ROWS][Board.NUM_OF_COLS];
        populateBoard();
        printBoard();
    }

    /**
     * Prints the board in command line
     */
    private void printBoard() {
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

    /**
     * Populates the board with tiles
     */
    private void populateBoard() {
        populateTile(controllerModel.getCrateInfo(), crate());
        populateTile(controllerModel.getStoneWallInfo(), stoneWall());
        populateTile(controllerModel.getPortalInfo(), portal());
        populateTile(controllerModel.getIceWallInfo(), iceWall());
        populateTurtle(controllerModel.getTurtleInfo());
        populateJewel(controllerModel.getJewelInfo());
    }

    private void populateTile(ArrayList<DisplayFormat> tileInfo, String tileString) {
        for (DisplayFormat tile : tileInfo) {
            int row = tile.getPosition()[ROW_INDEX];
            int col = tile.getPosition()[COLUMN_INDEX];
            commandLineBoard[row][col] = tileString;
        }
    }

    private void populateTurtle(ArrayList<DisplayFormat> turtleInfo) {
        for (DisplayFormat turtle : turtleInfo) {
            int row = turtle.getPosition()[ROW_INDEX];
            int col = turtle.getPosition()[COLUMN_INDEX];
            commandLineBoard[row][col] = turtle(turtle.getDirection(), turtle.isActive());
        }
    }

    private void populateJewel(ArrayList<DisplayFormat> jewelInfo) {
        for (DisplayFormat jewel : jewelInfo) {
            int row = jewel.getPosition()[ROW_INDEX];
            int col = jewel.getPosition()[COLUMN_INDEX];
            commandLineBoard[row][col] = jewel(jewel.isActive());
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
        String portalString = " ⬬ ";
        return portalString;
    }
  
    private String iceWall() {
        String iceWallString = " * ";
        return iceWallString;
    }

    private String crate() {
        String crateString = " □ ";
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
