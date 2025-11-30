package hu.uni.amoeba.controller;


import hu.uni.amoeba.model.Board;
import hu.uni.amoeba.model.Move;
import hu.uni.amoeba.model.Player;
import hu.uni.amoeba.service.GameService;
import hu.uni.amoeba.exception.InvalidMoveException;


import java.util.Scanner;


public class GameController {
private final GameService service = new GameService();


public void startConsoleGame() {
Scanner scanner = new Scanner(System.in);
Board board = new Board();
Player current = Player.X;
System.out.println("Üdv az amőba játékban!");
board.print();


while (true) {
System.out.println("Játékos " + current + ", írd be a sor és oszlop számát (0-2):");
int r = scanner.nextInt();
int c = scanner.nextInt();
try {
Move move = new Move(r, c, current);
service.makeMove(board, move);
board.print();


if (service.hasWon(board, current)) {
System.out.println("Gratulálok! Játékos " + current + " nyert!");
break;
}
if (service.isDraw(board)) {
System.out.println("Döntetlen!");
break;
}


current = current == Player.X ? Player.O : Player.X;
} catch (InvalidMoveException ex) {
System.out.println("Hiba: " + ex.getMessage());
}
}


scanner.close();
}
}
