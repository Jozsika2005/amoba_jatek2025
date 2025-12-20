package com.example.amoba;


import com.example.amoba.model.Game;
import com.example.amoba.model.Player;
import com.example.amoba.persistence.H2ScoreRepository;
import com.example.amoba.persistence.ScoreRepository;
import com.example.amoba.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Scanner;


public class App {
private static final Logger logger = LoggerFactory.getLogger(App.class);
public static void main(String[] args) throws Exception {
int size = 3;
if (args.length>0) {
try { size = Integer.parseInt(args[0]); }
catch(NumberFormatException e){ logger.warn("Invalid size arg, using default 3"); }
}
Scanner sc = new Scanner(System.in);
System.out.println("Amőba játék - méret: " + size + "x" + size);


System.out.print("Játékos1 neve: ");
String n1 = sc.nextLine().trim();
System.out.print("Játékos2 neve: ");
String n2 = sc.nextLine().trim();


Player p1 = new Player(n1.isEmpty() ? "X" : n1, 'X');
Player p2 = new Player(n2.isEmpty() ? "O" : n2, 'O');


Game game = new Game(size, p1, p2);
ScoreRepository repo = new H2ScoreRepository("jdbc:h2:./data/amoeba;AUTO_SERVER=TRUE");
repo.init();


GameService svc = new GameService(game, repo);


while (true) {
System.out.println(game.getBoard());
System.out.println("Következő: " + game.getCurrentPlayer().getName());
System.out.print("Add meg a sort és oszlopot (pl: 0 1), vagy 'exit': ");
String line = sc.nextLine();
if (line.equalsIgnoreCase("exit")) break;
String[] parts = line.trim().split("\\s+");
if (parts.length<2) continue;
try {
int r = Integer.parseInt(parts[0]);
int c = Integer.parseInt(parts[1]);
boolean ok = svc.makeMove(r,c);
if (!ok) System.out.println("Érvénytelen lépés!");
if (game.checkWinner().isPresent()) {
System.out.println("Nyert: " + game.checkWinner().get().getName());
break;
}
if (game.isDraw()) {
System.out.println("Döntetlen!");
break;
}
} catch (NumberFormatException ex) {
System.out.println("Hibás input");
}
}


System.out.println("Top scores:");
repo.topScores(10).forEach(r -> System.out.println(r.get("name") + " -> " + r.get("score")));
repo.close();
}
}
