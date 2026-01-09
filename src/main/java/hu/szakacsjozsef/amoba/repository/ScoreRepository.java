package hu.szakacsjozsef.amoba.repository;

import java.util.HashMap;
import java.util.Map;

public class ScoreRepository {

    private final Map<String, Integer> scores = new HashMap<>();

    public void addWin(String playerName) {
        scores.put(playerName, scores.getOrDefault(playerName, 0) + 1);
    }

    public Map<String, Integer> getScores() {
        return new HashMap<>(scores);
    }
}
