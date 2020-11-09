package com.robotturtles.model;

import java.util.HashMap;

public class Game {
    private static final int INITIAL_TURN = 0;

    private int numOfPlayers;
    private Board board;
    private int turn;
    private HashMap<Integer, Player> players;
    private GameState gameState;

    public Game(int numOfPlayers) {
        setNumOfPlayers(numOfPlayers);
        this.board = new Board();
        this.turn = INITIAL_TURN;
        this.players = new HashMap<>();
        this.gameState = GameState.IN_PROGRESS;
    }

    /**
     * Sets the number of players to the number specified
     */
    private void setNumOfPlayers(int numOfPlayers) {
        if (numOfPlayers >= 1 && numOfPlayers <= 4)  {
            this.numOfPlayers = numOfPlayers;
        } else {
            throw new IllegalArgumentException("invalid number of players");
        }
    }

    public void addPlayer(String playerName, int playerId) {
        Player newPlayer = new Player(playerName, playerId);
        this.players.put(playerId, newPlayer);
        setUpPlayerTiles(newPlayer);
        setUpPlayerJewel(newPlayer);
    }

    private void setUpPlayerTiles(Player player) {
        this.board.setUpTile(player.getTurtle());
    }

    private void setUpPlayerJewel(Player player) {
        this.board.setUpTile(player.getJewel());
    }
    /**
     * Method to return the current state of the game
     * @return current state of the game
     */
    public GameState getGameState() {
        return this.gameState;
    }

    /**
     * Method to return the Board
     * @return game board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Method to return the current turn in the game
     * @return turn represented by the player id
     */
    public int getTurn() {
        return this.turn;
    }


    /**
     * Returns the player name associated with the specified turn
     */
    public String getCurrentPlayerName() {
        return getCurrentPlayer().getPlayerName();
    }

    /**
     * Returns the deck of the current turn
     * @return Deck object of the current player in turn
     */
    public Deck getCurrentPlayerDeck() {
        return getCurrentPlayer().getDeck();
    }

    private Player getCurrentPlayer() {
        return this.players.get(turn);
    }

    /**
     * Assigns turn to the next player
     */
    public void assignTurnToNextPlayer() {
        turn = (turn + 1) % numOfPlayers;
        while (players.get(turn) == null) {
            turn = (turn + 1) % numOfPlayers;
        }
    }

    public boolean isValidCard(Card card) {
        MovableTile currentPlayerTurtle = getCurrentPlayer().getTurtle();
        Position currPosition = new Position(currentPlayerTurtle.getPosition().getRowNumber(), currentPlayerTurtle.getPosition().getColNumber());
        MovableTile clonedTurtle = new Turtle(currPosition, currentPlayerTurtle.getDirection());
        card.play(clonedTurtle);
        // If the turtle position doesn't change, the card is valid
        if (clonedTurtle.getPosition().equals(currPosition)) return true;

        if (causesEscapingTheBoard(clonedTurtle.getPosition())) return false;
        if (causesCollision(clonedTurtle.getPosition())) return false;

        return true;
    }

    private boolean causesCollision(Position position) {
        if (this.board.isOccupied(position) && !position.equals(getCurrentPlayer().getJewel().getPosition())) {
            return true;
        }
        return false;
    }

    private boolean causesEscapingTheBoard(Position position) {
        if (position.getRowNumber() >= Board.NUM_OF_ROWS || position.getRowNumber() < 0 || position.getColNumber() >= Board.NUM_OF_COLS || position.getColNumber() <0) {
            return true;
        }
        return false;
    }

    public void makeMove(Card card) {
        Player currentPlayer = getCurrentPlayer();
        Position oldPosition = currentPlayer.getTurtle().getPosition();
        card.play(getCurrentPlayer().getTurtle());
        board.makeMove(oldPosition, currentPlayer.getTurtle().getPosition(), currentPlayer.getTurtle());
    }

    /**
     * Checks if the executing the move results in the player successfully completing the game
     * @return true if the move results in the player completing the game; false otherwise
     */
    public boolean checkForPlayerWin() {
        // Check if the executing the move results in the player successfully completing the game
        // Set the GameState to Completed if all players have finished the game
        Position turtlePosition = getCurrentPlayer().getTurtle().getPosition();
        Position jewelPosition = getCurrentPlayer().getJewel().getPosition();
        if (turtlePosition.equals(jewelPosition)) {
            this.players.remove(getTurn());
            return true;
        }
        return false;
    }

    /**
     * Checks if the executing the move results in the all players completing the game
     * @return true if the move results in the all players completing the game; false otherwise
     */
    public boolean checkForGameCompletion() {
        if (players.isEmpty()) {
            this.gameState = GameState.COMPLETED;
            return true;
        }
        return false;
    }

}
