package com.robotturtles.model;

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

    public boolean isValidCard(Card card) {
        if (card == null) return false;

        MovableTile currentPlayerTurtle = getCurrentPlayer().getTurtle();
        MovableTile clonedTurtle = copyTileContents(currentPlayerTurtle);

        card.play(clonedTurtle);
        // If the turtle position doesn't change, the card is valid
        if (clonedTurtle.getPosition().equals(currentPlayerTurtle.getPosition())) return true;

        if (causesEscapingTheBoard(clonedTurtle.getPosition())) return false;
        if (causesCollision(clonedTurtle.getPosition())) return false;

        return true;
    }

    private MovableTile copyTileContents(MovableTile tile) {
        Position currPosition = new Position(tile.getPosition().getRowNumber(), tile.getPosition().getColNumber());
        MovableTile clonedTile = new Turtle(currPosition, tile.getDirection());
        // Copy directionsFaced and positionVisited Stack into clonedTile
        Stack<Direction> directionsFaced = new Stack<>();
        directionsFaced.addAll(tile.getDirectionsFaced());
        clonedTile.setDirectionsFaced(directionsFaced);

        Stack<Position> positionsVisited = new Stack<>();
        positionsVisited.addAll(tile.getPositionsVisited());
        clonedTile.setPositionsVisited(positionsVisited);;
        return clonedTile;
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
}
