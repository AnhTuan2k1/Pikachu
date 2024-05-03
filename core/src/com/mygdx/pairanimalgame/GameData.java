package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

public class GameData {
    static final String prefsKey = "gameData";
    int currentLevel;
    int[][] matrix;
    private int matrixBehaviour;

    public GameData(AnimalMatrix animalMatrix) {
        int r = animalMatrix.matrix.length;
        int c = animalMatrix.matrix[0].length;
        matrix = new int[r-2][c-2];
        this.currentLevel = animalMatrix.level;
        this.matrixBehaviour = animalMatrix.matrixBehaviour.ordinal();

        for (int i = 0; i<matrix.length; i++){
            for(int j = 0; j<matrix[0].length; j++){
                if(animalMatrix.matrix[i+1][j+1].isActive())
                    matrix[i][j] = animalMatrix.matrix[i+1][j+1].getType();
                else matrix[i][j] = -1;
            }
        }
    }

    //Serialize
    static void save(GameData gameData){
        Json json = new Json();
        String gameDataJson = json.toJson(gameData);
        Preferences prefs = Gdx.app.getPreferences("MyGamePreferences");
        prefs.putString(GameData.prefsKey, gameDataJson);
        prefs.flush();
        System.out.println("GameData saved");
    }

    // Deserialize
    static GameData load(){
        Preferences prefs = Gdx.app.getPreferences("MyGamePreferences");
        String gameData = prefs.getString(GameData.prefsKey, "");
        if (!gameData.isEmpty()) {
            Json json = new Json();

            return json.fromJson(GameData.class, gameData);
        }
        else return null;
    }

    static void removePreferences(){
        Preferences prefs = Gdx.app.getPreferences("MyGamePreferences");

        prefs.remove(GameData.prefsKey);
        prefs.flush(); // Đảm bảo rằng thay đổi được ghi lại
        System.out.println("GameData: delete gameData");
    }

    public GameData() {
        // Constructor mặc định cần thiết cho quá trình deserialization
    }

    // Getter và Setter cần thiết cho Json để serialize/deserialize

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
}

