package be.kdg.fill.model;

public enum Difficulty {
    easy(3, 4), medium(4, 5), hard(5, 6);

    private final int width;
    private final int height;

    Difficulty(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return name();
    }
}
