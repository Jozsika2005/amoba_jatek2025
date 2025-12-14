package com.example.amoba;


import com.example.amoba.model.Game;
import com.example.amoba.model.Player;
import com.example.amoba.persistence.ScoreRepository;
import com.example.amoba.service.GameService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.mockito.Mockito.*;


public class GameServiceTest {
@Test
public void testSaveScoreOnWin() throws Exception {
Player p1 = new Player("A",'X');
Player p2 = new Player("B",'O');
Game game = new Game(3, p1, p2);
// Az x a felső sor kitöltésével nyer
game.makeMove(0,0); // X
game.makeMove(1,0); // O
game.makeMove(0,1); // X
game.makeMove(1,1); // O
game.makeMove(0,2); // X -> wins


ScoreRepository repo = Mockito.mock(ScoreRepository.class);
GameService svc = new GameService(game, repo);


// perform a valid move (will be ignored if invalid), but winner already exists — our service checks winner after moves
svc.makeMove(2,2);


verify(repo, atLeastOnce()).saveScore(eq("A"), anyInt());
}
}
