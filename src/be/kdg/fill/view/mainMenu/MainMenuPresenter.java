package be.kdg.fill.view.mainMenu;

import be.kdg.fill.model.Difficulty;
import be.kdg.fill.model.LevelData;
import be.kdg.fill.model.LevelModel;
import be.kdg.fill.model.NavigationObject;
import be.kdg.fill.view.createLevelPrompt.CreateLevelPresenter;
import be.kdg.fill.view.createLevelPrompt.CreateLevelView;
import be.kdg.fill.view.levelSelect.LevelSelectPresenter;
import be.kdg.fill.view.levelSelect.LevelSelectView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainMenuPresenter implements NavigationObject {
    private final MainMenuView view;

    public MainMenuPresenter(MainMenuView view) {
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers() {
        NavigationObject thisObject = this;
        view.getBtnExit().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        view.getBtnLevelEasy().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadLevelSelect(Difficulty.easy);
            }
        });

        view.getBtnLevelMedium().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadLevelSelect(Difficulty.medium);
            }
        });

        view.getBtnLevelHard().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadLevelSelect(Difficulty.hard);
            }
        });

        view.getBtnLevelCreator().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CreateLevelView createLevelView = new CreateLevelView();
                CreateLevelPresenter createLevelPresenter = new CreateLevelPresenter(createLevelView, thisObject);
                view.getScene().setRoot(createLevelView);
            }
        });

        view.getBtnResetProgress().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("All your ingame progress will be erased!");
                alert.setContentText("Are you sure you want to do this?");
                alert.setTitle("Caution");
                alert.getButtonTypes().clear();
                ButtonType yes = new ButtonType("Yes");
                ButtonType no = new ButtonType("No");
                alert.getButtonTypes().addAll(yes, no);
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("/SelectedSquare.png"));
                alert.showAndWait();
                if (alert.getResult().equals(yes)) {
                    LevelData.resetPlayerProgress();
                } else {
                    alert.close();
                }
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

    private void loadLevelSelect(Difficulty difficulty) {
        LevelSelectView levelSelectView = new LevelSelectView();
        LevelModel levelModel = new LevelModel(difficulty);
        LevelSelectPresenter levelSelectPresenter = new LevelSelectPresenter(levelSelectView, levelModel, this);
        view.getScene().setRoot(levelSelectView);
    }

    @Override
    public void returnToPreviousScreen() {

    }

    @Override
    public void backToThisScreen(Node node) {
        node.getScene().setRoot(view);
    }
}
