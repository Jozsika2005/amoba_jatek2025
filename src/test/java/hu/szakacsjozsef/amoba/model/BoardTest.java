package hu.szakacsjozsef.amoba.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void rowWinDetected() {
        Board board = new Board(3);
        Player p1 = new Player("A", 'X');
        Player p2 = new Player("B", 'O');

        board.place(new Move(0, 0, p1));
        board.place(new Move(0, 1, p1));
        board.place(new Move(0, 2, p1));

        assertTrue(board.checkWinner(p1, p2).isPresent());
    }

    @Test
    void columnWinDetected() {
        Board board = new Board(3);
        Player p1 = new Player("A", 'X');
        Player p2 = new Player("B", 'O');

        board.place(new Move(0, 0, p2));
        board.place(new Move(1, 0, p2));
        board.place(new Move(2, 0, p2));

        assertTrue(board.checkWinner(p1, p2).isPresent());
    }

    @Test
    void diagonalWinDetected() {
        Board board = new Board(3);
        Player p1 = new Player("A", 'X');
        Player p2 = new Player("B", 'O');

        board.place(new Move(0, 0, p1));
        board.place(new Move(1, 1, p1));
        board.place(new Move(2, 2, p1));

        assertTrue(board.checkWinner(p1, p2).isPresent());
    }

    @Test
    void fullBoardDetected() {
        Board board = new Board(2);
        Player p = new Player("A", 'X');

        board.place(new Move(0, 0, p));
        board.place(new Move(0, 1, p));
        board.place(new Move(1, 0, p));
        board.place(new Move(1, 1, p));

        assertTrue(board.isFull());
    }
}