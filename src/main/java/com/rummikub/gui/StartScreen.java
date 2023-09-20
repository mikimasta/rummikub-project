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


public class StartScreen extends Application {

    public void start(Stage primaryStage) {

    }

    public static void main(String[] args) {
        launch();
    }

    public StartScreen() {
        Stage primaryStage = new Stage();
        primaryStage.setResizable(false);

        VBox layout = new VBox(20);

        Button hvh = new Button("human vs human");
        hvh.setStyle("-fx-font-size:20");
        hvh.setFont(new Font(20));
        hvh.setTranslateX(100);
        hvh.setTranslateY(150);
        hvh.setPrefHeight(40);
        hvh.setPrefWidth(200);

        Button hva = new Button("human vs AI");
        hva.setStyle("-fx-font-size:20");
        hva.setFont(new Font(20));
        hva.setTranslateX(100);
        hva.setTranslateY(150);
        hva.setPrefHeight(40);
        hva.setPrefWidth(200);

        Button b = new Button("Game rule");
        b.setStyle("-fx-font-size:20");
        b.setFont(new Font(20));
        b.setTranslateX(100);
        b.setTranslateY(150);
        b.setPrefHeight(40);
        b.setPrefWidth(200);

        Button se = new Button("Setting");
        se.setStyle("-fx-font-size:20");
        se.setFont(new Font(20));
        se.setTranslateX(100);
        se.setTranslateY(150);
        se.setPrefHeight(40);
        se.setPrefWidth(200);

        Button exit = new Button("Exit");
        exit.setStyle("-fx-font-size:20");
        exit.setFont(new Font(20));
        exit.setTranslateX(100);
        exit.setTranslateY(150);
        exit.setPrefHeight(40);
        exit.setPrefWidth(200);

        hvh.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                GameScreen gs = new GameScreen();
            
            }
        });

        b.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                GameRule gr = new GameRule();
            
            }
        });

        se.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Setting set = new Setting();
                primaryStage.close();
            }
        });

        exit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                primaryStage.close();
            
            }
        });

        Color color0 = Color.rgb(121, 160, 228);
        Color colorBack = Color.rgb(240, 248, 255);
        BackgroundFill backgroundFill = new BackgroundFill(colorBack, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        layout.getChildren().addAll(hvh, hva, b, se,exit);
        Scene s = new Scene(layout, 400, 600);

        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(s);
        primaryStage.show();
    }

}

