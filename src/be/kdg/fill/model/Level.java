package be.kdg.fill.model;

import java.io.Serializable;
import java.util.List;

public class Level implements Serializable, Comparable<Level> {
    private Difficulty difficulty;
    private Grid grid;
    private int levelNumber;
    private boolean isSolvable = false;

    public Level(int width, int height, Difficulty difficulty, int levelNumber) {
        this.levelNumber = levelNumber;
        this.difficulty = difficulty;
        grid = new Grid(new Block[width][height]);
        for (int i = 0; i < grid.getBlocks().length; i++) {
            for (int j = 0; j < grid.getBlocks()[0].length; j++) {
                grid.getBlocks()[i][j] = new Block(i, j);
            }

        }
    }

    public Level(int width, int height, Difficulty difficulty, int levelNumber, Grid grid) {
        this.levelNumber = levelNumber;
        this.difficulty = difficulty;
        this.grid = grid;
    }


    public Grid getGrid() {
        return grid;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    @Override
    public int compareTo(Level o) {
        return ((Integer) (levelNumber)).compareTo(o.levelNumber);
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public void setSolvable(boolean solvable) {
        isSolvable = solvable;
    }

    public void setCompleted() {
        LevelData.levelCompleted(difficulty);
        LevelData.savePlayerData();
    }
}
