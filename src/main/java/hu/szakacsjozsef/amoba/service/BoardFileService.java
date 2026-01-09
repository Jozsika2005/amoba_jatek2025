package hu.szakacsjozsef.amoba.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import hu.szakacsjozsef.amoba.model.Board;

public class BoardFileService {

    public static void saveBoard(Board board, String fileName)
            throws IOException {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName))) {

            int size = board.getSize();
            char[][] grid = board.getGridCopy();

            writer.write("SIZE=" + size);
            writer.newLine();

            for (int r = 0; r < size; r++) {
                writer.write(grid[r]);
                writer.newLine();
            }
        }
    }
}