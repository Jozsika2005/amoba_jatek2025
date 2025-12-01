import java.util.Scanner;

public class AmobaJatek {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] tabla = new char[3][3];

    
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabla[i][j] = ' ';
            }
        }

        boolean jatekVege = false;
        char jelenlegiJatekos = 'X';

        System.out.println("Üdv az amőba játékban!");
        printTabla(tabla);

        while (!jatekVege) {
            System.out.println("Játékos " + jelenlegiJatekos + ", írd be a sor és oszlop számát (0-2):");
            int sor = scanner.nextInt();
            int oszlop = scanner.nextInt();

            if (sor < 0 || sor > 2 || oszlop < 0 || oszlop > 2) {
                System.out.println("Hibás koordináták! Próbáld újra.");
                continue;
            }

            if (tabla[sor][oszlop] != ' ') {
                System.out.println("Ez a hely már foglalt! Próbáld újra.");
                continue;
            }

            tabla[sor][oszlop] = jelenlegiJatekos;
            printTabla(tabla);

            if (nyert(tabla, jelenlegiJatekos)) {
                System.out.println("Gratulálok! Játékos " + jelenlegiJatekos + " nyert!");
                jatekVege = true;

            } else if (dontetlen(tabla)) {
                System.out.println("Döntetlen!");
                jatekVege = true;

            } else {
                jelenlegiJatekos = (jelenlegiJatekos == 'X') ? 'O' : 'X';
            }
        }

        scanner.close();
    }

    public static void printTabla(char[][] tabla) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(tabla[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    public static boolean nyert(char[][] tabla, char jatekos) {
        for (int i = 0; i < 3; i++) {
            if (tabla[i][0] == jatekos && tabla[i][1] == jatekos && tabla[i][2] == jatekos) return true;
            if (tabla[0][i] == jatekos && tabla[1][i] == jatekos && tabla[2][i] == jatekos) return true;
        }

        if (tabla[0][0] == jatekos && tabla[1][1] == jatekos && tabla[2][2] == jatekos) return true;
        if (tabla[0][2] == jatekos && tabla[1][1] == jatekos && tabla[2][0] == jatekos) return true;

        return false;
    }

    public static boolean dontetlen(char[][] tabla) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabla[i][j] == ' ') return false;
            }
        }
        return true;
    }
}
