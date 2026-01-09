package hu.szakacsjozsef.amoba.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GepTest {

    @Test
    void lep_returnsValidMoveInsideBoard() {
        Board board = new Board(3);
        Player gep = new Player("Gép", 'O');
        Gep ai = new Gep();

        Move move = ai.lep(board, gep);

        assertNotNull(move);
        assertTrue(move.getRow() >= 0 && move.getRow() < 3);
        assertTrue(move.getCol() >= 0 && move.getCol() < 3);
    }

    @Test
    void gepMove_isAlwaysOnEmptyCell() {
        Board board = new Board(3);
        Player gep = new Player("Gép", 'O');
        Gep ai = new Gep();

        Move move = ai.lep(board, gep);

        assertTrue(board.place(move));
    }
}