package be.kdg.fill.model;

import javafx.scene.Node;

public interface NavigationObject {
    void returnToPreviousScreen();
    void backToThisScreen(Node node);
}
