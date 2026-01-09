package hu.szakacsjozsef.amoba.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import hu.szakacsjozsef.amoba.model.Game;
import hu.szakacsjozsef.amoba.model.Move;
import hu.szakacsjozsef.amoba.model.Player;

public final class GameXmlService {

    private static final File FILE = new File("save.xml");

    private GameXmlService() {
    }

    // ===== VAN-E MENTÉS =====
    public static boolean hasSave() {
        return FILE.exists();
    }

    // ===== MENTÉS XML =====
    public static void save(Game game) throws IOException {
        try (PrintWriter pw = new PrintWriter(FILE)) {

            pw.println("<game>");
            pw.println("  <size>" + game.getBoard().getSize() + "</size>");
            pw.println("  <current>"
                    + game.getCurrentPlayer().getSymbol()
                    + "</current>");
            pw.println("  <board>");

            char[][] grid = game.getBoard().getGridCopy();
            for (char[] row : grid) {
                pw.println("    <row>" + new String(row) + "</row>");
            }

            pw.println("  </board>");
            pw.println("</game>");
        }
    }

    // ===== BETÖLTÉS XML =====
    public static Game load(Player ember, Player gep)
            throws IOException {

        try (Scanner sc = new Scanner(FILE)) {

            int size = 0;
            char current = 'X';
            String[] rows = null;

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();

                if (line.startsWith("<size>")) {
                    size = Integer.parseInt(
                            line.replace("<size>", "")
                                    .replace("</size>", ""));
                }

                if (line.startsWith("<current>")) {
                    current = line
                            .replace("<current>", "")
                            .replace("</current>", "")
                            .charAt(0);
                }

                if (line.startsWith("<row>")) {
                    if (rows == null) {
                        rows = new String[size];
                    }
                    for (int i = 0; i < size; i++) {
                        rows[i] = line
                                .replace("<row>", "")
                                .replace("</row>", "");
                        if (i < size - 1) {
                            line = sc.nextLine().trim();
                        }
                    }
                }
            }

            Game game = new Game(ember, gep, size);

            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    char ch = rows[r].charAt(c);
                    if (ch == 'X') {
                        game.placeInitial(new Move(r, c, ember));
                    } else if (ch == 'O') {
                        game.placeInitial(new Move(r, c, gep));
                    }
                }
            }

            if (current == gep.getSymbol()) {
                game.forceGepTurn();
            }

            return game;
        }
    }

    // ===== TÖRLÉS =====
    public static void delete() {
        if (FILE.exists()) {
            FILE.delete();
        }
    }
}