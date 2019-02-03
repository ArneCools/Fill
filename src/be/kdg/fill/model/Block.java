package be.kdg.fill.model;

import java.io.Serializable;

public class Block implements Serializable {
    private int row;
    private int column;
    private BlockState blockState;
    private boolean hint = false;

    public Block(int column, int row) {
        this.row = row;
        this.column = column;
        this.blockState = BlockState.empty;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public void setBlockState(BlockState blockState) {
        if (blockState == null) {
            throw new RuntimeException("Set blockstate to empty.");
        }
        this.blockState = blockState;
    }

    public void nextBlockState(boolean isStartBlockIncluded) {
        //used for creating levels
        switch (blockState) {
            case unused:
                blockState = BlockState.empty;
                break;
            case empty:
                if (isStartBlockIncluded) blockState = BlockState.startblock;
                else blockState = BlockState.unused;
                break;
            case selected:
                blockState = BlockState.empty;
                break;
            case startblock:
                blockState = BlockState.unused;
                break;
        }
    }
}
