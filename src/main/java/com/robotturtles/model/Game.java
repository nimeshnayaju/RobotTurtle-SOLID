package com.robotturtles.model;

import java.util.ArrayList;
import java.util.HashMap;
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

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void addPlayer(String playerName, int playerId) {
        Player newPlayer = new Player(playerName, playerId);
        this.players.put(playerId, newPlayer);
        setUpPlayerTiles(newPlayer);
        setUpPlayerJewel(newPlayer);
    }

    public void setUpPlayerTiles(Player player) {
        this.board.setUpTile(player.getTurtle());
    }

    public void setUpPlayerJewel(Player player) {
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
        gameState  = state;
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

    public void setTurn(int turn){
        this.turn = turn;
    }

    public HashMap<Integer,Player> getSetPlayers(){
        return players;
    }

    /**
     * Returns the player name associated with the specified turn
     */
    public String getCurrentPlayerName() {
        return getCurrentPlayer().getPlayerName();
    }

    public Player getCurrentPlayer() {
        return this.players.get(turn);
    }

    public ArrayList<TileInfo> getAllTurtlesInfo() {
        ArrayList<TileInfo> info = new ArrayList<>(numOfPlayers);
        for (Player player: players.values()) {
            Position position = player.getTurtle().getPosition();
            Direction direction = player.getTurtle().getDirection();
            boolean active = player.getPlayerId() == getTurn();
            info.add(new TileInfo(position, direction, active));
        }
        return info;
    }

    public ArrayList<TileInfo> getAllJewelInfo() {
        ArrayList<TileInfo> info = new ArrayList<>(numOfPlayers);
        for (Player player: players.values()) {
            Position position = player.getJewel().getPosition();
            boolean active = player.getPlayerId() == getTurn();
            info.add(new TileInfo(position, null, active));
        }
        return info;
    }
}
