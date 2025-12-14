package com.example.amoba.model;
public boolean place(Move move) {
int r = move.getRow();
int c = move.getCol();
if (r < 0 || r >= size || c < 0 || c >= size) return false;
if (grid[r][c] != EMPTY) return false;
grid[r][c] = move.getPlayer().getSymbol();
return true;
}


public Optional<Player> checkWinner(Player p1, Player p2) {
char s1 = p1.getSymbol(), s2 = p2.getSymbol();
// rows
for (int r=0;r<size;r++) {
char first = grid[r][0];
if (first==EMPTY) continue;
boolean ok = true;
for (int c=1;c<size;c++) if (grid[r][c]!=first) { ok=false; break; }
if (ok) return Optional.of(getPlayerForSymbol(s1,s2, first, p1, p2));
}
// cols
for (int c=0;c<size;c++) {
char first = grid[0][c];
if (first==EMPTY) continue;
boolean ok = true;
for (int r=1;r<size;r++) if (grid[r][c]!=first) { ok=false; break; }
if (ok) return Optional.of(getPlayerForSymbol(s1,s2, first, p1, p2));
}
// diag
char first = grid[0][0];
if (first!=EMPTY) {
boolean ok = true;
for (int k=1;k<size;k++) if (grid[k][k]!=first) { ok=false; break; }
if (ok) return Optional.of(getPlayerForSymbol(s1,s2, first, p1, p2));
}
// anti-diag
first = grid[0][size-1];
if (first!=EMPTY) {
boolean ok = true;
for (int k=1;k<size;k++) if (grid[k][size-1-k]!=first) { ok=false; break; }
if (ok) return Optional.of(getPlayerForSymbol(s1,s2, first, p1, p2));
}
return Optional.empty();
}


private Player getPlayerForSymbol(char s1, char s2, char found, Player p1, Player p2) {
if (found==s1) return p1;
if (found==s2) return p2;
return null;
}


public boolean isFull(){
for (char[] row : grid) for (char cell : row) if (cell==EMPTY) return false;
return true;
}


public int getSize(){ return size; }


public char[][] getGridCopy(){
char[][] copy = new char[size][size];
for (int i=0;i<size;i++) copy[i]=Arrays.copyOf(grid[i], size);
return copy;
}


@Override
public String toString(){
StringBuilder sb = new StringBuilder();
for (char[] row : grid){
for (char c : row) sb.append(c).append(' ');
sb.append('\n');
}
return sb.toString();
}
}
