package be.kdg.fill;

import be.kdg.fill.model.LevelData;
import be.kdg.fill.view.mainMenu.MainMenuPresenter;
import be.kdg.fill.view.mainMenu.MainMenuView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        setStage(primaryStage);

        MainMenuView menu = new MainMenuView();
        MainMenuPresenter presenter = new MainMenuPresenter(menu);
        Scene scene = new Scene(menu);
        primaryStage.setTitle("Fill");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1000);
        menu.updateScreen();
        LevelData.loadLevels();
        primaryStage.getIcons().add(new Image("/SelectedSquare.png"));

    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Main.stage = stage;
    }
}
