package hu.szakacsjozsef.amoba.model;

import static org.junit.jupiter.api.Assertions.*;

import hu.szakacsjozsef.amoba.exception.InvalidMoveException;
import org.junit.jupiter.api.Test;

class GameTest {


    @Test
    void newGame_isNotFinished() {
        Player p1 = new Player("A", 'X');
        Player p2 = new Player("B", 'O');

        Game game = new Game(p1, p2, 3);

        assertFalse(game.isFinished());
        assertEquals(p1, game.getCurrentPlayer());
    }

    @Test
    void makeMove_switchesPlayer() {
        Player p1 = new Player("A", 'X');
        Player p2 = new Player("B", 'O');

        Game game = new Game(p1, p2, 3);

        game.makeMove(new Move(0, 0, p1));

        assertEquals(p2, game.getCurrentPlayer());
    }

    @Test
    void makeMove_invalidMove_throwsException() {
        Player p1 = new Player("A", 'X');
        Player p2 = new Player("B", 'O');

        Game game = new Game(p1, p2, 3);

        game.makeMove(new Move(0, 0, p1));

        assertThrows(
                InvalidMoveException.class,
                () -> game.makeMove(new Move(0, 0, p2))
        );
    }

    @Test
    void gameDetectsWinner() {
        Player p1 = new Player("A", 'X');
        Player p2 = new Player("B", 'O');

        Game game = new Game(p1, p2, 3);

        game.makeMove(new Move(0, 0, p1));
        game.makeMove(new Move(1, 0, p2));
        game.makeMove(new Move(0, 1, p1));
        game.makeMove(new Move(1, 1, p2));
        game.makeMove(new Move(0, 2, p1)); // WIN

        assertTrue(game.isFinished());
        assertTrue(Game.getWinner().isPresent());
        assertEquals(p1, Game.getWinner().get());
    }

    @Test
    void finishedGame_rejectsFurtherMoves() {
        Player p1 = new Player("A", 'X');
        Player p2 = new Player("B", 'O');

        Game game = new Game(p1, p2, 3);

        game.makeMove(new Move(0, 0, p1));
        game.makeMove(new Move(1, 0, p2));
        game.makeMove(new Move(0, 1, p1));
        game.makeMove(new Move(1, 1, p2));
        game.makeMove(new Move(0, 2, p1)); // WIN

        assertThrows(
                InvalidMoveException.class,
                () -> game.makeMove(new Move(2, 2, p2))
        );
    }

}