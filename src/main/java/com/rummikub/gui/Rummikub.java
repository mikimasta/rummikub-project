package com.rummikub.gui;


import javafx.application.*;
import javafx.stage.Stage;

public class Rummikub extends Application {

    static Stage gameWindow;

    private static final int WIDTH = 1920;

    private static final int HEIGHT = 1080;

    @Override
    public void start(Stage primaryStage) {

        gameWindow = primaryStage;
        gameWindow.setTitle("Rummikub");
        gameWindow.setWidth(WIDTH);
        gameWindow.setHeight(HEIGHT);


        

        gameWindow.show();
    }

    public static void main(String[] args) {

        launch(args);

    }

}
