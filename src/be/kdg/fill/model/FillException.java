package be.kdg.fill.model;

public class FillException extends RuntimeException {
    public FillException(String message) {
        super("A Fill exception has occured: " + message);
    }
}
