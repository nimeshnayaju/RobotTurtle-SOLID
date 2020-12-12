package com.robotturtles.model.game;

import com.robotturtles.model.tile.*;

import java.util.*;

import static com.robotturtles.model.game.Board.NUM_OF_ROWS;

public class Game {
    private static final int INITIAL_TURN = 0;
    private static final int PORTAL_ONE_ROW = 1;
    private static final int PORTAL_ONE_COL = 2;
    private static final int PORTAL_TWO_ROW = 4;
    private static final int PORTAL_TWO_COL = 5;
    public static final int NUM_OF_INDIVIDUAL_PORTAL = 2;

    private int numOfPlayers;
    private Board board;
    private int turn;
    private HashMap<Integer, Player> players;
    private GameState gameState;
    private List<StoneWall> stoneWalls;
    private Portal portalPair;
    private List<IceWall> iceWalls;
    private List<Crate> crates;

    public Game(int numOfPlayers) {
        setNumOfPlayers(numOfPlayers);
        this.board = new Board();
        this.turn = INITIAL_TURN;
        this.players = new HashMap<>();
        this.gameState = GameState.IN_PROGRESS;
        this.portalPair = generatePortal();
        this.stoneWalls = generateStoneWalls();
        this.iceWalls = this.generateIceWalls();
        this.crates = generateCrates();
    }

    private List<Crate> generateCrates() {
        List<Crate> crates = new ArrayList<>(numOfPlayers);
        for (int i = 0; i < numOfPlayers; i++) {
            Position uniquePosition = generateUniquePosition();
            Crate newCrate = new Crate(uniquePosition);
            crates.add(newCrate);
            board.setUpTile(newCrate);
        }
        return crates;
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
        this.board.setUpTile(portalOne);
        this.board.setUpTile(portalTwo);
        return portalTwo;
    }

    private List<StoneWall> generateStoneWalls(){
        List<StoneWall> stoneWalls = new ArrayList<>(numOfPlayers);
        for (int i = 0; i < numOfPlayers; i++) {
            Position uniquePosition = generateUniquePosition();
            StoneWall newStoneWall = new StoneWall(uniquePosition);
            stoneWalls.add(newStoneWall);
            board.setUpTile(newStoneWall);
        }
        return stoneWalls;
    }

    private List<IceWall> generateIceWalls(){
        List<IceWall> iceWalls = new ArrayList<>(numOfPlayers);
        for (int i = 0; i < numOfPlayers; i++) {
            Position uniquePosition = generateUniquePosition();
            IceWall newIceWall = new IceWall(uniquePosition);
            iceWalls.add(newIceWall);
            board.setUpTile(newIceWall);
        }
        return iceWalls;
    }

    private Position generateUniquePosition() {
        Position uniquePosition;
        Random rand = new Random();
        do {
            int row = rand.nextInt(NUM_OF_ROWS-1)+1;
            int col = rand.nextInt(NUM_OF_ROWS-1)+1;
            uniquePosition = new Position(row, col);
        } while (board.isOccupied(uniquePosition));

        return uniquePosition;
    }

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
        this.board.setUpTile(newPlayer.getTurtle());
        this.board.setUpTile(newPlayer.getJewel());
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public void setGameState(GameState state){
        gameState  = state;
    }

    public Board getBoard() {
        return this.board;
    }

    public int getTurn() {
        return this.turn;
    }

    public String getCurrentPlayerName() {
        return getCurrentPlayer().getPlayerName();
    }

    public Player getCurrentPlayer() {
        return this.players.get(turn);
    }

    public HashMap<Integer, Player> getPlayers() {
        return players;
    }

    public Portal getPortalPair() {
        return portalPair;
    }

    public List<Crate> getCrates() {
        return crates;
    }

    public List<IceWall> getIceWalls() {
        return iceWalls;
    }

    public List<StoneWall> getStoneWalls() {
        return stoneWalls;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
