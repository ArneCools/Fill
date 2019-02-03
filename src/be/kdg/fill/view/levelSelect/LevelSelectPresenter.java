package be.kdg.fill.view.levelSelect;

import be.kdg.fill.model.Game;
import be.kdg.fill.model.LevelModel;
import be.kdg.fill.model.NavigationObject;
import be.kdg.fill.view.game.GamePresenter;
import be.kdg.fill.view.game.GameView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Node;

public class LevelSelectPresenter implements NavigationObject {
    private final LevelSelectView view;
    private final LevelModel model;
    private final NavigationObject parent;

    public LevelSelectPresenter(LevelSelectView levelSelectView, LevelModel model, NavigationObject parent) {
        this.view = levelSelectView;
        this.model = model;
        this.parent = parent;
        updateView();
        addEventHandlers();

    }

    private void addEventHandlers() {
        view.getBtnBack().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                returnToPreviousScreen();
            }
        });

        for (int i = 0; i < view.getLevelButtons().length; i++) {
            int finalI = i;
            NavigationObject thisObject = this;
            view.getLevelButtons()[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    GameView gameView = new GameView(model.getLevels().get(finalI).getGrid());
                    Game game = new Game(model.getLevels().get(finalI), false);
                    GamePresenter gamePresenter = new GamePresenter(game, gameView, parent);
                    view.getScene().setRoot(gameView);
                    gamePresenter.updateView();

                }
            });
        }
    }

    private void updateView() {
        view.setButtons(model.getLevels());
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
