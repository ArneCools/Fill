package be.kdg.fill.view.levelCreator;

import be.kdg.fill.Main;
import be.kdg.fill.model.Grid;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LevelCreatorView extends BorderPane {
    private ImageView[][] blocks;
    private int width;
    private int height;
    private GridPane gridBlocks;
    private Text txtTitle;
    private Button btnSave;
    private Button btnBack;
    private Button btnDone;
    private HBox hboxButtons;

    private Image imageEmpty;
    private Image imageSelected;
    private Image imageSelectedConnecting;

    private final double BLOCK_SIZE = 50.;


    public LevelCreatorView(int width, int height) {
        getStylesheets().add("be/kdg/fill/Style/Menu.css");
        this.width = width;
        this.height = height;
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        imageEmpty = new Image("/EmptySquare.png", BLOCK_SIZE, BLOCK_SIZE, false, false);
        imageSelected = new Image("/SelectedSquare.png", BLOCK_SIZE, BLOCK_SIZE, false, false);
        imageSelectedConnecting = new Image("/SelectedSquareConnecting.png", BLOCK_SIZE, BLOCK_SIZE, false, false);

        txtTitle = new Text("Create new level");
        txtTitle.getStyleClass().add("createLevel");
        gridBlocks = new GridPane();
        blocks = new ImageView[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                blocks[i][j] = new ImageView();
            }
        }
        btnSave = new Button("Save level");
        btnBack = new Button("Back");
        btnDone = new Button("Test level");
        hboxButtons = new HBox();
        btnDone.setDisable(true);
    }

    private void layoutNodes() {
        hboxButtons.getChildren().addAll(btnBack, btnSave, btnDone);
        setCenter(gridBlocks);
        setTop(txtTitle);
        setBottom(hboxButtons);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                gridBlocks.add(blocks[i][j], i, j);
            }
        }
        setAlignment(hboxButtons, Pos.CENTER);
        hboxButtons.setAlignment(Pos.CENTER);
        setAlignment(txtTitle, Pos.CENTER);
        gridBlocks.setAlignment(Pos.CENTER);
        HBox.setMargin(btnBack, new Insets(10));
        HBox.setMargin(btnDone, new Insets(10));
        HBox.setMargin(btnSave, new Insets(10));
        setMargin(hboxButtons, new Insets(0, 0, 10, 0));
        setMargin(txtTitle, new Insets(10, 0, 0, 0));
    }

    public void updateGrid(Grid grid) {
        for (int i = 0; i < grid.getBlocks().length; i++) {
            for (int j = 0; j < grid.getBlocks()[0].length; j++) {
                switch (grid.getBlocks()[i][j].getBlockState()) {
                    case unused:
                        this.blocks[i][j].setOpacity(0.);
                        break;
                    case empty:
                        this.blocks[i][j].setImage(imageEmpty);
                        this.blocks[i][j].setOpacity(1.);
                        break;
                    case selected:
                        this.blocks[i][j].setImage(imageSelected);
                        this.blocks[i][j].setOpacity(1.);
                        break;
                    case startblock:
                        this.blocks[i][j].setImage(imageSelected);
                        this.blocks[i][j].setOpacity(1.);
                        break;
                }
            }
        }
    }


    public void updateScreen() {
        //Make blocks responsive according to screen size
        double size = Math.min(Main.getStage().getWidth() * 0.7 / blocks.length, Main.getStage().getHeight() * 0.7 / blocks[0].length);
        size = Math.max(70, size);
        if (size > 190) {
            size = 190;
        }
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                blocks[i][j].setFitWidth(size);
                blocks[i][j].setFitHeight(size);
            }
        }
    }

    public ImageView[][] getBlocks() {
        return blocks;
    }

    public Button getBtnSave() {
        return btnSave;
    }

    public Button getBtnBack() {
        return btnBack;
    }

    public Button getBtnDone() {
        return btnDone;
    }
}
