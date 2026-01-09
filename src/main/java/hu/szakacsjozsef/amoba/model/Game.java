package hu.szakacsjozsef.amoba.model;

import java.util.Optional;

import hu.szakacsjozsef.amoba.exception.InvalidMoveException;

public class Game {

    private int totalMoves;
    private int emberMoves;
    private int gepMoves;

    private final Board board;
    private final Player ember;
    private final Player gep;

    private Player currentPlayer;
    private static Optional<Player> winner = Optional.empty();
    private boolean finished;

    public Game(Player ember, Player gep, int size) {
        this.ember = ember;
        this.gep = gep;
        this.currentPlayer = ember;
        this.board = new Board(size);
        this.winner = Optional.empty();
        this.finished = false;

        this.totalMoves = 0;
        this.emberMoves = 0;
        this.gepMoves = 0;
    }

    public void makeMove(Move move) {
        if (finished) {
            throw new InvalidMoveException("A játék már véget ért.");
        }

        boolean success = board.place(move);
        if (!success) {
            throw new InvalidMoveException("Érvénytelen lépés.");
        }

        // ===== LÉPÉSSZÁMLÁLÁS =====
        totalMoves++;
        if (move.getPlayer() == ember) {
            emberMoves++;
        } else {
            gepMoves++;
        }

        winner = board.checkWinner(ember, gep);

        if (winner.isPresent() || board.isFull()) {
            finished = true;
            return;
        }

        switchPlayer();
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == ember) ? gep : ember;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static Optional<Player> getWinner() {
        return winner;
    }

    public boolean isFinished() {
        return finished;
    }

    public Board getBoard() {
        return board;
    }

    public Player getEmber() {
        return ember;
    }

    public Player getGep() {
        return gep;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public void placeInitial(Move move) {
        board.place(move);
    }

    public void forceGepTurn() {
        currentPlayer = gep;
    }

    // ===== GETTEREK A STATISZTIKÁHOZ =====
    public int getTotalMoves() {
        return totalMoves;
    }

    public int getEmberMoves() {
        return emberMoves;
    }

    public int getGepMoves() {
        return gepMoves;
    }
}
