package hu.uni.amoeba.service;


import hu.uni.amoeba.model.Board;
import hu.uni.amoeba.model.Move;
import hu.uni.amoeba.model.Player;
import hu.uni.amoeba.exception.InvalidMoveException;


public class GameService {


public void makeMove(Board board, Move move) throws InvalidMoveException {
int r = move.row();
int c = move.col();
if (r < 0 || r > 2 || c < 0 || c > 2) {
throw new InvalidMoveException("Koordináták kívül esnek a táblán: " + r + "," + c);
}
if (board.get(r, c) != null) {
throw new InvalidMoveException("Ez a mező már foglalt: " + r + "," + c);
}
board.set(r, c, move.player());
}


public boolean hasWon(Board b, Player p) {
for (int i = 0; i < 3; i++) {
if (b.get(i,0) == p && b.get(i,1) == p && b.get(i,2) == p) return true;
if (b.get(0,i) == p && b.get(1,i) == p && b.get(2,i) == p) return true;
}
if (b.get(0,0) == p && b.get(1,1) == p && b.get(2,2) == p) return true;
if (b.get(0,2) == p && b.get(1,1) == p && b.get(2,0) == p) return true;
return false;
}


public boolean isDraw(Board b) {
return b.isFull() && !hasWon(b, Player.X) && !hasWon(b, Player.O);
}
}
