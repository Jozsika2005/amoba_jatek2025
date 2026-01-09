package hu.szakacsjozsef.amoba.model;

import java.util.Arrays;
import java.util.Optional;

public class Board {

    private static final char EMPTY = '.';

    private final int size;
    private final char[][] grid;

    public Board(int size) {
        this.size = size;
        this.grid = new char[size][size];

        for (int i = 0; i < size; i++) {
            Arrays.fill(grid[i], EMPTY);
        }
    }

    public boolean place(Move move) {
        int row = move.getRow();
        int col = move.getCol();

        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }

        if (grid[row][col] != EMPTY) {
            return false;
        }

        grid[row][col] = move.getPlayer().getSymbol();
        return true;
    }

    public Optional<Player> checkWinner(Player p1, Player p2) {
        Optional<Player> winner;

        winner = checkRows(p1, p2);
        if (winner.isPresent()) {
            return winner;
        }

        winner = checkColumns(p1, p2);
        if (winner.isPresent()) {
            return winner;
        }

        winner = checkMainDiagonal(p1, p2);
        if (winner.isPresent()) {
            return winner;
        }

        return checkAntiDiagonal(p1, p2);
    }

    private Optional<Player> checkRows(Player p1, Player p2) {
        for (int row = 0; row < size; row++) {
            char first = grid[row][0];

            if (first == EMPTY) {
                continue;
            }

            boolean win = true;

            for (int col = 1; col < size; col++) {
                if (grid[row][col] != first) {
                    win = false;
                    break;
                }
            }

            if (win) {
                return Optional.of(resolvePlayer(first, p1, p2));
            }
        }
        return Optional.empty();
    }

    private Optional<Player> checkColumns(Player p1, Player p2) {
        for (int col = 0; col < size; col++) {
            char first = grid[0][col];

            if (first == EMPTY) {
                continue;
            }

            boolean win = true;

            for (int row = 1; row < size; row++) {
                if (grid[row][col] != first) {
                    win = false;
                    break;
                }
            }

            if (win) {
                return Optional.of(resolvePlayer(first, p1, p2));
            }
        }
        return Optional.empty();
    }

    private Optional<Player> checkMainDiagonal(Player p1, Player p2) {
        char first = grid[0][0];

        if (first == EMPTY) {
            return Optional.empty();
        }

        for (int i = 1; i < size; i++) {
            if (grid[i][i] != first) {
                return Optional.empty();
            }
        }

        return Optional.of(resolvePlayer(first, p1, p2));
    }

    private Optional<Player> checkAntiDiagonal(Player p1, Player p2) {
        char first = grid[0][size - 1];

        if (first == EMPTY) {
            return Optional.empty();
        }

        for (int i = 1; i < size; i++) {
            if (grid[i][size - 1 - i] != first) {
                return Optional.empty();
            }
        }

        return Optional.of(resolvePlayer(first, p1, p2));
    }

    private Player resolvePlayer(char symbol, Player p1, Player p2) {
        if (symbol == p1.getSymbol()) {
            return p1;
        }
        return p2;
    }

    public boolean isFull() {
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getSize() {
        return size;
    }

    public char[][] getGridCopy() {
        char[][] copy = new char[size][size];

        for (int i = 0; i < size; i++) {
            copy[i] = Arrays.copyOf(grid[i], size);
        }

        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (char[] row : grid) {
            for (char cell : row) {
                sb.append(cell).append(' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}
