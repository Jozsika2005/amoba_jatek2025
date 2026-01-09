package hu.szakacsjozsef.amoba.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class HighScoreService {

    private static final File FILE = new File("highscore.txt");

    private HighScoreService() {
    }

    // ===== GYŐZELEM RÖGZÍTÉSE =====
    public static void addWin(String name, int totalMoves) throws IOException {
        Map<String, Integer> scores = loadScores();
        scores.put(name, scores.getOrDefault(name, 0) + 1);
        saveScores(scores);
    }

    // ===== SZÉP MEGJELENÍTÉS =====
    public static void show() throws IOException {
        Map<String, Integer> scores = loadScores();

        if (scores.isEmpty()) {
            System.out.println("Nincs még highscore adat.");
            return;
        }

        System.out.println("======================");
        System.out.println("       HIGHSCORE");
        System.out.println("======================");
        System.out.printf(" %-3s %-12s %s%n", "#", "Név", "Győzelem");
        System.out.println("----------------------");

        int index = 1;
        for (var entry : scores.entrySet()
                .stream()
                .sorted(Map.Entry
                        .comparingByValue(Comparator.reverseOrder()))
                .toList()) {

            System.out.printf(" %2d. %-12s %d%n",
                    index++,
                    entry.getKey(),
                    entry.getValue());
        }

        System.out.println("======================");
    }

    // ===== BETÖLTÉS =====
    private static Map<String, Integer> loadScores() throws IOException {
        Map<String, Integer> scores = new HashMap<>();

        if (!FILE.exists()) {
            return scores;
        }

        try (Scanner sc = new Scanner(FILE)) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(";");
                scores.put(parts[0], Integer.parseInt(parts[1]));
            }
        }

        return scores;
    }

    // ===== MENTÉS =====
    private static void saveScores(Map<String, Integer> scores)
            throws IOException {

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (var e : scores.entrySet()) {
                pw.println(e.getKey() + ";" + e.getValue());
            }
        }
    }
}