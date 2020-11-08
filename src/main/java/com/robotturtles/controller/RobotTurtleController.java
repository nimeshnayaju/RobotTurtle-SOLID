package com.robotturtles.controller;

import com.robotturtles.model.*;
import com.robotturtles.view.GameDisplay;

import java.util.Scanner;

import static com.robotturtles.controller.LogicController.cardFromCardNumber;
import static com.robotturtles.controller.PromptController.*;

public class RobotTurtleController {
    private Scanner sc;
    private GameDisplay gameDisplay;
    private Game robotTurtleGame;

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
    public static void main(String[] args) throws CloneNotSupportedException {
        RobotTurtleController robotTurtleController = new RobotTurtleController();
        robotTurtleController.playGame();
    }

    /**
     * Starts the game of RobotTurtle
     */
    private void playGame() {
        int numOfPlayers = promptNumOfPlayers(gameDisplay, sc);
        robotTurtleGame = new Game(numOfPlayers);
        addPlayerToGame(promptPlayerNames(gameDisplay, sc, numOfPlayers), robotTurtleGame);
        do {
            gameDisplay.displayBoard(robotTurtleGame.getBoard());
            int cardNumber = promptChooseCards(gameDisplay, sc, robotTurtleGame.getTurn(), robotTurtleGame.getCurrentPlayerName(), robotTurtleGame.getCurrentPlayerDeck());
            Card cardChosen = cardFromCardNumber(cardNumber);
            while (!robotTurtleGame.isValidCard(cardChosen)) {
                gameDisplay.displayMessage("invalid card chosen (causes a collision/moves turtle out of board)! please select another card: ");
                cardNumber = promptChooseCards(gameDisplay, sc, robotTurtleGame.getTurn(), robotTurtleGame.getCurrentPlayerName(), robotTurtleGame.getCurrentPlayerDeck());
                cardChosen = cardFromCardNumber(cardNumber);
            }
            robotTurtleGame.makeMove(cardChosen);
        } while (robotTurtleGame.getGameState() == GameState.IN_PROGRESS);
    }



    static void addPlayerToGame(String[] playerNames, Game game) {
        for (int i = 0; i < playerNames.length; i++) {
            game.addPlayer(playerNames[i], i);
        }
    }
}
