package com.robotturtles.controller;

import com.robotturtles.view.GameDisplay;

import java.util.Scanner;

import static com.robotturtles.controller.LogicController.validateNumOfPlayers;

public class PromptController {

    /**
     * Displays a prompt to enter the number of players
     * @return (valid) number of players
     */
    static int promptNumOfPlayers(GameDisplay display, Scanner sc) {
        int numOfPlayers = Integer.MIN_VALUE;
        while (!validateNumOfPlayers(numOfPlayers)) {
            display.displayMessage("Enter the number of players (1 to 4): ");
            String numOfPlayersString = sc.nextLine();
            while (!numOfPlayersString.matches("\\d+")) {
                display.displayMessage("Please enter a valid number (1 to 4): ");
                numOfPlayersString = sc.nextLine();
            }
            numOfPlayers = Integer.parseInt(numOfPlayersString);
        }
        return numOfPlayers;
    }

    /**
     * Prompts each player for their name
     * @return an array containing the list of the player name
     */
    static String[] promptPlayerNames(GameDisplay display, Scanner sc, int numOfPlayers) {
        String[] playerNames = new String[numOfPlayers];
        for (int i = 1; i <= numOfPlayers; i++) {
            display.displayMessage("Enter Player " + i + "'s name: ");
            String playerName = sc.nextLine();
            playerNames[i-1] = playerName;
        }
        return playerNames;
    }

    static int promptChooseCards(GameDisplay display, Scanner sc, int turn, String playerName) {
        display.displayMessage("Turn: Player " + (turn + 1) + " ("+ playerName + ")\n");
        display.displayMessage("Select a card from the following options: ");
        display.displayCards();
        String cardChosenString = sc.nextLine();
        while (!cardChosenString.matches("\\d+")) {
            display.displayMessage("Select a valid card from the following options: ");
            display.displayCards();
            cardChosenString = sc.nextLine();
        }
        return Integer.parseInt(cardChosenString);
    }
}
