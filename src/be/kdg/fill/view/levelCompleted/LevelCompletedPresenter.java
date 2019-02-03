package be.kdg.fill.view.levelCompleted;

import be.kdg.fill.model.LevelModel;
import be.kdg.fill.model.NavigationObject;
import be.kdg.fill.view.game.GamePresenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

public class LevelCompletedPresenter implements NavigationObject {
    private final LevelCompletedView view;
    private final LevelModel model;
    private final NavigationObject parent;


    public LevelCompletedPresenter(LevelCompletedView levelCompletedView, LevelModel model, NavigationObject parent) {

        this.model = model;
        this.parent = parent;
        view = levelCompletedView;
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getBtnMenu().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                returnToPreviousScreen();
            }
        });

        NavigationObject thisObject = this;
        view.getBtnNext().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((GamePresenter) parent).loadNextLevel(view);
            }
        });
    }

    @Override
    public void returnToPreviousScreen() {
        parent.backToThisScreen(view);
    }

    @Override
    public void backToThisScreen(Node node) {
    }
}