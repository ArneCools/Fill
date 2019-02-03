package be.kdg.fill.view.levelCreator;

import be.kdg.fill.model.*;
import be.kdg.fill.view.game.GamePresenter;
import be.kdg.fill.view.game.GameView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LevelCreatorPresenter implements NavigationObject {
    private LevelCreatorView view;
    private Level model;
    private NavigationObject parent;
    private boolean isStarBlockSet = false;

    public LevelCreatorPresenter(LevelCreatorView view,Level model,NavigationObject parent) {
        this.view = view;
        this.model = model;
        this.parent = parent;
        addEventHandlers();
        updateView();
    }

    private void addEventHandlers() {
        NavigationObject thisObject = this;
        for (int i = 0; i < view.getBlocks().length; i++) {
            for (int j = 0; j < view.getBlocks()[0].length; j++) {
                int finalI = i;
                int finalJ = j;
                view.getBlocks()[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (model.getGrid().getBlocks()[finalI][finalJ].getBlockState() == BlockState.startblock){
                            isStarBlockSet = false;
                            view.getBtnDone().setDisable(true);
                        }
                        model.getGrid().getBlocks()[finalI][finalJ].nextBlockState(!isStarBlockSet);
                        updateView();
                        if (model.getGrid().getBlocks()[finalI][finalJ].getBlockState() == BlockState.startblock){
                            isStarBlockSet = true;
                            view.getBtnDone().setDisable(false);
                        }
                    }
                });
            }
        }
        view.getBtnSave().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Your level will be saved and you will be returned to the main menu!");
                alert.setContentText("Are you done?");
                alert.setTitle("Save level");
                alert.getButtonTypes().clear();
                ButtonType yes = new ButtonType("Yes");
                ButtonType no = new ButtonType("No");
                alert.getButtonTypes().addAll(yes, no);
                alert.showAndWait();
                if (alert.getResult().equals(yes)) {
                    try {
                        LevelData.saveLevel(model);
                        returnToPreviousScreen();
                    } catch (IOException e) {
                        throw new FillException("Error saving level: " + e.getMessage());
                    }
                }else{
                    alert.close();
                }
            }
        });

        view.getBtnBack().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                returnToPreviousScreen();
            }
        });

        view.getBtnDone().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!isStarBlockSet) return;
                GameView gameView = new GameView(model.getGrid());
                Game game = new Game(model,true);
                GamePresenter gamePresenter = new GamePresenter(game, gameView,thisObject);
                view.getScene().setRoot(gameView);
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

    private void updateView() {
        view.updateGrid(model.getGrid());
        view.getBtnSave().setDisable(!model.isSolvable());
    }

    @Override
    public void returnToPreviousScreen() {
        parent.backToThisScreen(view);
    }

    @Override
    public void backToThisScreen(Node node) {
        node.getScene().setRoot(view);
        updateView();
    }
}



