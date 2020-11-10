package com.robotturtles.view;

import com.robotturtles.controller.DisplayFormat;
import com.robotturtles.controller.ManipulateModel;
import com.robotturtles.model.*;

public class GameDisplay {
    private static final int ROW_INDEX = 0;
    private static final int COLUMN_INDEX = 1;

    ManipulateModel controllerModel;

    /**
     * Displays the Board in terminal
     */
    public void displayBoard() {
        String[][] commandLineBoard = new String[Board.NUM_OF_ROWS][Board.NUM_OF_COLS];

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

    public void displayCards() {
        System.out.println("Make your choice: [1] forward; [2] Turn left; [3] Turn Right; [4] Bug");
    }

    /**
     * Displays the specified message to the terminal
     * @param message message to be displayed
     */
    public void displayMessage(String message) {
        System.out.printf("%s", message);
    }
}
