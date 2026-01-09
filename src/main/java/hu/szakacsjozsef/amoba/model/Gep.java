package hu.szakacsjozsef.amoba.model;

import java.util.Random;

public class Gep {

    private final Random random = new Random();

    public Move lep(Board board, Player gepPlayer) {
        int size = board.getSize();
        char[][] grid = board.getGridCopy();

        while (true) {
            int r = random.nextInt(size);
            int c = random.nextInt(size);

            if (grid[r][c] == '.') {
                return new Move(r, c, gepPlayer);
            }
        }
    }
}
