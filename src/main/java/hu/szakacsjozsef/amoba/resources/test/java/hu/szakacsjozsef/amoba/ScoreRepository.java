package com.example.amoba.persistence;


import java.util.List;
import java.util.Map;


public interface ScoreRepository {
void init() throws Exception;
void saveScore(String playerName, int score) throws Exception;
List<Map<String,Object>> topScores(int limit) throws Exception;
void close() throws Exception;
}
