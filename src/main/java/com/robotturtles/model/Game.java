package com.robotturtles.model;

import javax.management.loading.ClassLoaderRepository;
import java.util.*;

import static com.robotturtles.model.Board.NUM_OF_ROWS;

public class Game {
    private static final int INITIAL_TURN = 0;
    private static final int PORTAL_ONE_ROW = 1;
    private static final int PORTAL_ONE_COL = 2;
    private static final int PORTAL_TWO_ROW = 4;
    private static final int PORTAL_TWO_COL = 5;
    private static final int NUM_OF_INDIVIDUAL_PORTAL = 2;

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

    public List<StoneWall> getStoneWalls() {
        return stoneWalls;
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

    public List<IceWall> getIceWallLst() {
        return iceWalls;
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
        for (IceWall iceWall: iceWalls) {
            if (iceWall.getPosition() != null) {
                info.add(new TileInfo(iceWall.getPosition()));
            }
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

        // If the card is not a Laser card
        if (!card.isLaserCard()) {
            Turtle clonedTurtle = copyTileContents(currentPlayerTurtle);
            card.play(clonedTurtle);
            // If the turtle position doesn't change, the card is valid
            if (clonedTurtle.getPosition().equals(currentPlayerTurtle.getPosition())) return true;

            if (causesEscapingTheBoard(clonedTurtle.getPosition())) return false;
            // Check if the turtle pushes the crate (if any) in front out of the board
            if (isCrate(clonedTurtle.getPosition())) {
                Position cratePotentialPosition = determineCratePotentialPosition(clonedTurtle.getPosition(), clonedTurtle.getDirection());
                if (causesEscapingTheBoard(cratePotentialPosition) || board.isOccupied(cratePotentialPosition)) return false;
            }
            // Check if there is a collision with another tile (except own jewel, Portal or Crate)
            if (causesCollision(clonedTurtle.getPosition())) return false;
        } else {
            IceWall iceWall = getIceWall(currentPlayerTurtle.getPosition(), currentPlayerTurtle.getDirection());
            return iceWall != null;
        }
        return true;
    }

    private Position determineCratePotentialPosition(Position position, Direction direction) {
        return ForwardMove.determinePosition(direction, position);
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
        if (this.board.isOccupied(position) && !isJewel(position) && !isPortal(position) && !isCrate(position)) {
            return true;
        }
        return false;
    }

    private boolean isJewel(Position position) {
        if (position.equals(getCurrentPlayer().getJewel().getPosition())) return true;
        return false;
    }

    private boolean isCrate(Position position) {
        for (Crate crate : crates) {
            if (position.equals(crate.getPosition())) return true;
        }
        return false;
    }

    private boolean isPortal(Position position) {
        if (position.equals(portalPair.getPosition()) || position.equals(portalPair.getOtherPair().getPosition())) return true;
        return false;
    }

    private boolean causesEscapingTheBoard(Position position) {
        if (position.getRowNumber() >= NUM_OF_ROWS || position.getRowNumber() < 0 || position.getColNumber() >= Board.NUM_OF_COLS || position.getColNumber() <0) {
            return true;
        }
        return false;
    }

    public void makeMove(Card card) {
        Player currentPlayer = getCurrentPlayer();
        Position oldPosition = currentPlayer.getTurtle().getPosition();
        Direction oldDirection = currentPlayer.getTurtle().getDirection();

        if (!card.isLaserCard()) {
            card.play(getCurrentPlayer().getTurtle());

            Position destinationPosition = currentPlayer.getTurtle().getPosition();

            if (isPortal(destinationPosition) && !oldPosition.equals(destinationPosition)) {
                // If the turtle lands in one of the pairs of a Portal tile, teleport it to the position of the other tile
                destinationPosition = getOtherPortalTile(destinationPosition);
            }
            board.makeMove(oldPosition, destinationPosition, crateIfAny(destinationPosition), currentPlayer.getTurtle());
        } else {
            IceWall iceWall = getIceWall(oldPosition, oldDirection);
            board.setPositionNull(iceWall.getPosition());
            card.play(iceWall);
        }
    }

    private IceWall getIceWall(Position turtlePosition, Direction turtleDirection) {
        Position iceWallPosition = ForwardMove.determinePosition(turtleDirection, turtlePosition);
        for (IceWall iceWall : iceWalls) {
            if (iceWallPosition.equals(iceWall.getPosition())) return iceWall;
        }
        return null;
    }

    private Crate crateIfAny(Position position) {
        for (Crate crate : crates) {
            if (position.equals(crate.getPosition())) return crate;
        }
        return null;
    }

    private Position getOtherPortalTile(Position position) {
        if (position.equals(portalPair.getPosition())) {
            return portalPair.getOtherPair().getPosition();
        } else if (position.equals(portalPair.getOtherPair().getPosition())) {
            return portalPair.getPosition();
        }
        return null;
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
        ArrayList<TileInfo> info = new ArrayList<>(numOfPlayers);
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

    public ArrayList<TileInfo> getAllCrateInfo() {
        ArrayList<TileInfo> info = new ArrayList<>(numOfPlayers);
        for (Crate crate : crates) {
            Position position = crate.getPosition();
            info.add(new TileInfo(position));
        }
        return info;
    }
}
