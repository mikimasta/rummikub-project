package com.rummikub.gui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class GameScreen extends Pane {

    GameScreen() {


        // sets for the grid pane settings

        setBackground(new Background(new BackgroundFill(Color.web("#4A6B3C"), null, null)));
        getStylesheets().add("styling.css");
        //setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

        RackGUI rack = RackGUI.getInstance();

        rack.addToRack(new TileGUI(Color.BLACK));
    
        rack.addToRack(new TileGUI(Color.BLACK));
        rack.addToRack(new TileGUI((byte) 9, Color.RED));
        rack.addToRack(new TileGUI((byte) 7, Color.ORANGE));
        rack.addToRack(new TileGUI((byte) 2, Color.RED));
        rack.addToRack(new TileGUI((byte) 5, Color.BLACK));
        rack.addToRack(new TileGUI((byte) 1, Color.ORANGE));
        rack.addToRack(new TileGUI((byte) 6, Color.BLUE));
        rack.addToRack(new TileGUI((byte) 10, Color.BLUE));
        rack.addToRack(new TileGUI((byte) 11, Color.RED));
        rack.addToRack(new TileGUI((byte) 4, Color.BLACK));
        rack.addToRack(new TileGUI((byte) 8, Color.ORANGE));
        rack.addToRack(new TileGUI((byte) 13, Color.BLACK));
        rack.addToRack(new TileGUI((byte) 12, Color.ORANGE));
        rack.addToRack(new TileGUI((byte) 3, Color.BLUE));


        GridPane players = new GridPane();

        GameboardGUI gameBoard = GameboardGUI.getInstance(); 



        ImageView rackIV = new ImageView(Images.rack);
        rackIV.setPreserveRatio(true);
        rackIV.setFitWidth(1000);
        rackIV.setLayoutX(Rummikub.xCenter - 500);
        rackIV.setLayoutY(888);
        //System.out.println(rackIV.getLayoutBounds().getHeight());

        getChildren().addAll(players, gameBoard, rackIV, rack);

    }



}
