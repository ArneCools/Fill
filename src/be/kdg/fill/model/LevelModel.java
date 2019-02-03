package be.kdg.fill.model;

import java.util.List;

public class LevelModel {
    private List<Level> levels;
    private Difficulty difficulty;

    public LevelModel(Difficulty difficulty) {
        this.difficulty = difficulty;
        getLevelsInfo();

    }

    private void getLevelsInfo() {
        levels = LevelData.getLevels(difficulty);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public List<Level> getLevels() {
        return levels;
    }
}
