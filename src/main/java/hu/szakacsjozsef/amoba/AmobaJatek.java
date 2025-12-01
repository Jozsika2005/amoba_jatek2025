
import java.util.Scanner;

public class AmobaJatek {

    private static char[][] board = new char[3][3];
    private static char currentPlayer = 'X';

    public static void main(String[] args) {
        initializeBoard();
        playGame();
    }

    private static void initializeBoard() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c] = ' ';
            }
        }
    }

    private static void playGame() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            printBoard();
            System.out.println("Jelenlegi játékos: " + currentPlayer);
            System.out.print("Add meg a sort (1-3): ");
            int row = sc.nextInt() - 1;

            System.out.print("Add meg az oszlopot (1-3): ");
            int col = sc.nextInt() - 1;

            if (!isValidMove(row, col)) {
                System.out.println("Érvénytelen lépés! Próbáld újra.");
                continue;
            }

            board[row][col] = currentPlayer;

            if (checkWin(currentPlayer)) {
                printBoard();
                System.out.println("Játékos " + currentPlayer + " nyert!");
                break;
            }

            if (isDraw()) {
                printBoard();
                System.out.println("Döntetlen!");
                break;
            }

            switchPlayer();
        }

        sc.close();
    }

    private static void printBoard() {
        System.out.println("\n  1   2   3 ");
        for (int r = 0; r < 3; r++) {
            System.out.println(" +---+---+---+");

            System.out.print((r + 1) + "|");
            for (int c = 0; c < 3; c++) {
                System.out.print(" " + board[r][c] + " |");
            }
            System.out.println();
        }
        System.out.println(" +---+---+---+\n");
    }

    private static boolean isValidMove(int r, int c) {
        if (r < 0 || r >= 3 || c < 0 || c >= 3) return false;
        return board[r][c] == ' ';
    }

    private static void switchPlayer() {
        if (currentPlayer == 'X') currentPlayer = 'O';
        else currentPlayer = 'X';
    }

    private static boolean checkWin(char player) {

        for (int r = 0; r < 3; r++) {
            if (board[r][0] == player && board[r][1] == player && board[r][2] == player)
                return true;
        }

        for (int c = 0; c < 3; c++) {
            if (board[0][c] == player && board[1][c] == player && board[2][c] == player)
                return true;
        }

        if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
            return true;

        if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return true;

        return false;
    }


    private static boolean isDraw() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == ' ') return false;
            }
        }
        return true;
    }
}

