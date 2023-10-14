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
        rack.addToRack(new TileGUI((byte) 4, Color.RED));
        rack.addToRack(new TileGUI((byte) 10, Color.ORANGE));
        rack.addToRack(new TileGUI((byte) 7, Color.BLACK));
        rack.addToRack(new TileGUI((byte) 2, Color.BLUE));
        rack.addToRack(new TileGUI((byte) 9, Color.RED));
        rack.addToRack(new TileGUI((byte) 6, Color.ORANGE));
        rack.addToRack(new TileGUI((byte) 12, Color.BLACK));
        rack.addToRack(new TileGUI((byte) 1, Color.BLUE));
        rack.addToRack(new TileGUI((byte) 5, Color.RED));
        rack.addToRack(new TileGUI((byte) 8, Color.ORANGE));
        rack.addToRack(new TileGUI((byte) 13, Color.BLACK));
        rack.addToRack(new TileGUI((byte) 3, Color.BLUE));
        rack.addToRack(new TileGUI((byte) 11, Color.ORANGE));
        rack.addToRack(new TileGUI(Color.RED));
        rack.addToRack(new TileGUI(Color.BLACK));

        rack.getChildren().remove(1);
        rack.getChildren().remove(2);
        rack.getChildren().remove(3);



        GridPane players = new GridPane();

        GridPane gameBoard = new GridPane();

        Rectangle gb = new Rectangle(320, 90, 1280, 720);
        gb.setFill(Color.TRANSPARENT);
        gb.setStroke(Color.BLACK);



        ImageView rackIV = new ImageView(Images.rack);
        rackIV.setPreserveRatio(true);
        rackIV.setFitWidth(1000);
        rackIV.setLayoutX(Rummikub.xCenter - 500);
        rackIV.setLayoutY(888);
        System.out.println(rackIV.getLayoutBounds().getHeight());

        getChildren().addAll(players, gameBoard, gb, rackIV, rack);

    }





















}
