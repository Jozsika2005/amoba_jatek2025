package com.example.amoba.persistence;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.*;
import java.util.*;


public class H2ScoreRepository implements ScoreRepository {
private static final Logger logger = LoggerFactory.getLogger(H2ScoreRepository.class);
private final String url;
private Connection conn;


public H2ScoreRepository(String url) {
this.url = url;
}


@Override
public void init() throws Exception {
conn = DriverManager.getConnection(url);
try (Statement s = conn.createStatement()) {
s.execute("CREATE TABLE IF NOT EXISTS highscores (id IDENTITY PRIMARY KEY, name VARCHAR(255), score INT, ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP())");
}
logger.info("H2ScoreRepository initialized with url={}", url);
}


@Override
public void saveScore(String playerName, int score) throws Exception {
try (PreparedStatement ps = conn.prepareStatement("INSERT INTO highscores(name,score) VALUES(?,?)")) {
ps.setString(1, playerName);
ps.setInt(2, score);
ps.executeUpdate();
}
logger.info("Saved score: {} -> {}", playerName, score);
}


@Override
public List<Map<String, Object>> topScores(int limit) throws Exception {
try (PreparedStatement ps = conn.prepareStatement("SELECT name,score,ts FROM highscores ORDER BY score DESC, ts ASC LIMIT ?")) {
ps.setInt(1, limit);
try (ResultSet rs = ps.executeQuery()) {
List<Map<String,Object>> out = new ArrayList<>();
while (rs.next()) {
Map<String,Object> row = new HashMap<>();
row.put("name", rs.getString("name"));
row.put("score", rs.getInt("score"));
row.put("ts", rs.getTimestamp("ts"));
out.add(row);
}
return out;
}
}
}


@Override
public void close() throws Exception {
if (conn!=null && !conn.isClosed()) conn.close();
}
}
