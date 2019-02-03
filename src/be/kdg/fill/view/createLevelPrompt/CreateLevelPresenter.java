package be.kdg.fill.view.createLevelPrompt;

import be.kdg.fill.model.*;
import be.kdg.fill.view.levelCreator.LevelCreatorPresenter;
import be.kdg.fill.view.levelCreator.LevelCreatorView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Node;

public class CreateLevelPresenter implements NavigationObject {
    private final NavigationObject parent;
    private CreateLevelView view;

    public CreateLevelPresenter(CreateLevelView view, NavigationObject parent) {
        this.view = view;
        this.parent = parent;
        addEventHandlers();
    }

    private void addEventHandlers() {
        NavigationObject thisObject = this;
        view.getBtnCreate().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final int width = (int) view.getSliderWidth().getValue();
                final int height = (int) view.getSliderHeight().getValue();
                final Difficulty difficulty = Difficulty.values()[(int) view.getSliderDifficulty().getValue()];
                Level levelModel = new Level(width, height, difficulty, LevelData.getLevels(difficulty).size() + 1);
                LevelCreatorView levelCreatorView = new LevelCreatorView(width, height);
                LevelCreatorPresenter levelCreatorPresenter = new LevelCreatorPresenter(levelCreatorView, levelModel, thisObject);
                view.getScene().setRoot(levelCreatorView);
            }
        });

        view.getBtnBack().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                returnToPreviousScreen();
            }
        });

        view.getSliderWidth().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                view.getSliderWidth().setValue(newValue.intValue());
            }
        });

        view.getSliderHeight().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                view.getSliderHeight().setValue(newValue.intValue());
            }
        });

        view.getSliderDifficulty().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                view.getSliderDifficulty().setValue(newValue.intValue());
            }
        });
    }

    @Override
    public void returnToPreviousScreen() {
        parent.backToThisScreen(view);
    }

    @Override
    public void backToThisScreen(Node node) {
        node.getScene().setRoot(view);
    }
}
