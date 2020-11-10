package com.robotturtles.model;
import java.util.Stack;

public class GameOperation {
    private Game game;

    //-- Constructor --//

    public GameOperation(Game game){
        this.game = game;
    }

    //-- Operation --//

    /**
     * Assigns turn to the next player
     */
    public void assignTurnToNextPlayer() {
        game.setTurn((game.getTurn() + 1) % game.getNumOfPlayers());
        while (game.getSetPlayers().get(game.getTurn()) == null) {
            game.setTurn((game.getTurn() + 1) % game.getNumOfPlayers());
        }
    }

    public void makeMove(Card card) {
        Player currentPlayer = game.getCurrentPlayer();
        Position oldPosition = currentPlayer.getTurtle().getPosition();
        card.play(game.getCurrentPlayer().getTurtle());
        game.getBoard().makeMove(oldPosition, currentPlayer.getTurtle().getPosition(), currentPlayer.getTurtle());
    }

    public boolean isValidCard(Card card) {
        if (card == null) return false;

        MovableTile currentPlayerTurtle = this.game.getCurrentPlayer().getTurtle();
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
        if (game.getBoard().isOccupied(position) && !position.equals(game.getCurrentPlayer().getJewel().getPosition())) {
            return true;
        }
        return false;
    }

    private boolean causesEscapingTheBoard(Position position) {
        if (position.getRowNumber() >= game.getBoard().NUM_OF_ROWS || position.getRowNumber() < 0 || position.getColNumber() >= game.getBoard().NUM_OF_COLS || position.getColNumber() <0) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the executing the move results in the player successfully completing the game
     * @return true if the move results in the player completing the game; false otherwise
     */
    public boolean checkForPlayerWin() {
        // Check if the executing the move results in the player successfully completing the game
        // Set the GameState to Completed if all players have finished the game
        Position turtlePosition = game.getCurrentPlayer().getTurtle().getPosition();
        Position jewelPosition = game.getCurrentPlayer().getJewel().getPosition();
        if (turtlePosition.equals(jewelPosition)) {
            game.getSetPlayers().remove(game.getTurn());
            return true;
        }
        return false;
    }

    /**
     * Checks if the executing the move results in the all players completing the game
     * @return true if the move results in the all players completing the game; false otherwise
     */
    public boolean checkForGameCompletion() {
        if (game.getSetPlayers().isEmpty()) {
            game.setGameState(GameState.COMPLETED);
            return true;
        }
        return false;
    }
}
