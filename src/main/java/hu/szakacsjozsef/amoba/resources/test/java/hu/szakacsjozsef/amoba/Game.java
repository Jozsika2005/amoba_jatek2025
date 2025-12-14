
package com.example.amoba.model;


import java.util.Optional;


public class Game {
private final Board board;
private final Player p1;
private final Player p2;
private Player current;


public Game(int size, Player p1, Player p2) {
this.board = new Board(size);
this.p1 = p1; this.p2 = p2;
this.current = p1;
}


public Game(Board board, Player p1, Player p2) {
this.board = board;
this.p1 = p1; this.p2 = p2;
this.current = p1;
}


public boolean makeMove(int row, int col) {
Move m = new Move(row, col, current);
boolean ok = board.place(m);
if (!ok) return false;
// switch player
current = (current == p1) ? p2 : p1;
return true;
}


public Optional<Player> checkWinner() { return board.checkWinner(p1, p2); }
public boolean isDraw() { return board.isFull() && checkWinner().isEmpty(); }
public Board getBoard() { return board; }
public Player getCurrentPlayer() { return current; }
public Player getP1() { return p1; }
public Player getP2() { return p2; }
}
