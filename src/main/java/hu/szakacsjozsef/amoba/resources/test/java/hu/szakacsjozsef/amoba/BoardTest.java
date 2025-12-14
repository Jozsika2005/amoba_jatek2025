package com.example.amoba;


import com.example.amoba.model.Board;
import com.example.amoba.model.Player;
import com.example.amoba.model.Move;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BoardTest {
@Test
public void testPlaceAndFull() {
Board b = new Board(3);
Player p = new Player("A", 'X');
assertTrue(b.place(new Move(0,0,p)));
assertFalse(b.place(new Move(0,0,p))); // already occupied
}


@Test
public void testWinnerRow() {
Player p1 = new Player("A",'X');
Player p2 = new Player("B",'O');
Board b = new Board(3);
b.place(new Move(0,0,p1));
b.place(new Move(0,1,p1));
b.place(new Move(0,2,p1));
assertTrue(b.checkWinner(p1,p2).isPresent());
}
}
