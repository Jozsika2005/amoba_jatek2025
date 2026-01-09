package hu.szakacsjozsef.amoba.ui;

import java.io.IOException;
import java.util.Scanner;

import hu.szakacsjozsef.amoba.exception.InvalidMoveException;
import hu.szakacsjozsef.amoba.model.Game;
import hu.szakacsjozsef.amoba.model.Gep;
import hu.szakacsjozsef.amoba.model.Move;
import hu.szakacsjozsef.amoba.model.Player;
import hu.szakacsjozsef.amoba.service.GameFileService;
import hu.szakacsjozsef.amoba.service.GameXmlService;
import hu.szakacsjozsef.amoba.service.HighScoreService;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ===== NÉV BEKÉRÉSE =====
        System.out.print("Add meg a neved: ");
        Player ember = new Player(scanner.nextLine(), 'X');
        Player gep = new Player("Gép", 'O');
        Gep gepAI = new Gep();

        // ===== CÍM =====
        System.out.println();
        System.out.println("=====================");
        System.out.println("     AMŐBA JÁTÉK     ");
        System.out.println("=====================");

        boolean vanTxtMentes = GameFileService.hasSavedGame();
        boolean vanXmlMentes = GameXmlService.hasSave();

        int valasztas = menuValasztas(scanner, vanTxtMentes, vanXmlMentes);

        if (valasztas == 0) {
            System.out.println("Kilépés...");
            return;
        }

        if (valasztas == 4) {
            try {
                HighScoreService.show();
            } catch (IOException e) {
                System.out.println("Nem sikerült betölteni a highscore-t.");
            }
            return;
        }

        if (valasztas == 3) {
            try {
                GameFileService.showHistory();
            } catch (IOException e) {
                System.out.println("Nem sikerült betölteni az előzményeket.");
            }
            return;
        }

        Game game;

        try {
            if (valasztas == 2 && vanTxtMentes) {
                System.out.println("Félbehagyott TXT játék betöltése...");
                game = GameFileService.load(ember, gep);

            } else if (valasztas == 5 && vanXmlMentes) {
                System.out.println("Félbehagyott XML játék betöltése...");
                game = GameXmlService.load(ember, gep);

            } else {
                int tablaMeret = bekerTablaMeret(scanner);
                game = new Game(ember, gep, tablaMeret);

                // előre lerakott mező (plusz pont)
                game.placeInitial(new Move(0, 0, ember));
            }

        } catch (IOException e) {
            System.out.println("Hiba történt a betöltés során.");
            return;
        }

        // ===== JÁTÉK CIKLUS =====
        while (!game.isFinished()) {
            System.out.println();
            System.out.println(game.getBoard());
            System.out.println("Aktuális játékos: "
                    + game.getCurrentPlayer().getName());

            try {
                if (game.getCurrentPlayer() == ember) {

                    System.out.print(
                            "Lépés (pl. 2 3) | help | exit: ");
                    String input = scanner.nextLine().trim();

                    if (input.equalsIgnoreCase("help")) {
                        kiirHelp();
                        continue;
                    }

                    if (input.equalsIgnoreCase("exit")) {
                        GameFileService.save(game);
                        GameXmlService.save(game);
                        System.out.println(
                                "Játék elmentve. Kilépés...");
                        return;
                    }

                    String[] parts = input.split("\\s+");
                    if (parts.length != 2) {
                        System.out.println("Hibás formátum!");
                        continue;
                    }

                    int r = Integer.parseInt(parts[0]) - 1;
                    int c = Integer.parseInt(parts[1]) - 1;

                    game.makeMove(new Move(r, c, ember));

                } else {
                    Move gepMove = gepAI.lep(game.getBoard(), gep);
                    System.out.println("Gép lépett: "
                            + (gepMove.getRow() + 1) + ", "
                            + (gepMove.getCol() + 1));
                    game.makeMove(gepMove);
                }

                // mentés minden lépés után
                GameFileService.save(game);
                GameXmlService.save(game);

            } catch (InvalidMoveException
                     | IOException
                     | NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        // ===== JÁTÉK VÉGE =====
        System.out.println();
        System.out.println(game.getBoard());

        try {
            GameFileService.saveFinished(game);
            GameFileService.deleteSave();
            GameXmlService.delete();
        } catch (IOException e) {
            System.out.println("Nem sikerült menteni a játék végét.");
        }

        // ===== STATISZTIKA + HIGHSCORE =====
        System.out.println();
        System.out.println("=== STATISZTIKA ===");
        System.out.println("Összes lépés: " + game.getTotalMoves());
        System.out.println("Ember lépései: " + game.getEmberMoves());
        System.out.println("Gép lépései: " + game.getGepMoves());

        game.getWinner().ifPresentOrElse(
                w -> {
                    System.out.println("Győztes: " + w.getName());
                    try {
                        HighScoreService.addWin(
                                w.getName(),
                                game.getTotalMoves());
                    } catch (IOException e) {
                        System.out.println(
                                "Highscore mentése sikertelen.");
                    }
                },
                () -> System.out.println("Döntetlen!")
        );
    }

    // ===== HELP =====
    private static void kiirHelp() {
        System.out.println();
        System.out.println("Parancsok:");
        System.out.println("- sor oszlop  (pl. 2 3)");
        System.out.println("- help       (segítség)");
        System.out.println("- exit       (mentés és kilépés)");
        System.out.println();
    }

    // ===== MENÜ =====
    private static int menuValasztas(
            Scanner scanner, boolean vanTxt, boolean vanXml) {

        while (true) {
            System.out.println();
            System.out.println("1 - Új játék");

            if (vanTxt) {
                System.out.println(
                        "2 - Félbehagyott játék folytatása (TXT)");
            }

            System.out.println(
                    "3 - Lejátszott meccsek megtekintése");

            if (vanXml) {
                System.out.println(
                        "5 - Félbehagyott játék folytatása (XML)");
            }

            System.out.println("4 - Highscore megtekintése");
            System.out.println("0 - Kilépés");
            System.out.print("Választás: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Hibás bevitel!");
                scanner.next();
                continue;
            }

            int v = scanner.nextInt();
            scanner.nextLine();

            if (v == 0 || v == 1 || v == 3 || v == 4
                    || (vanTxt && v == 2)
                    || (vanXml && v == 5)) {
                return v;
            }

            System.out.println("Érvénytelen választás!");
        }
    }

    // ===== TÁBLA MÉRET =====
    private static int bekerTablaMeret(Scanner scanner) {
        int meret;

        while (true) {
            System.out.print("Add meg a tábla méretét (4–25): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Hibás érték!");
                scanner.next();
                continue;
            }

            meret = scanner.nextInt();

            if (meret < 4 || meret > 25) {
                System.out.println(
                        "A méretnek 4 és 25 között kell lennie!");
                continue;
            }

            scanner.nextLine();
            return meret;
        }
    }
}
