package com.robotturtles.controller;

import com.robotturtles.model.Card;
import com.robotturtles.model.ForwardMove;
import com.robotturtles.model.LeftTurn;
import com.robotturtles.model.RightTurn;

public class LogicController {
    private static final int FORWARD_CARD = 1;
    private static final int LEFT_TURN_CARD = 2;
    private static final int Right_TURN_CARD = 3;

    /**
     * Helper method to check if the number of players entered is valid or not
     * @param numOfPlayers number of players entered
     * @return true if the number of players entered is valid; false otherwise
     */
    static boolean validateNumOfPlayers(int numOfPlayers) {
        return numOfPlayers >= 1 && numOfPlayers <= 4;
    }

    static Card cardFromCardNumber(int cardNumber) {
        Card card;
        switch (cardNumber) {
            case FORWARD_CARD:
                card = new Card(new ForwardMove());
                break;
            case LEFT_TURN_CARD:
                card = new Card(new LeftTurn());
                break;
            case Right_TURN_CARD:
                card = new Card(new RightTurn());
                break;
            default:
                card = null;
        }
        return card;
    }
}
