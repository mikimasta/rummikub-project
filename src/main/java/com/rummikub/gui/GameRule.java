import javafx.application.Application;
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


public class GameRule extends Application {

    Label label0 = new Label(""); //add game rule.
    public void start(Stage primaryStage) {

    }

    public static void main(String[] args) {
        launch();
    }

    public GameRule() {
        Stage primaryStage = new Stage();
        primaryStage.setResizable(false);

        VBox layout = new VBox(20);
        
        

        Color color0 = Color.rgb(121, 160, 228);
        Color colorBack = Color.rgb(240, 248, 255);
        BackgroundFill backgroundFill = new BackgroundFill(colorBack, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        label0.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 50));
        label0.setTextFill(color0);
        label0.setTranslateX(5);
        label0.setTranslateY(55);
        layout.setBackground(background);
        layout.getChildren().addAll(label0); //add something
        Scene s = new Scene(layout, 400, 600);

        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(s);
        primaryStage.show();
    }

}

