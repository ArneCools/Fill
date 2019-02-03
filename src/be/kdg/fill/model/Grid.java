package be.kdg.fill.model;

import java.io.Serializable;
import java.util.List;

public class Grid implements Serializable {
    private Block[][] blocks;
    private List<Block> solutionPath;
    private Difficulty difficulty;

    public Grid(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.blocks = new Block[difficulty.getWidth()][difficulty.getHeight()];
        LoadLevel();
    }

    void LoadLevel() {
        for (int i = 0; i < difficulty.getWidth(); i++) {
            for (int j = 0; j < difficulty.getHeight(); j++) {
                blocks[i][j] = new Block(i, j);
            }
        }
    }

    public Grid(Block[][] blocks) {
        this.blocks = blocks;
    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public List<Block> getSolutionPath() {
        return solutionPath;
    }

    public void setSolutionPath(List<Block> solutionPath) {
        this.solutionPath = solutionPath;
    }

}
