package hu.szakacsjozsef.amoba.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ScoreRepositoryTest {

    @Test
    void addWin_increasesScore() {
        ScoreRepository repo = new ScoreRepository();

        repo.addWin("Jozsi");
        repo.addWin("Jozsi");

        assertEquals(2, repo.getScores().get("Jozsi"));
    }

    @Test
    void getScores_returnsCopy() {
        ScoreRepository repo = new ScoreRepository();
        repo.addWin("A");

        assertEquals(1, repo.getScores().size());
    }
}