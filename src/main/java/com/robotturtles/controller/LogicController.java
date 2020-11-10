package com.robotturtles.controller;

import com.robotturtles.model.*;

public class LogicController {
    private static final int FORWARD_CARD = 1;
    private static final int LEFT_TURN_CARD = 2;
    private static final int RIGHT_TURN_CARD = 3;
    private static final int BUG_CARD = 4;

    private Game game;

    public LogicController(Game game) {
        this.game = game;
    }

    /**
     * Helper method to check if the number of players entered is valid or not
     * @param numOfPlayers number of players entered
     * @return true if the number of players entered is valid; false otherwise
     */
    static boolean validateNumOfPlayers(int numOfPlayers) {
        return numOfPlayers >= 1 && numOfPlayers <= 4;
    }

    public Card cardFromCardNumber(int cardNumber) {
        Card card;
        switch (cardNumber) {
            case FORWARD_CARD:
                card = new Card(new ForwardMove());
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
            default:
                card = null;
        }
        return card;
    }

    public void addPlayerToGame(String[] playerNames, Game game) {
        for (int i = 0; i < playerNames.length; i++) {
            game.addPlayer(playerNames[i], i);
        }
    }
}
