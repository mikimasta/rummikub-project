import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * language: english
 * set the number of player
 * exit game
 * 
 */
public class Setting extends Application {
 
    public void start(Stage primaryStage) {

    }

    public static void main(String[] args) {
        launch();
    }

    public Setting() {
        Stage primaryStage = new Stage();
        primaryStage.setResizable(false);

        VBox layout = new VBox(20);
        
        Color color0 = Color.rgb(121, 160, 228);
        Color colorBack = Color.rgb(240, 248, 255);
        BackgroundFill backgroundFill = new BackgroundFill(colorBack, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        layout.setBackground(background);
        layout.getChildren().addAll(); //add something
        Scene s = new Scene(layout, 400, 600);

        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(s);
        primaryStage.show();
    }
}

