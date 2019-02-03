package be.kdg.fill.view.levelSelect;

import be.kdg.fill.model.Level;
import be.kdg.fill.model.LevelData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.List;

public class LevelSelectView extends BorderPane {
    private Text title;
    private Button[] levelButtons;
    private Button btnBack;
    private GridPane gridLevels;

    public LevelSelectView() {
        getStylesheets().add("be/kdg/fill/Style/Menu.css");
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        title = new Text("Select level");
        btnBack = new Button("Back");
        gridLevels = new GridPane();
        levelButtons = new Button[0];
    }

    private void layoutNodes() {
        btnBack.getStyleClass().add("button");
        title.getStyleClass().add("selectLevel");
        setTop(title);
        setCenter(gridLevels);
        setBottom(btnBack);
        gridLevels.setAlignment(Pos.CENTER);
        setAlignment(btnBack, Pos.CENTER);
        setAlignment(title, Pos.CENTER);

        setMargin(btnBack, new Insets(20));
        setMargin(title, new Insets(50));
    }


    public Text getTitle() {
        return title;
    }

    public Button[] getLevelButtons() {
        return levelButtons;
    }

    public Button getBtnBack() {
        return btnBack;
    }

    public GridPane getGridLevels() {
        return gridLevels;
    }

    public void setButtons(List<Level> levels) {

        levelButtons = new Button[levels.size()];
        int iterator = 0;
        int column = 0;
        int row = 0;
        for (Level level : levels) {
            if (column > 4) {
                row++;
                column = 0;
            }
            levelButtons[iterator] = new Button(Integer.toString(level.getLevelNumber()));
            gridLevels.add(levelButtons[iterator], column, row);
            GridPane.setMargin(levelButtons[iterator], new Insets(5));
            if (LevelData.getPlayerProgress(level.getDifficulty()) < level.getLevelNumber() - 1)
                levelButtons[iterator].setDisable(true);
            iterator++;
            column++;
        }
    }
}
