package com.robotturtles.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Game {
    private static final int INITIAL_TURN = 0;
    private static final int PORTAL_ONE_ROW = 1;
    private static final int PORTAL_ONE_COL = 2;
    private static final int PORTAL_TWO_ROW = 4;
    private static final int PORTAL_TWO_COL = 5;
    private static final int NUM_OF_STONEWALLS = 2;
    private static final int NUM_OF_INDIVIDUAL_PORTAL = 2;

    private int numOfPlayers;
    private Board board;
    private int turn;
    private HashMap<Integer, Player> players;
    private GameState gameState;
    private ArrayList<StoneWall> stoneWalls;
    private Portal portalPair;
    private ArrayList<IceWall> iceWallLst;

    public Game(int numOfPlayers) {
        setNumOfPlayers(numOfPlayers);
        this.board = new Board();
        this.turn = INITIAL_TURN;
        this.players = new HashMap<>();
        this.gameState = GameState.IN_PROGRESS;
        this.stoneWalls = generateAllStoneWall();
        this.portalPair = generatePortal();
    }

    /**
     * Generate a Portal Tile
     */
    private Portal generatePortal() {
        Position portalOnePosition = new Position(PORTAL_ONE_ROW, PORTAL_ONE_COL);
        Position portalTwoPosition = new Position(PORTAL_TWO_ROW, PORTAL_TWO_COL);

        Portal portalOne = new Portal(portalOnePosition);
        Portal portalTwo = new Portal(portalTwoPosition, portalOne);
        portalOne.setOtherPair(portalTwo);
        this.board.setUpPortal(portalTwo);
        return portalTwo;
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

    public ArrayList<StoneWall> getStoneWalls() {
        return stoneWalls;
    }

    private ArrayList<StoneWall> generateAllStoneWall(){
        ArrayList<StoneWall> stonewalls = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; i++) {
            StoneWall stonewall = new StoneWall();
            stonewalls.add(stonewall);
            this.board.setUpTile(stonewall);
        }
        return stonewalls;
    }

    public ArrayList<IceWall> getIceWallLst() {
        return iceWallLst;
    }

    private ArrayList<IceWall> generateAllIceWall(){
        ArrayList<IceWall> icewalls = new ArrayList<IceWall>();
        for (int i = 0; i < numOfPlayers; i++) {
            IceWall stonewall = new IceWall(new Position(INITIAL_POSITION,INITIAL_POSITION));
            icewalls.add((stonewall));
        }
        return icewalls;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void addPlayer(String playerName, int playerId) {
        Player newPlayer = new Player(playerName, playerId);
        this.players.put(playerId, newPlayer);
        setUpPlayerTurtle(newPlayer);
        setUpPlayerJewel(newPlayer);
    }

    public void setUpPlayerTurtle(Player player) {
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

    public ArrayList<TileInfo> getAllJewelInfo() {
        ArrayList<TileInfo> info = new ArrayList<>(numOfPlayers);
        for (Player player: players.values()) {
            Position position = player.getJewel().getPosition();
            boolean active = player.getPlayerId() == getTurn();
            info.add(new TileInfo(position, null, active));
        }
        return info;
    }

    public ArrayList<TileInfo> getAllIceWallInfo() {
        ArrayList<TileInfo> info = new ArrayList<>(numOfPlayers);
        for (IceWall iceWall: iceWallLst) {
            Position position = iceWall.getPosition();
            boolean active = true;
            info.add(new TileInfo(position, null, active));
        }
        return info;
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
        if (card == null) return false;

        Turtle currentPlayerTurtle = getCurrentPlayer().getTurtle();
        Turtle clonedTurtle = copyTileContents(currentPlayerTurtle);

        card.play(clonedTurtle);
        // If the turtle position doesn't change, the card is valid
        if (clonedTurtle.getPosition().equals(currentPlayerTurtle.getPosition())) return true;

        if (causesEscapingTheBoard(clonedTurtle.getPosition())) return false;
        if (causesCollision(clonedTurtle.getPosition())) return false;
        if (causesCollisionIceWall(clonedTurtle.getPosition())) return false;

        return true;
    }

    private Turtle copyTileContents(Turtle tile) {
        Position currPosition = new Position(tile.getPosition().getRowNumber(), tile.getPosition().getColNumber());
        Turtle clonedTile = new Turtle(currPosition, tile.getDirection());
        // Copy directionsFaced and positionVisited Stack into clonedTile
        Stack<Direction> directionsFaced = new Stack<>();
        directionsFaced.addAll(tile.getDirectionsFaced());
        clonedTile.setDirectionsFaced(directionsFaced);

        Stack<Position> positionsVisited = new Stack<>();
        positionsVisited.addAll(tile.getPositionsVisited());
        clonedTile.setPositionsVisited(positionsVisited);
        return clonedTile;
    }

    private boolean causesCollision(Position position) {
        if (this.board.isOccupied(position) && !position.equals(getCurrentPlayer().getJewel().getPosition())) {
            return true;
        }
        return false;
    }


    private boolean causesCollisionIceWall(Position position) {
        if ( !collisionWithAnyIceWall(position) ) {
            return true;
        }
        return false;
    }

    private boolean collisionWithAnyIceWall(Position position){
        for (IceWall icewall: getIceWallLst()) {
            if(position.equals(icewall.getPosition())){
                return false;
            }
        }
        return true;
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


    public ArrayList<TileInfo> getAllStoneWallInfo() {
        ArrayList<TileInfo> info = new ArrayList<>(NUM_OF_STONEWALLS);
        for (StoneWall stoneWall: stoneWalls) {
            Position position = stoneWall.getPosition();
            info.add(new TileInfo(position));
        }
        return info;
    }

    public ArrayList<TileInfo> getAllPortalInfo() {
        ArrayList<TileInfo> info = new ArrayList<>(NUM_OF_INDIVIDUAL_PORTAL);
        info.add(new TileInfo(portalPair.getPosition()));
        info.add(new TileInfo(portalPair.getOtherPair().getPosition()));
        return info;
    }
}
