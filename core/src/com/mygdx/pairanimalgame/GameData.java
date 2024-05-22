package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

public class GameData {
    static private Preferences gamePreferences;
    static final String bestRankPrefs = "bestRankPrefs";
    static final String highScorePrefs = "highScorePrefs";
    private String rankName;
    private int currentLevel;
    private int[][] matrix;
    private int matrixBehaviour;
    private int score;
    private int remainSeconds;
    private boolean isWon = false;

    public GameData(AnimalMatrix animalMatrix) {
        if(animalMatrix.matrix != null){
            int r = animalMatrix.matrix.length;
            int c = animalMatrix.matrix[0].length;
            matrix = new int[r-2][c-2];
            this.currentLevel = animalMatrix.level;
            this.matrixBehaviour = animalMatrix.matrixBehaviour;
            this.rankName = animalMatrix.rankName;
            this.score = animalMatrix.score;
            this.remainSeconds = animalMatrix.remainSeconds;

            for (int i = 0; i<matrix.length; i++){
                for(int j = 0; j<matrix[0].length; j++){
                    if(animalMatrix.matrix[i+1][j+1].isActive())
                        matrix[i][j] = animalMatrix.matrix[i+1][j+1].getType();
                    else matrix[i][j] = -1;
                }
            }
        }
        else {
            this.rankName = animalMatrix.rankName;
            this.currentLevel = 1;
            this.matrix = null;
            this.matrixBehaviour = 0;
            this.score = 0;
            this.remainSeconds = Rank.remainSeconds(rankName);
        }
    }
    public GameData(AnimalMatrix animalMatrix, boolean isWin) {
        this(animalMatrix);
        this.isWon = isWin;

        this.rankName = animalMatrix.rankName;
        this.currentLevel = 1;
        this.matrix = null;
        this.matrixBehaviour = 0;
        this.score = 0;
        this.remainSeconds = Rank.remainSeconds(rankName);
    }

    public GameData(String rankName) {
        this.rankName = rankName;
        this.currentLevel = 1;
        this.matrix = null;
        this.matrixBehaviour = 0;
        this.score = 0;
        this.remainSeconds = Rank.remainSeconds(rankName);
    }

    static Preferences getGamePreferences() {
        if(gamePreferences == null){
            gamePreferences = Gdx.app.getPreferences("MyGamePreferences");
            return gamePreferences;
        }
        else return gamePreferences;
    }

    //Serialize
    static void save(GameData gameData){
        Json json = new Json();
        String gameDataJson = json.toJson(gameData);
        Preferences prefs = getGamePreferences();
        prefs.putString(gameData.rankName, gameDataJson);
        prefs.flush();
        System.out.println("GameData saved");
        updateHighScore(gameData.getScore());
    }

    // Deserialize
    static GameData load(String rankName){
        Preferences prefs = getGamePreferences();
        String gameData = prefs.getString(rankName, "");
        if (!gameData.isEmpty()) {
            Json json = new Json();

            return json.fromJson(GameData.class, gameData);
        }
        else {
            GameData data = new GameData();
            data.rankName = rankName;
            data.currentLevel = 1;
            data.matrix = null;
            data.matrixBehaviour = 2;
            data.score = 0;
            data.remainSeconds = Rank.remainSeconds(rankName);
            return data;
        }
    }
    static void updateHighScore(int score){
        if(score>getHighScore()){
            Preferences prefs = getGamePreferences();
            prefs.putInteger(highScorePrefs, score);
            prefs.flush();
            System.out.println("updateHighScore saved");
        }
    }
    static void updateBestRank(String rank){
        if(Rank.rankName(rank)>Rank.rankName(getBestRank())) {
            Preferences prefs = getGamePreferences();
            prefs.putString(bestRankPrefs, rank);
            prefs.flush();
            System.out.println("updateBestRank saved");
        }
    }
    static int getHighScore(){
        Preferences prefs = getGamePreferences();
        return prefs.getInteger(highScorePrefs, 0);
    }
    static String getBestRank(){
        Preferences prefs = getGamePreferences();
        return prefs.getString(bestRankPrefs, Rank.Rank1.name);
    }
    static void removeAllGamePreferences(){
        Preferences prefs = Gdx.app.getPreferences("MyGamePreferences");

        prefs.remove(Rank.Rank1.name);
        prefs.remove(Rank.Rank2.name);
        prefs.remove(Rank.Rank3.name);
        prefs.remove(Rank.Rank4.name);
        prefs.flush(); // Đảm bảo rằng thay đổi được ghi lại
        System.out.println("GameData: delete gameData");
    }

    static void removePreferences(String rankNamePreferences){
        Preferences prefs = Gdx.app.getPreferences("MyGamePreferences");

        prefs.remove(rankNamePreferences);
        prefs.flush(); // Đảm bảo rằng thay đổi được ghi lại
        System.out.println("GameData: delete gameData");
    }

    public GameData() {
        // Constructor mặc định cần thiết cho quá trình deserialization
    }

    // Getter và Setter cần thiết cho Json để serialize/deserialize

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getMatrixBehaviour() {
        return matrixBehaviour;
    }

    public void setMatrixBehaviour(int matrixBehaviour) {
        this.matrixBehaviour = matrixBehaviour;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }
}

