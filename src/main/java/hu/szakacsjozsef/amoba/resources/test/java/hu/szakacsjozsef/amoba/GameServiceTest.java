package hu.szakacsjozsef.amoba;

import hu.szakacsjozsef.amoba.service.GameService;
import hu.szakacsjozsef.amoba.service.HighScoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {

    private GameService gameService;
    private HighScoreService highScoreServiceMock;

    @BeforeEach
    void init() {
        highScoreServiceMock = Mockito.mock(HighScoreService.class);
        gameService = new GameService(highScoreServiceMock);
    }

    @Test
    void testUresTablaLetrejon() {
        char[][] tabla = gameService.ujJatek();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(' ', tabla[i][j]);
            }
        }
    }

    @Test
    void testErvenyesLepes() {
        gameService.ujJatek();
        boolean siker = gameService.lep(0, 0, 'X');

        assertTrue(siker);
        assertEquals('X', gameService.getTabla()[0][0]);
    }

    @Test
    void testFoglaltMezoreNemLehetLepni() {
        gameService.ujJatek();
        gameService.lep(0, 0, 'X');
        boolean siker = gameService.lep(0, 0, 'O');

        assertFalse(siker);
    }

    @Test
    void testXNyertes() {
        gameService.ujJatek();
        gameService.lep(0, 0, 'X');
        gameService.lep(1, 0, 'O');
        gameService.lep(0, 1, 'X');
        gameService.lep(1, 1, 'O');
        gameService.lep(0, 2, 'X');

        assertTrue(gameService.jatekosNyert('X'));
    }

    @Test
    void testDontetlen() {
        gameService.ujJatek();
        char[][] dontetlenTabla = {
                {'X','O','X'},
                {'X','X','O'},
                {'O','X','O'}
        };

        gameService.setTabla(dontetlenTabla);

        assertTrue(gameService.dontetlen());
    }

    @Test
    void testHighScoreMentes() {
        gameService.ujJatek();
        gameService.mentesHighScore("Jóska", 12);

        Mockito.verify(highScoreServiceMock)
                .addScore("Jóska", 12);
    }
}
