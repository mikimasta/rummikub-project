package com.rummikub.gui;


import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Rummikub extends Application {

    /**
     * game window instance used
     */
    static Stage gameWindow;

    private static final int WIDTH = 1920;

    private static final int HEIGHT = 1080;

    private static int xCenter = WIDTH / 2;
    private static int yCenter = HEIGHT / 2;

    @Override
    public void start(Stage primaryStage) {

        gameWindow = primaryStage;
        gameWindow.setTitle("Rummikub");
        gameWindow.setWidth(WIDTH);
        gameWindow.setHeight(HEIGHT);
        

        Pane root = new Pane();

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        gameWindow.setScene(scene);

        Line xCenterLine, yCenterLine;

        xCenterLine = new Line(0, HEIGHT / 2, WIDTH, HEIGHT / 2);
        yCenterLine = new Line(WIDTH / 2, 0, WIDTH / 2, HEIGHT);


        // start button
        Button start = new Button("Start");
        start.setPrefSize(400, 50);
        //start.setBackground(null);
        start.setLayoutX(xCenter - 200);
        start.setLayoutY(yCenter - 50);
        start.setStyle("-fx-font-size: 60");
        start.setOnAction(e -> {
            
            System.out.println("test");

        });
        
        

        //Color.web("#4C516D" - blue shade)
        root.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, null, null)));
        root.getStylesheets().add("styling.css");


        ImageView rummikubLogoIV = new ImageView(Images.rummikubLogo);
        rummikubLogoIV.setX(100);
        rummikubLogoIV.setY(100);
        rummikubLogoIV.setPreserveRatio(true);
        rummikubLogoIV.setFitWidth(600);

        root.getChildren().addAll(rummikubLogoIV, start, xCenterLine, yCenterLine);


        

        gameWindow.show();
    }

    public static void main(String[] args) {

        launch(args);

    }

}
