package com.robotturtles.controller;

import com.robotturtles.model.*;
import com.robotturtles.view.GameDisplay;

import java.util.Scanner;

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
        PromptController promptController = new PromptController();
        int numOfPlayers = promptController.promptNumOfPlayers(gameDisplay, sc);
        robotTurtleGame = new Game(numOfPlayers);
        gameDisplay.setControllerModel(new ManipulateModel(robotTurtleGame));

        LogicController logicController = new LogicController(robotTurtleGame);

        logicController.addPlayerToGame(promptController.promptPlayerNames(gameDisplay, sc, numOfPlayers), robotTurtleGame);
        do {
            gameDisplay.displayBoard();
            int cardNumber = promptController.promptChooseCards(gameDisplay, sc, robotTurtleGame.getTurn(), robotTurtleGame.getCurrentPlayerName());
            Card cardChosen = logicController.cardFromCardNumber(cardNumber);
            while (!robotTurtleGame.isValidCard(cardChosen)) {
                gameDisplay.displayMessage("invalid card chosen (causes a collision/moves turtle out of board)! please select another card\n");
                cardNumber = promptController.promptChooseCards(gameDisplay, sc, robotTurtleGame.getTurn(), robotTurtleGame.getCurrentPlayerName());
                cardChosen = logicController.cardFromCardNumber(cardNumber);
            }
            robotTurtleGame.makeMove(cardChosen);
            int turn = robotTurtleGame.getTurn();
            String playerName = robotTurtleGame.getCurrentPlayerName();
            if (robotTurtleGame.checkForPlayerWin()) {
                gameDisplay.displayMessage("Player " + (turn + 1) + " (" + playerName + ")" + " has successfully completed the game!\n");
            }
            if (!robotTurtleGame.checkForGameCompletion()) {
                robotTurtleGame.assignTurnToNextPlayer();
            }
        } while (robotTurtleGame.getGameState() == GameState.IN_PROGRESS);
    }


}
