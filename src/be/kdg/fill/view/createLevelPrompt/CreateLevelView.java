package be.kdg.fill.view.createLevelPrompt;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import javax.swing.text.StyledEditorKit;


public class CreateLevelView extends VBox {

    private Text txttitle;
    private Text txtWidth;
    private Text txtheight;
    private Text txtDifficulty;
    private Button btnCreate;
    private Button btnBack;
    private Slider sliderWidth;
    private Slider sliderHeight;
    private Slider sliderDifficulty;

    private HBox hboxWidth;
    private HBox hboxHeight;
    private HBox hboxDifficulty;
    private HBox hboxButtons;


    public CreateLevelView() {
        getStylesheets().add("be/kdg/fill/Style/Menu.css");
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        txttitle = new Text("Create a new level");
        txtWidth = new Text("Level width");
        txtheight = new Text("Level height");
        txtDifficulty = new Text("Difficulty");
        btnCreate = new Button("Create");
        btnBack = new Button("Back");
        sliderWidth = new Slider();
        sliderHeight = new Slider();
        sliderDifficulty = new Slider(0, 2, 0);

        sliderWidth.setMin(3);
        sliderWidth.setMax(7);
        sliderWidth.setValue(2);
        sliderWidth.setShowTickLabels(true);
        sliderWidth.setShowTickMarks(true);
        sliderWidth.setSnapToTicks(true);
        sliderWidth.setMajorTickUnit(1);
        sliderWidth.setMinorTickCount(0);


        sliderHeight.setMin(3);
        sliderHeight.setMax(7);
        sliderHeight.setValue(2);
        sliderHeight.setShowTickLabels(true);
        sliderHeight.setShowTickMarks(true);
        sliderHeight.setSnapToTicks(true);
        sliderHeight.setMajorTickUnit(1);
        sliderHeight.setMinorTickCount(0);


        sliderDifficulty.setMin(0);
        sliderDifficulty.setMax(2);
        sliderDifficulty.setValue(1);
        sliderDifficulty.setMinorTickCount(0);
        sliderDifficulty.setMajorTickUnit(1);
        sliderDifficulty.setSnapToTicks(true);
        sliderDifficulty.setShowTickMarks(true);
        sliderDifficulty.setShowTickLabels(true);
        sliderDifficulty.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                if (object < 0.5) return "Easy";
                if (object < 1.5) return "Medium";

                return "Hard";
            }
            @Override
            public Double fromString(String string) {
                switch (string) {
                    case "Easy":
                        return 0.;
                    case "Medium":
                        return 1.;
                    case "Hard":
                        return 2.;
                    default:
                        return 2.;
                }
            }
        });

        hboxWidth = new HBox();
        hboxHeight = new HBox();
        hboxDifficulty = new HBox();
        hboxButtons = new HBox();

    }

    private void layoutNodes() {
        hboxHeight.getChildren().addAll(txtheight, sliderHeight);
        hboxWidth.getChildren().addAll(txtWidth, sliderWidth);
        hboxDifficulty.getChildren().addAll(txtDifficulty, sliderDifficulty);
        hboxButtons.getChildren().addAll(btnBack, btnCreate);
        getChildren().addAll(txttitle, hboxWidth, hboxHeight, hboxDifficulty, hboxButtons);

        setAlignment(Pos.CENTER);
        hboxHeight.setAlignment(Pos.CENTER);
        hboxWidth.setAlignment(Pos.CENTER);
        hboxDifficulty.setAlignment(Pos.CENTER);
        hboxButtons.setAlignment(Pos.CENTER);

        btnBack.getStyleClass().add("button");
        btnCreate.getStyleClass().add("button");
        txttitle.getStyleClass().add("createLevel");
        setMargin(txttitle, new Insets(50));
        setMargin(hboxHeight, new Insets(10));
        setMargin(hboxWidth, new Insets(10));
        setMargin(hboxDifficulty, new Insets(10));
        setMargin(hboxButtons, new Insets(10));
        hboxButtons.setMargin(btnBack, new Insets(0, 10, 0, 0));
        hboxWidth.setMargin(txtWidth, new Insets(0, 10, 0, 0));
        hboxHeight.setMargin(txtheight, new Insets(0, 10, 0, 0));
        hboxDifficulty.setMargin(txtDifficulty, new Insets(0, 10, 0, 0));

    }

    public Button getBtnCreate() {
        return btnCreate;
    }

    public Slider getSliderWidth() {
        return sliderWidth;
    }

    public Slider getSliderHeight() {
        return sliderHeight;
    }

    public Slider getSliderDifficulty() {
        return sliderDifficulty;
    }

    public Button getBtnBack() {
        return btnBack;
    }
}
