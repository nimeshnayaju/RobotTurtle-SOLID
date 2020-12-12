package com.robotturtles.controller;

import com.robotturtles.model.card.*;
import com.robotturtles.model.game.Game;
import com.robotturtles.model.game.GameBoardInfo;
import com.robotturtles.model.game.GameManipulate;
import com.robotturtles.model.game.GameState;
import com.robotturtles.view.GameDisplay;

import java.util.Scanner;


public class RobotTurtleController {
    private static final int FORWARD_CARD = 1;
    private static final int LEFT_TURN_CARD = 2;
    private static final int RIGHT_TURN_CARD = 3;
    private static final int BUG_CARD = 4;
    private static final int LASER_CARD = 5;

    private Scanner sc;
    private GameDisplay gameDisplay;
    private Game robotTurtleGame;
    GameManipulate gameManipulate;

    /**
     * RobotTurtleController Constructor
     */
    public RobotTurtleController() {
        sc = new Scanner(System.in);
        gameDisplay = new GameDisplay();
    }

    /**
     * Main method for running RobotTurtleController
     */
    public static void main(String[] args) {
        RobotTurtleController robotTurtleController = new RobotTurtleController();
        robotTurtleController.startGame();
    }

    /**
     * Starts and initialize the game of RobotTurtle
     */
    private void startGame() {
        int numOfPlayers = promptNumOfPlayers();

        robotTurtleGame = new Game(numOfPlayers);
        gameManipulate = new GameManipulate(robotTurtleGame);

        gameDisplay.setControllerModel(new ManipulateModel(new GameBoardInfo(robotTurtleGame)));

        addPlayerToGame(promptPlayerNames(numOfPlayers));
        playGame();
    }

    /**
     * Plays the game until the game is completed by all players
     */
    private void playGame() {
        do {
            gameDisplay.displayBoard();

            Card card = chooseCard();

            gameManipulate.makeMove(card);

            updateGameState();

        } while (robotTurtleGame.getGameState() == GameState.IN_PROGRESS);
    }

    /**
     * Displays a prompt to enter the number of players
     * @return (valid) number of players
     */
    private int promptNumOfPlayers() {
        int numOfPlayers = Integer.MIN_VALUE;
        while (!validateNumOfPlayers(numOfPlayers)) {
            gameDisplay.displayMessage("Enter the number of players (1 to 4): ");
            String numOfPlayersString = sc.nextLine();
            while (!numOfPlayersString.matches("\\d+")) {
                gameDisplay.displayMessage("Please enter a valid number (1 to 4): ");
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
    private String[] promptPlayerNames(int numOfPlayers) {
        String[] playerNames = new String[numOfPlayers];
        for (int i = 1; i <= numOfPlayers; i++) {
            gameDisplay.displayMessage("Enter Player " + i + "'s name: ");
            String playerName = sc.nextLine();
            playerNames[i-1] = playerName;
        }
        return playerNames;
    }

    /**
     * Prompts the player with specified turn to choose a card
     * @return the integer value corresponding to card selected
     */
    private int promptChooseCards(int turn, String playerName) {
        gameDisplay.displayMessage("Turn: Player " + (turn + 1) + " ("+ playerName + ")\n");
        gameDisplay.displayMessage("Select a card from the following options: ");
        gameDisplay.displayCards();
        String cardChosenString = sc.nextLine();
        while (!cardChosenString.matches("\\d+")) {
            gameDisplay.displayMessage("Select a valid card from the following options: ");
            gameDisplay.displayCards();
            cardChosenString = sc.nextLine();
        }
        return Integer.parseInt(cardChosenString);
    }


    /**
     * Asks the user to choose a (valid card)
     * @return (valid) Card chosen
     */
    private Card chooseCard() {
        int cardNumber = promptChooseCards(robotTurtleGame.getTurn(), robotTurtleGame.getCurrentPlayerName());

        Card cardChosen = cardFromCardNumber(cardNumber);

        while (!gameManipulate.isValidCard(cardChosen)) {
            gameDisplay.displayMessage("invalid card chosen (causes a collision/moves turtle out of board/invalid laser)! please select another card\n");
            cardNumber = promptChooseCards(robotTurtleGame.getTurn(), robotTurtleGame.getCurrentPlayerName());
            cardChosen = cardFromCardNumber(cardNumber);
        }
        return cardChosen;
    }


    /**
     * Adds the specified players into the game
     */
    private void addPlayerToGame(String[] playerNames) {
        for (int i = 0; i < playerNames.length; i++) {
            robotTurtleGame.addPlayer(playerNames[i], i);
        }
    }

    /**
     * Updates the game state after each move
     */
    private void updateGameState() {
        int turn = robotTurtleGame.getTurn();
        String playerName = robotTurtleGame.getCurrentPlayerName();
        if (gameManipulate.checkForPlayerWin()) {
            gameDisplay.displayMessage("Player " + (turn + 1) + " (" + playerName + ")" + " has successfully completed the game!\n");
        }
        if (!gameManipulate.checkForGameCompletion()) {
            gameManipulate.assignTurnToNextPlayer();
        }
    }

    /**
     * Helper method to check if the number of players entered is valid or not
     * @param numOfPlayers number of players entered
     * @return true if the number of players entered is valid; false otherwise
     */
    static boolean validateNumOfPlayers(int numOfPlayers) {
        return numOfPlayers >= 1 && numOfPlayers <= 4;
    }

    /**
     * Returns Card associated with the card number
     */
    private Card cardFromCardNumber(int cardNumber) {
        Card card;
        switch (cardNumber) {
            case FORWARD_CARD:
                card = new Card(new Forward());
                break;
            case LEFT_TURN_CARD:
                card = new Card(new LeftTurn());
                break;
            case RIGHT_TURN_CARD:
                card = new Card(new RightTurn());
                break;
            case BUG_CARD:
                card = new Card(new Bug());
                break;
            case LASER_CARD:
                card = new Card(new Laser());
                break;
            default:
                card = null;
        }
        return card;
    }
}
