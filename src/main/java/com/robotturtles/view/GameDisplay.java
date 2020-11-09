package com.robotturtles.view;

import com.robotturtles.model.*;

public class GameDisplay {

    /**
     * Displays the Board in terminal
     * @param gameBoard board to be displayed
     */
    public void displayBoard(Board gameBoard) {
        // display board here
        for (int i = 0; i < Board.NUM_OF_ROWS; i++) {
            for (int j = 0; j < Board.NUM_OF_COLS; j++) {
                if (gameBoard.isOccupied(i, j)) {
                    BasicTile currTile= gameBoard.getTile(i, j);
                    if(currTile instanceof Turtle) {
                        Direction currDirection = ((Turtle) currTile).getDirection();
                        System.out.print(printTurtle(currTile,currDirection));
                    }else if(currTile instanceof Jewel){
                        System.out.print(" J ");
                    }
                } else {
                    System.out.print(" ✕ ");
                }
                if (j != Board.NUM_OF_COLS -1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public String printTurtle(BasicTile currentTurtle,Direction direction) {
        String turtleString = "";
        if (direction == Direction.NORTH) {
            turtleString += " ↑ ";
        } else if (direction == Direction.SOUTH) {
            turtleString += " ↓ ";
        } else if (direction == Direction.EAST) {
            turtleString += " → ";
        } else {
            turtleString += " ← ";
        }
        return turtleString;
    }

    public void displayDeck(/*Deck deck*/) {
        // display deck here
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
