package be.kdg.fill.view.game;

import be.kdg.fill.model.*;
import be.kdg.fill.view.levelCompleted.LevelCompletedPresenter;
import be.kdg.fill.view.levelCompleted.LevelCompletedView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

import javafx.scene.Node;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GamePresenter implements NavigationObject {
    private Game model;
    private GameView view;
    private boolean mouseDown = false;
    private final NavigationObject parent;

    public GamePresenter(Game model, GameView view, NavigationObject parent) {
        this.model = model;
        this.view = view;
        this.parent = parent;

        if (model.isCreateLevel()) {
            view.getBtnHint().setDisable(true);
            view.getBtnExit().setText("Back");
        }
        addEventHandlers();
    }

    private void addEventHandlers() {

        for (int i = 0; i < view.getBlocks().length; i++) {
            for (int j = 0; j < view.getBlocks()[0].length; j++) {
                int finalJ = j;
                int finalI = i;

                view.addEventFilter(MouseEvent.DRAG_DETECTED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        view.startFullDrag();
                    }
                });

                view.getBlocks()[i][j].setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
                    @Override
                    public void handle(MouseDragEvent event) {
                        model.handleDrag(finalI, finalJ);
                        updateView();
                        if (model.checkIfWon()) gameWon();
                    }
                });

                view.getBlocks()[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        model.handleDrag(finalI, finalJ);
                        updateView();
                        if (model.checkIfWon()) gameWon();
                    }
                });

            }
        }

        view.getBtnHint().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (LevelData.getHintsAvailable() > 0) {
                    LevelData.useHint();
                    model.showHint();
                    view.updateHints();
                    updateView();
                }
            }
        });

        view.getBtnExit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.resetGrid();
                returnWithTransition();
            }
        });

        view.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                view.updateScreen();
            }
        });

        view.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                view.updateScreen();
            }
        });

    }

    public void updateView() {

        view.updateGrid(model.getGrid());
        view.updatePath(model.getPath(), model.getHintPath());
        view.updateScreen();
    }

    @Override
    public void returnToPreviousScreen() {
        parent.backToThisScreen(view);
    }

    @Override
    public void backToThisScreen(Node node) {
        parent.backToThisScreen(node);
    }

    private void gameWon() {
        if (model.isCreateLevel()) {
            returnWithTransition();
            return;
        }

        view.transition(false);
        view.getTransition().setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                endLevel();
            }
        });
    }

    private void endLevel() {
        model.getLevel().setCompleted();
        LevelCompletedView levelCompletedView = new LevelCompletedView();
        LevelModel levelModel = new LevelModel(model.getLevel().getDifficulty());
        LevelCompletedPresenter levelCompletedPresenter = new LevelCompletedPresenter(levelCompletedView, levelModel, this);
        view.getScene().setRoot(levelCompletedView);
    }

    private void returnWithTransition() {
        view.transition(false);
        view.getTransition().setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                returnToPreviousScreen();
            }
        });
    }

    public void loadNextLevel(Node node) {

        if (model.getLevel().getLevelNumber() < LevelData.getLevels(model.getLevel().getDifficulty()).size()) {
            GameView gameView = new GameView(LevelData.getLevels(model.getLevel().getDifficulty()).get(model.getLevel().getLevelNumber()).getGrid());
            Game game = new Game(LevelData.getLevels(model.getLevel().getDifficulty()).get(model.getLevel().getLevelNumber()), false);
            GamePresenter gamePresenter = new GamePresenter(game, gameView, this);
            node.getScene().setRoot(gameView);
        } else {
            backToThisScreen(node);
        }

    }
}
