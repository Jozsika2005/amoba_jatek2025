package com.example.amoba.service;


import com.example.amoba.model.Game;
import com.example.amoba.model.Player;
import com.example.amoba.persistence.ScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Optional;


public class GameService {
private static final Logger logger = LoggerFactory.getLogger(GameService.class);
private final ScoreRepository scoreRepo;
private final Game game;


public GameService(Game game, ScoreRepository scoreRepo) {
this.game = game;
this.scoreRepo = scoreRepo;
}


public boolean makeMove(int r, int c) {
boolean ok = game.makeMove(r,c);
logger.info("Move at ({},{}) accepted? {}", r,c,ok);
if (ok) {
Optional<Player> winner = game.checkWinner();
if (winner.isPresent()) {
try {
// Nyertes esetén 1 pont alap (összeállítható)
scoreRepo.saveScore(winner.get().getName(), 1);
} catch (Exception e) {
logger.error("Failed to save score", e);
}
}
}
return ok;
}


public Game getGame(){ return game; }
}
