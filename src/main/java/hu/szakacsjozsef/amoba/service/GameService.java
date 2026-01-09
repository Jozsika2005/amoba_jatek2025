package hu.szakacsjozsef.amoba.service;

import java.util.Optional;

import hu.szakacsjozsef.amoba.model.Game;
import hu.szakacsjozsef.amoba.model.Move;
import hu.szakacsjozsef.amoba.model.Player;
import hu.szakacsjozsef.amoba.repository.ScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameService {

    private static final Logger logger =
            LoggerFactory.getLogger(GameService.class);

    private final Game game;
    private final ScoreRepository scoreRepository;

    public GameService(Game game, ScoreRepository scoreRepository) {
        this.game = game;
        this.scoreRepository = scoreRepository;
    }

    public void makeMove(int row, int col) {
        logger.info("Player {} moves to ({},{})",
                game.getCurrentPlayer().getName(), row, col);

        try {
            Move move = new Move(row, col, game.getCurrentPlayer());
            game.makeMove(move);
        } catch (RuntimeException e) {
            logger.warn("Invalid move: {}", e.getMessage());
            throw e;
        }

        Optional<Player> winner = game.getWinner();
        if (winner.isPresent()) {
            logger.info("Winner: {}", winner.get().getName());
            scoreRepository.addWin(winner.get().getName());
        }
    }

    public Game getGame() {
        return game;
    }
}
