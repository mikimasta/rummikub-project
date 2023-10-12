package com.rummikub.gui;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;

class GameScreen extends Pane {

    GameScreen() {


        // sets for the grid pane settings

        setBackground(new Background(new BackgroundFill(Color.web("#4A6B3C"), null, null)));
        getStylesheets().add("styling.css");
        //setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

        RackGUI rack = new RackGUI();

        TileGUI test = new TileGUI((byte) 14, Color.BLUE);
        test.setLayoutX(500);
        test.setLayoutY(300);
        test.toFront();

        GridPane players = new GridPane();

        GridPane gameBoard = new GridPane();

        Rectangle gb = new Rectangle(320, 90, 1280, 720);
        gb.setFill(Color.TRANSPARENT);
        gb.setStroke(Color.BLACK);



        ImageView rackIV = new ImageView(Images.rack);
        rackIV.setPreserveRatio(true);
        rackIV.setFitWidth(800);
        rackIV.setLayoutX(Rummikub.xCenter - 400);
        rackIV.setLayoutY(880);

        getChildren().addAll(rack, players, gameBoard, gb, rackIV, test);

    }





















}
