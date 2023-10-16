package com.rummikub.gui;

import com.rummikub.game.Game;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Arrays;

class GameScreen extends Pane {

    RackGUI rack;
    GameboardGUI gameboard;

    GameScreen() {


        setBackground(new Background(new BackgroundFill(Color.web("#4A6B3C"), null, null)));
        getStylesheets().add("styling.css");
        //setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

        rack = RackGUI.getInstance();

        GridPane players = new GridPane();

        gameboard = GameboardGUI.getInstance(); 

        Button endTurn = new HoverButton("End Turn");
        endTurn.setPrefSize(400, 100);
        endTurn.setLayoutX(RackGUI.RACK_X + RackGUI.RACK_WIDTH + 50);
        endTurn.setLayoutY(RackGUI.RACK_Y + RackGUI.RACK_HEIGHT / 2 - 50);


        ImageView rackIV = new ImageView(Images.rack);
        rackIV.setPreserveRatio(true);
        rackIV.setFitWidth(1000);
        rackIV.setLayoutX(Rummikub.xCenter - 500);
        rackIV.setLayoutY(888);
        //System.out.println(rackIV.getLayoutBounds().getHeight());

        getChildren().addAll(players, gameboard, rackIV, rack, endTurn);

        //for (int i = 0; i < 13; ++i) rack.addToRack(Game.getInstance().getPool().remove(0));

        rack.handToRack(Game.getInstance().currentPlayer.getHand());

        endTurn.setOnAction(e -> {

            System.out.println(Arrays.deepToString(Game.getInstance().currentPlayer.getHand()));


            if (gameboard.stateNotChanged())
                rack.addToRack(Game.getInstance().getPool().remove(0));

            if (Game.getInstance().isValidBoard(gameboard.getState())) {
                Tile[][] rackTileCopy = new Tile[2][15];
                for (int i = 0; i < rack.tiles.length; i++) {
                    rackTileCopy[i] = rack.tiles[i].clone();
                }
                Game.getInstance().currentPlayer.setHand(rackTileCopy);
                GameboardGUI.getInstance().setPrevState();
                Game.getInstance().nextPlayer();
                System.out.println(Arrays.deepToString(Game.getInstance().currentPlayer.getHand()));
                rack.handToRack(Game.getInstance().currentPlayer.getHand());
                System.out.println(Arrays.deepToString(Game.getInstance().currentPlayer.getHand()));

            } else {
                System.out.println("Board is not in a valid state!");
            }







        });





    }


    }




