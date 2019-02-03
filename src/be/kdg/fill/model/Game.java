package be.kdg.fill.model;

import java.util.ArrayList;
import java.util.List;

/**
 * De klasse Game is de belangrijkste model klasse.
 * Deze zal zorgen dat je met je muis een pad kan maken, hints getoont kunnen worden en dat je kan zien of je gewonnen hebt.
 *
 * @author Arne Cools, Ben Van De Vliet
 * @version 0.1
 */
public class Game {
    private Grid grid;
    private List<Block> path;
    private List<Block> hintPath;
    private final Level level;
    private int blocksToFill;
    private boolean gameEnded = false;
    private boolean isCreateLevel = false;
    private int hintSpacesShown = 1;


    /**
     * De constructor initialiseert de lijst voor het path en het hintpath.
     *
     * @param level         Het grid dat je wil gebruiken voor het spel.
     * @param isCreateLevel True als er een spel wordt gestart om een level te testen.
     */
    public Game(Level level, boolean isCreateLevel) {

        this.grid = (Grid) level.getGrid();
        this.level = level;
        this.isCreateLevel = isCreateLevel;
        path = new ArrayList<Block>();
        hintPath = new ArrayList<Block>();
        blocksToFill = 0;
        for (int i = 0; i < grid.getBlocks().length; i++) {
            for (int j = 0; j < grid.getBlocks()[0].length; j++) {
                if (grid.getBlocks()[i][j].getBlockState().equals(BlockState.startblock)) {
                    path.add(grid.getBlocks()[i][j]);
                }
                if (grid.getBlocks()[i][j].getBlockState().equals(BlockState.empty)) {
                    blocksToFill++;
                }
            }
        }


        System.out.println("path = " + path);
    }

    /**
     * @return Geeft een referentie naar het level terug.
     */
    public Level getLevel() {
        return level;
    }

    /**
     * @return Geeft een referentie naar het grid terug.
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Wordt gebruikt om het grid weer naar zijn orginele staat te zetten.
     */
    public void resetGrid() {
        for (int i = 0; i < grid.getBlocks().length; i++) {
            for (int j = 0; j < grid.getBlocks()[0].length; j++) {
                if (grid.getBlocks()[i][j].getBlockState().equals(BlockState.selected)) {
                    grid.getBlocks()[i][j].setBlockState(BlockState.empty);
                }
            }
        }
    }

    /**
     * Zorgt er voor dat het path en het grid wordt aangepast als de gebruiker met de muis over het grid beweegt.
     *
     * @param x De x coördinaat van het blok
     * @param y De y coördinaat van het blok
     */
    public void handleDrag(int x, int y) {
        //Check if something should happen when dragging over blocks
        Block block = grid.getBlocks()[x][y];
        if (hdCheckIfInPath(block)) return;
        if (hdCheckBlock(block) && hdCheckNeighbours(block)) {
            grid.getBlocks()[x][y].setBlockState(BlockState.selected);
            path.add(block);
        }

    }

    private boolean hdCheckBlock(Block block) {
        //Check if block is empty
        return block.getBlockState().equals(BlockState.empty);
    }

    private boolean hdCheckNeighbours(Block block) {
        //Check if block is a neighbour of the last block placed
        if (block.getRow() == path.get(path.size() - 1).getRow() - 1 && block.getColumn() == path.get(path.size() - 1).getColumn())
            return true;
        if (block.getRow() == path.get(path.size() - 1).getRow() + 1 && block.getColumn() == path.get(path.size() - 1).getColumn())
            return true;
        if (block.getColumn() == path.get(path.size() - 1).getColumn() - 1 && block.getRow() == path.get(path.size() - 1).getRow())
            return true;
        if (block.getColumn() == path.get(path.size() - 1).getColumn() + 1 && block.getRow() == path.get(path.size() - 1).getRow())
            return true;
        return false;
    }

    private boolean hdCheckIfInPath(Block block) {
        List<Block> tempPath = new ArrayList<Block>();
        boolean check = false;
        for (Block pathBlock :
                path) {
            if (check) {
                pathBlock.setBlockState(BlockState.empty);
            } else {
                tempPath.add(pathBlock);
            }
            if (block.equals(pathBlock)) {
                path = tempPath;
                check = true;
            }
        }

        if (check) {
            path = tempPath;
        }
        return check;
    }

    /**
     * Zorgt ervoor dat het hintpath wordt aangepast, indien er nog hints zijn.
     */
    public void showHint() {
        hintSpacesShown += 5;
        if (hintSpacesShown > grid.getSolutionPath().size()) hintSpacesShown = grid.getSolutionPath().size();
        hintPath = new ArrayList<Block>();
        for (int i = 0; i < hintSpacesShown; i++) {
            hintPath.add(grid.getSolutionPath().get(i));
        }
    }


    /**
     * Zal checken of het spel gewonnen is.
     * @return True als het spel gewonnnen is
     */
    public boolean checkIfWon() {
        if (blocksToFill == path.size() - 1 && !gameEnded) {
            gameEnded = true;
            System.out.println("Game won!");
            resetGrid();
            if (isCreateLevel) {
                level.getGrid().setSolutionPath(path);
                level.setSolvable(true);
            }
            return true;
        }

        return false;
    }

    /**
     * @return Geeft een referentie naar het path terug.
     */
    public List<Block> getPath() {
        return path;
    }

    /**
     * @return Geeft een referentie naar het hint path terug.
     */
    public List<Block> getHintPath() {
        return hintPath;
    }

    /**
     * @return True als je het level in een testmodus is.
     */
    public boolean isCreateLevel() {
        return isCreateLevel;
    }


}
