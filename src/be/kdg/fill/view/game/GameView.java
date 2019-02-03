package be.kdg.fill.view.game;

import be.kdg.fill.Main;
import be.kdg.fill.model.Block;
import be.kdg.fill.model.Grid;
import be.kdg.fill.model.LevelData;
import javafx.animation.ScaleTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import java.util.List;


public class GameView extends BorderPane {
    private ImageView[][] blocks;
    private Button btnHint;
    private Button btnExit;
    private GridPane gridBLocks;
    private HBox hboxButtons;
    private Label txtHints;

    private Image imageEmpty;
    private Image imageSelected;
    private Image imageSelectedConnecting;
    private Image imageSelectedConnectingBoth;
    private Image imageSelectedConnectingTurn;

    private Image imageSelectedHint;
    private Image imageSelectedConnectingHint;
    private Image imageSelectedConnectingBothHint;
    private Image imageSelectedConnectingTurnHint;

    ScaleTransition transition;


    private final double BLOCK_SIZE = 100.;

    public GameView(Grid grid) {
        getStylesheets().add("be/kdg/fill/Style/Menu.css");
        initialiseNodes(grid);
        updateGrid(grid);
        layoutNodes();
        updateScreen();

    }

    private void initialiseNodes(Grid grid) {
        imageEmpty = new Image("/EmptySquare.png", BLOCK_SIZE, BLOCK_SIZE, false, false);
        imageSelected = new Image("/SelectedSquare.png", BLOCK_SIZE, BLOCK_SIZE, false, false);
        imageSelectedConnecting = new Image("/SelectedSquareConnecting.png", BLOCK_SIZE, BLOCK_SIZE, false, false);
        imageSelectedConnectingBoth = new Image("/SelectedSquareBoth.png", BLOCK_SIZE, BLOCK_SIZE, false, false);
        imageSelectedConnectingTurn = new Image("/SelectedSquareCurved.png", BLOCK_SIZE, BLOCK_SIZE, false, false);
        imageSelectedHint = new Image("/SelectedSquareHint.png", BLOCK_SIZE, BLOCK_SIZE, false, false);
        imageSelectedConnectingHint = new Image("/SelectedSquareConnectingHint.png", BLOCK_SIZE, BLOCK_SIZE, false, false);
        imageSelectedConnectingBothHint = new Image("/SelectedSquareBothHint.png", BLOCK_SIZE, BLOCK_SIZE, false, false);
        imageSelectedConnectingTurnHint = new Image("/SelectedSquareCurvedHint.png", BLOCK_SIZE, BLOCK_SIZE, false, false);

        btnHint = new Button("Hint");
        btnExit = new Button("Exit");
        btnHint.getStyleClass().add("button");
        btnExit.getStyleClass().add("button");
        hboxButtons = new HBox();
        txtHints = new Label("Available hints: []");
        txtHints.getStyleClass().add("hintText");


        this.gridBLocks = new GridPane();


        blocks = new ImageView[grid.getBlocks().length][grid.getBlocks()[0].length];

        for (int i = 0; i < grid.getBlocks().length; i++) {
            for (int j = 0; j < grid.getBlocks()[0].length; j++) {
                this.blocks[i][j] = new ImageView();
            }
        }

        transition(true);


    }

    private void layoutNodes() {
        gridBLocks.setAlignment(Pos.CENTER);
        this.setCenter(gridBLocks);
        hboxButtons.setAlignment(Pos.CENTER);
        hboxButtons.getChildren().addAll(btnExit, btnHint);
        setAlignment(txtHints, Pos.CENTER);
        setMargin(txtHints, new Insets(10, 0, 0, 0));
        setBottom(hboxButtons);
        setTop(txtHints);
        for (int i = 0; i < this.blocks.length; i++) {
            for (int j = 0; j < this.blocks[i].length; j++) {
                GridPane.setHalignment(blocks[i][j], HPos.CENTER);
                this.gridBLocks.add(this.blocks[i][j], i, j);
            }
        }

        HBox.setMargin(btnExit, new Insets(10));
        HBox.setMargin(btnHint, new Insets(10));
        setMargin(hboxButtons, new Insets(0, 0, 10, 0));

        updateHints();

    }

    public void updateGrid(Grid grid) {
        for (int i = 0; i < grid.getBlocks().length; i++) {
            for (int j = 0; j < grid.getBlocks()[0].length; j++) {
                switch (grid.getBlocks()[i][j].getBlockState()) {
                    case unused:
                        break;
                    case empty:
                        this.blocks[i][j].setImage(imageEmpty);
                        break;
                    case selected:
                        this.blocks[i][j].setImage(imageSelected);
                        break;
                    case startblock:
                        this.blocks[i][j].setImage(imageSelected);
                        break;
                }
            }
        }
    }

    public void updatePath(List<Block> path, List<Block> hintPath) {

        for (int i = 0; i < hintPath.size(); i++) {
            ImageView blockImage = this.blocks[hintPath.get(i).getColumn()][hintPath.get(i).getRow()];
            Block thisBlock = hintPath.get(i);
            int connections = 0;

            Block connectingBlock1 = new Block(0, 0);
            Block connectingBlock2 = new Block(0, 0);
            if (hintPath.size() == 1) {
                return;
            }

            if (hintPath.size() == i + 1) {
                connections = 1;
                connectingBlock1 = hintPath.get(i - 1);
            } else if (i == 0) {
                connections = 1;
                connectingBlock1 = hintPath.get(i + 1);
            } else {
                connections = 2;
                connectingBlock1 = hintPath.get(i - 1);
                connectingBlock2 = hintPath.get(i + 1);
            }

            if (connections == 1) {
                //Only one connection
                if (connectingBlock1.getColumn() > thisBlock.getColumn()) {
                    blockImage.setImage(imageSelectedConnectingHint);
                    blockImage.setRotate(0.);
                } else if (connectingBlock1.getColumn() < thisBlock.getColumn()) {
                    blockImage.setImage(imageSelectedConnectingHint);
                    blockImage.setRotate(180.);
                }
                if (connectingBlock1.getRow() > thisBlock.getRow()) {
                    blockImage.setImage(imageSelectedConnectingHint);
                    blockImage.setRotate(90.);
                } else if (connectingBlock1.getRow() < thisBlock.getRow()) {
                    blockImage.setImage(imageSelectedConnectingHint);
                    blockImage.setRotate(-90.);
                }
            } else {
                //Connection on both sides

                //Straight lines:
                if (connectingBlock1.getRow() == connectingBlock2.getRow()) {
                    blockImage.setImage(imageSelectedConnectingBothHint);
                    blockImage.setRotate(0.);
                } else if (connectingBlock1.getColumn() == connectingBlock2.getColumn()) {
                    blockImage.setImage(imageSelectedConnectingBothHint);
                    blockImage.setRotate(90.);

                }

                //Curved lines

                else if ((connectingBlock1.getRow() < thisBlock.getRow() && connectingBlock2.getColumn() > thisBlock.getColumn()
                ) || (connectingBlock2.getRow() < thisBlock.getRow() && connectingBlock1.getColumn() > thisBlock.getColumn())) {
                    blockImage.setImage(imageSelectedConnectingTurnHint);
                    blockImage.setRotate(0.);
                } //top and right connection

                else if ((connectingBlock1.getColumn() > thisBlock.getColumn() && connectingBlock2.getRow() > thisBlock.getRow()
                ) || (connectingBlock2.getColumn() > thisBlock.getColumn() && connectingBlock1.getRow() > thisBlock.getRow())) {
                    blockImage.setImage(imageSelectedConnectingTurnHint);
                    blockImage.setRotate(90.);
                } //right and bottom connection

                else if ((connectingBlock1.getColumn() < thisBlock.getColumn() && connectingBlock2.getRow() > thisBlock.getRow()
                ) || (connectingBlock2.getColumn() < thisBlock.getColumn() && connectingBlock1.getRow() > thisBlock.getRow())) {
                    blockImage.setImage(imageSelectedConnectingTurnHint);
                    blockImage.setRotate(180.);
                } //bottom and left connection

                else if ((connectingBlock1.getRow() < thisBlock.getRow() && connectingBlock2.getColumn() < thisBlock.getColumn()
                ) || (connectingBlock2.getRow() < thisBlock.getRow() && connectingBlock1.getColumn() < thisBlock.getColumn())) {
                    blockImage.setImage(imageSelectedConnectingTurnHint);
                    blockImage.setRotate(270.);
                } //left and top connection


            }
        }

        for (int i = 0; i < path.size(); i++) {
            ImageView blockImage = this.blocks[path.get(i).getColumn()][path.get(i).getRow()];
            Block thisBlock = path.get(i);
            int connections = 0;

            Block connectingBlock1 = new Block(0, 0);
            Block connectingBlock2 = new Block(0, 0);
            if (path.size() == 1) {
                blockImage.setImage(imageSelected);
                return;
            }

            if (path.size() == i + 1) {
                connections = 1;
                connectingBlock1 = path.get(i - 1);
            } else if (i == 0) {
                connections = 1;
                connectingBlock1 = path.get(i + 1);
            } else {
                connections = 2;
                connectingBlock1 = path.get(i - 1);
                connectingBlock2 = path.get(i + 1);
            }

            if (connections == 1) {
                //Only one connection
                if (connectingBlock1.getColumn() > thisBlock.getColumn()) {
                    blockImage.setImage(imageSelectedConnecting);
                    blockImage.setRotate(0.);
                } else if (connectingBlock1.getColumn() < thisBlock.getColumn()) {
                    blockImage.setImage(imageSelectedConnecting);
                    blockImage.setRotate(180.);
                }
                if (connectingBlock1.getRow() > thisBlock.getRow()) {
                    blockImage.setImage(imageSelectedConnecting);
                    blockImage.setRotate(90.);
                } else if (connectingBlock1.getRow() < thisBlock.getRow()) {
                    blockImage.setImage(imageSelectedConnecting);
                    blockImage.setRotate(-90.);
                }
            } else {
                //Connection on both sides

                //Straight lines:
                if (connectingBlock1.getRow() == connectingBlock2.getRow()) {
                    blockImage.setImage(imageSelectedConnectingBoth);
                    blockImage.setRotate(0.);
                } else if (connectingBlock1.getColumn() == connectingBlock2.getColumn()) {
                    blockImage.setImage(imageSelectedConnectingBoth);
                    blockImage.setRotate(90.);

                }

                //Curved lines

                else if ((connectingBlock1.getRow() < thisBlock.getRow() && connectingBlock2.getColumn() > thisBlock.getColumn()
                ) || (connectingBlock2.getRow() < thisBlock.getRow() && connectingBlock1.getColumn() > thisBlock.getColumn())) {
                    blockImage.setImage(imageSelectedConnectingTurn);
                    blockImage.setRotate(0.);
                } //top and right connection

                else if ((connectingBlock1.getColumn() > thisBlock.getColumn() && connectingBlock2.getRow() > thisBlock.getRow()
                ) || (connectingBlock2.getColumn() > thisBlock.getColumn() && connectingBlock1.getRow() > thisBlock.getRow())) {
                    blockImage.setImage(imageSelectedConnectingTurn);
                    blockImage.setRotate(90.);
                } //right and bottom connection

                else if ((connectingBlock1.getColumn() < thisBlock.getColumn() && connectingBlock2.getRow() > thisBlock.getRow()
                ) || (connectingBlock2.getColumn() < thisBlock.getColumn() && connectingBlock1.getRow() > thisBlock.getRow())) {
                    blockImage.setImage(imageSelectedConnectingTurn);
                    blockImage.setRotate(180.);
                } //bottom and left connection

                else if ((connectingBlock1.getRow() < thisBlock.getRow() && connectingBlock2.getColumn() < thisBlock.getColumn()
                ) || (connectingBlock2.getRow() < thisBlock.getRow() && connectingBlock1.getColumn() < thisBlock.getColumn())) {
                    blockImage.setImage(imageSelectedConnectingTurn);
                    blockImage.setRotate(270.);
                } //left and top connection


            }


        }
    }

    public void updateScreen() {
        //Make blocks responsive according to screen size
        double size = Math.min(Main.getStage().getWidth() * 0.8 / blocks.length, Main.getStage().getHeight() * 0.8 / blocks[0].length);
        size = Math.max(100, size);
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


    public void transition(Boolean isTranstionIn) {

        double in = 0.;
        double out = 1.;
        if (!isTranstionIn) {
            in = 1.;
            out = 0.;
        }
        for (ImageView[] blockArr :
                blocks) {
            for (ImageView block :
                    blockArr) {
                transition = new ScaleTransition();
                transition.setNode(block);
                transition.setDuration(new Duration(500));
                transition.setFromX(in);
                transition.setFromY(in);
                transition.setToX(out);
                transition.setToY(out);
                transition.setCycleCount(1);
                transition.play();

            }
        }
    }

    public void updateHints() {
        if (LevelData.getHintsAvailable() < 1) {
            txtHints.setText("Get more hints by completing levels!");
            btnHint.setDisable(true);
        } else {
            txtHints.setText("Available hints: " + LevelData.getHintsAvailable());
        }
    }

    public Button getBtnHint() {
        return btnHint;
    }

    public Button getBtnExit() {
        return btnExit;
    }

    public ScaleTransition getTransition() {
        return transition;
    }

    public ImageView[][] getBlocks() {
        return blocks;
    }


}
