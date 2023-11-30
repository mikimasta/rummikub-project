package com.rummikub.gui;


import javafx.application.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point for the game. The main stage is created here. Contains some global variables<br>
 * used throughout the application. Extends the {@link Application} class.
 */
public class Rummikub extends Application {

    /**
     * game window instance used
     */
    static Stage gameWindow;

    static final int WIDTH = 1620;

    static final int HEIGHT = 820;

    static int xCenter = WIDTH / 2;
    static int yCenter = HEIGHT / 2;



    /**
     * Sets up the Main Menu screen.
     * @param primaryStage stage  used
     */
    @Override
    public void start(Stage primaryStage) {

        gameWindow = primaryStage;
        gameWindow.setTitle("Rummikub");
        gameWindow.setWidth(WIDTH);
        gameWindow.setHeight(HEIGHT);
        gameWindow.setFullScreen(true);
        
        MainMenu root = new MainMenu();

        Scene scene = new Scene(root, WIDTH, HEIGHT);

        gameWindow.setScene(scene);

        gameWindow.show();
    
    }

    public static void main(String[] args) {

        launch(args);

    }

}
