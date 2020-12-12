package com.robotturtles.model.game;

import com.robotturtles.model.card.Card;
import com.robotturtles.model.card.Forward;
import com.robotturtles.model.tile.*;

import java.util.Stack;

import static com.robotturtles.model.game.Board.NUM_OF_ROWS;

public class GameManipulate {
    private Game game;

    public GameManipulate(Game game) {
        this.game = game;
    }

    public boolean isValidCard(Card card) {
        if (card == null) return false;

        Turtle currentPlayerTurtle = game.getCurrentPlayer().getTurtle();

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
                if (causesEscapingTheBoard(cratePotentialPosition) || game.getBoard().isOccupied(cratePotentialPosition)) return false;
            }
            // Check if there is a collision with another tile (except own jewel, Portal or Crate)
            if (causesCollision(clonedTurtle.getPosition())) return false;
        } else {
            IceWall iceWall = getIceWall(currentPlayerTurtle.getPosition(), currentPlayerTurtle.getDirection());
            return iceWall != null;
        }
        return true;
    }

    private boolean causesEscapingTheBoard(Position position) {
        if (position.getRowNumber() >= NUM_OF_ROWS || position.getRowNumber() < 0 || position.getColNumber() >= Board.NUM_OF_COLS || position.getColNumber() <0) {
            return true;
        }
        return false;
    }


    private boolean causesCollision(Position position) {
        if (game.getBoard().isOccupied(position) && !isJewel(position) && !isPortal(position) && !isCrate(position)) {
            return true;
        }
        return false;
    }

    private Position determineCratePotentialPosition(Position position, Direction direction) {
        return Forward.determinePosition(direction, position);
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

    private boolean isJewel(Position position) {
        if (position.equals(game.getCurrentPlayer().getJewel().getPosition())) return true;
        return false;
    }

    private boolean isCrate(Position position) {
        for (Crate crate : game.getCrates()) {
            if (position.equals(crate.getPosition())) return true;
        }
        return false;
    }

    private boolean isPortal(Position position) {
        if (position.equals(game.getPortalPair().getPosition()) || position.equals(game.getPortalPair().getOtherPair().getPosition())) return true;
        return false;
    }

    public void makeMove(Card card) {
        Player currentPlayer = game.getCurrentPlayer();
        Position oldPosition = currentPlayer.getTurtle().getPosition();
        Direction oldDirection = currentPlayer.getTurtle().getDirection();

        if (!card.isLaserCard()) {
            card.play(game.getCurrentPlayer().getTurtle());

            Position destinationPosition = currentPlayer.getTurtle().getPosition();

            if (isPortal(destinationPosition) && !oldPosition.equals(destinationPosition)) {
                // If the turtle lands in one of the pairs of a Portal tile, teleport it to the position of the other tile
                destinationPosition = getOtherPortalTile(destinationPosition);
            }
            game.getBoard().makeMove(oldPosition, destinationPosition, crateIfAny(destinationPosition), currentPlayer.getTurtle());
        } else {
            IceWall iceWall = getIceWall(oldPosition, oldDirection);
            game.getBoard().setPositionNull(iceWall.getPosition());
            card.play(iceWall);
        }
    }

    private IceWall getIceWall(Position turtlePosition, Direction turtleDirection) {
        Position iceWallPosition = Forward.determinePosition(turtleDirection, turtlePosition);
        for (IceWall iceWall : game.getIceWalls()) {
            if (iceWallPosition.equals(iceWall.getPosition())) return iceWall;
        }
        return null;
    }

    private Crate crateIfAny(Position position) {
        for (Crate crate : game.getCrates()) {
            if (position.equals(crate.getPosition())) return crate;
        }
        return null;
    }

    private Position getOtherPortalTile(Position position) {
        if (position.equals(game.getPortalPair().getPosition())) {
            return game.getPortalPair().getOtherPair().getPosition();
        } else if (position.equals(game.getPortalPair().getOtherPair().getPosition())) {
            return game.getPortalPair().getPosition();
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
        Position turtlePosition = game.getCurrentPlayer().getTurtle().getPosition();
        Position jewelPosition = game.getCurrentPlayer().getJewel().getPosition();
        if (turtlePosition.equals(jewelPosition)) {
            game.getPlayers().remove(game.getTurn());
            return true;
        }
        return false;
    }

    /**
     * Checks if the executing the move results in the all players completing the game
     * @return true if the move results in the all players completing the game; false otherwise
     */
    public boolean checkForGameCompletion() {
        if (game.getPlayers().isEmpty()) {
            game.setGameState(GameState.COMPLETED);
            return true;
        }
        return false;
    }

    public void assignTurnToNextPlayer() {
        int turn = game.getTurn();
        turn = (turn + 1) % game.getNumOfPlayers();
        while (game.getPlayers().get(turn) == null) {
            turn = (turn + 1) % game.getNumOfPlayers();
        }
        game.setTurn(turn);
    }
}
