import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class HighScoreService {

    private static final String SCORE_FILE = "src/main/resources/highscore.txt";

    public void addScore(String playerName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE, true))) {
            writer.write(playerName + " - " + new Date());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Nem sikerült menteni a pontot: " + e.getMessage());
        }
    }

    public List<String> loadScores() {
        try {
            return Files.readAllLines(Paths.get(SCORE_FILE));
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void printScores() {
        List<String> scores = loadScores();
        System.out.println("\n=== High Score Tábla ===");
        if (scores.isEmpty()) {
            System.out.println("Még nincsenek eredmények.");
        } else {
            for (String s : scores) {
                System.out.println(s);
            }
        }
        System.out.println("========================\n");
    }
}
