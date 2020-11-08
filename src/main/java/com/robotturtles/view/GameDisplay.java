package com.robotturtles.view;

import com.robotturtles.model.Board;
import com.robotturtles.model.Deck;

public class GameDisplay {

    /**
     * Displays the Board in terminal
     * @param gameBoard board to be displayed
     */
    public void displayBoard(Board gameBoard) {
        // display board here
    }

    public void displayDeck(Deck deck) {
        // display deck here
    }

    /**
     * Displays the specified message to the terminal
     * @param message message to be displayed
     */
    public void displayMessage(String message) {
        System.out.printf("%s", message);
    }
}
