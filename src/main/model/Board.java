
package hu.uni.amoeba.model;


public class Board {
private final Player[][] grid = new Player[3][3];

public Board() {
  
}
  


public Player get(int r, int c) {
return grid[r][c];
}


public void set(int r, int c, Player p) {
grid[r][c] = p;
}


public boolean isFull() {
for (int i = 0; i < 3; i++) {
for (int j = 0; j < 3; j++) {
if (grid[i][j] == null) return false;
}
}
return true;
}


public void print() {
System.out.println("-------------");
for (int i = 0; i < 3; i++) {
System.out.print("| ");
for (int j = 0; j < 3; j++) {
System.out.print((grid[i][j] == null ? ' ' : grid[i][j].name()) + " | ");
}
System.out.println();
System.out.println("-------------");
}
}
}
