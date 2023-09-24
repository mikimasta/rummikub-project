package com.rummikub.gui;


import javafx.application.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Rummikub extends Application {

    /**
     * game window instance used
     */
    static Stage gameWindow;

    private static final int WIDTH = 1920;

    private static final int HEIGHT = 1080;

    static int xCenter = WIDTH / 2;
    static int yCenter = HEIGHT / 2;



    @Override
    public void start(Stage primaryStage) {

        gameWindow = primaryStage;
        gameWindow.setTitle("Rummikub");
        gameWindow.setWidth(WIDTH);
        gameWindow.setHeight(HEIGHT);
        //gameWindow.setFullScreen(true);
        
        MainMenu root = new MainMenu();

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        gameWindow.setScene(scene);

        gameWindow.show();
    
    }

    public static void main(String[] args) {

        launch(args);

    }

}