package be.kdg.fill.view.levelCompleted;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javax.print.FlavorException;

public class LevelCompletedView extends BorderPane {
    Button btnNext;
    Button btnMenu;
    Label label;
    HBox hBox;

    public LevelCompletedView() {
        getStylesheets().add("be/kdg/fill/Style/Menu.css");
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {

        btnNext = new Button("Next Level");
        btnMenu = new Button("Menu");
        label = new Label("Level completed");
        hBox = new HBox(btnMenu, btnNext);

    }


    private void layoutNodes() {
        this.setCenter(label);
        this.setBottom(hBox);
        label.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);

        label.setPadding(new Insets(10));
        hBox.setPadding(new Insets(10));

        btnNext.setMinSize(50, 50);
        btnMenu.setMinSize(50, 50);
        HBox.setMargin(btnMenu, new Insets(0, 10, 0, 10));
        HBox.setMargin(btnNext, new Insets(0, 10, 0, 10));
        setMargin(hBox, new Insets(0, 0, 10, 0));
        btnNext.getStyleClass().add("button");
        btnMenu.getStyleClass().add("button");
        label.getStyleClass().add("levelCompleted");
    }

    public Button getBtnNext() {
        return btnNext;
    }

    public Button getBtnMenu() {
        return btnMenu;
    }
}