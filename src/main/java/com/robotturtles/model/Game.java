package com.robotturtles.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Stack;

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

    // -- Operations --//
    /**
     * Assigns turn to the next player
     */
    public void assignTurnToNextPlayer() {
        turn = (turn + 1) % numOfPlayers;
        while (players.get(turn) == null) {
            turn = (turn + 1) % numOfPlayers;
        }
    }

    public void makeMove(Card card) {
        Player currentPlayer = getCurrentPlayer();
        Position oldPosition = currentPlayer.getTurtle().getPosition();
        card.play(getCurrentPlayer().getTurtle());
        board.makeMove(oldPosition, currentPlayer.getTurtle().getPosition(), currentPlayer.getTurtle());
    }

    // -- Getters/Setters --//
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

    public void setGameState(GameState state){
        this.gameState = state;
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

    public Player getCurrentPlayer() {
        return this.players.get(turn);
    }


    public HashMap<Integer,Player> getSetPlayer(){
        return this.players;
    }



}
