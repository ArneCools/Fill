package be.kdg.fill.view.mainMenu;

import be.kdg.fill.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainMenuView extends VBox {

    private ImageView logoImage;

    private Button btnLevelEasy;
    private Button btnLevelMedium;
    private Button btnLevelHard;
    private Button btnExit;
    private Button btnLevelCreator;
    private Button btnResetProgress;

    List<Button> buttons;

    private final double BUTTON_WIDTH = 250.;
    private final double BUTTON_HEIGHT = 80.;
    private final double MARGIN_SIDE = 50.;
    private final double MARGIN_TOP = 10.;

    public MainMenuView() {
        setMinSize(800, 600);
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        getStylesheets().add("be/kdg/fill/Style/Menu.css");
        logoImage = new ImageView(new Image("/Logo.png"));
        btnLevelEasy = new Button("Easy");
        btnLevelMedium = new Button("Medium");
        btnLevelHard = new Button("Hard");
        btnExit = new Button("Exit");
        btnResetProgress = new Button("Reset all progress");
        btnLevelCreator = new Button("Create level");
        buttons = new ArrayList<Button>();
        buttons.add(btnLevelEasy);
        buttons.add(btnLevelMedium);
        buttons.add(btnLevelHard);
        buttons.add(btnLevelCreator);
        buttons.add(btnResetProgress);
        buttons.add(btnExit);

    }

    private void layoutNodes() {

        getStyleClass().add("background");
        getChildren().add(logoImage);
        getChildren().addAll(buttons);
        setAlignment(Pos.CENTER);

        for (Button button :
                buttons) {
            button.setMinSize(Main.getStage().getWidth() / 2, Main.getStage().getHeight() * .05);
            button.getStyleClass().add("Button");
            setMargin(button, new Insets(MARGIN_TOP, MARGIN_SIDE, MARGIN_TOP, MARGIN_SIDE));
        }
        setMargin(logoImage, new Insets(Main.getStage().getHeight() * 0.1, 0., Main.getStage().getHeight() * 0.5, 0.));
        setPadding(new Insets(50));

    }

    public void updateScreen() {
        for (Button button :
                buttons) {
            button.setMinSize(Main.getStage().getWidth() / 2, Main.getStage().getHeight() * 0.05);
        }
    }

    public Button getBtnLevelEasy() {
        return btnLevelEasy;
    }

    public Button getBtnLevelMedium() {
        return btnLevelMedium;
    }

    public Button getBtnLevelHard() {
        return btnLevelHard;
    }

    public Button getBtnExit() {
        return btnExit;
    }

    public Button getBtnLevelCreator() {
        return btnLevelCreator;
    }

    public Button getBtnResetProgress() {
        return btnResetProgress;
    }
}
