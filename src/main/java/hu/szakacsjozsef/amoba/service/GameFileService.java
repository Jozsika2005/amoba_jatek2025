package hu.szakacsjozsef.amoba.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import hu.szakacsjozsef.amoba.model.Game;
import hu.szakacsjozsef.amoba.model.Move;
import hu.szakacsjozsef.amoba.model.Player;

public final class GameFileService {

    private static final File SAVE_FILE = new File("save.txt");
    private static final File HISTORY_FILE = new File("history.txt");

    private GameFileService() {
    }

    // ===== FÉLBEHAGYOTT JÁTÉK =====
    public static boolean hasSavedGame() {
        return SAVE_FILE.exists();
    }

    public static void save(Game game) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SAVE_FILE))) {
            pw.println(game.getBoard().getSize());
            pw.println(game.getCurrentPlayer().getSymbol());

            char[][] grid = game.getBoard().getGridCopy();
            for (char[] row : grid) {
                pw.println(new String(row));
            }
        }
    }

    public static Game load(Player ember, Player gep) throws IOException {
        try (Scanner sc = new Scanner(SAVE_FILE)) {
            int size = Integer.parseInt(sc.nextLine());
            char current = sc.nextLine().charAt(0);

            Game game = new Game(ember, gep, size);

            for (int r = 0; r < size; r++) {
                String line = sc.nextLine();
                for (int c = 0; c < size; c++) {
                    char ch = line.charAt(c);
                    if (ch == 'X') {
                        game.placeInitial(new Move(r, c, ember));
                    } else if (ch == 'O') {
                        game.placeInitial(new Move(r, c, gep));
                    }
                }
            }

            // aktuális játékos visszaállítása
            if (current == gep.getSymbol()) {
                game.makeMove(new Move(0, 0, ember)); // CSAK váltás
            }

            return game;
        }
    }

    public static void deleteSave() {
        if (SAVE_FILE.exists()) {
            SAVE_FILE.delete();
        }
    }

    // ===== ÚJ JÁTÉK KEZDŐ ÁLLAPOT =====
    public static void placeInitialForNewGame(Game game) {
        int size = game.getBoard().getSize();
        int center = size / 2;

        // középre egy X (ember)
        game.placeInitial(new Move(center, center, game.getEmber()));
    }

    // ===== BEFEJEZETT MECCSEK =====
    public static void saveFinished(Game game) throws IOException {
        try (PrintWriter pw =
                     new PrintWriter(new FileWriter(HISTORY_FILE, true))) {

            pw.println("=== MECCS ===");
            pw.println(game.getBoard());
            pw.println("Győztes: "
                    + game.getWinner()
                    .map(Player::getName)
                    .orElse("Döntetlen"));
            pw.println();
        }
    }

    public static void showHistory() throws IOException {
        if (!HISTORY_FILE.exists()) {
            System.out.println("Nincs korábbi meccs.");
            return;
        }

        try (Scanner sc = new Scanner(HISTORY_FILE)) {
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        }
    }
}