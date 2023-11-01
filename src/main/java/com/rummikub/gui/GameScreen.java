package com.rummikub.gui;

import com.rummikub.game.Game;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Arrays;

class GameScreen extends Pane {

    RackGUI rack;
    GameboardGUI gameboard;
    PlayersGUI players;

    GameScreen() {


        setBackground(new Background(new BackgroundFill(Color.web("#4A6B3C"), null, null)));
        getStylesheets().add("styling.css");
        //setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

        rack = RackGUI.getInstance();

        players = new PlayersGUI(Game.getInstance().getPlayers());


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


        rack.handToRack(Game.getInstance().currentPlayer.getHand());
        //System.out.println("Starting player: " + Game.getInstance().currentPlayer);
        //System.out.println(Game.getInstance().currentPlayer + "'s hand is " + Game.printBoard(Game.getInstance().currentPlayer.getHand()));
        System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));


        endTurn.setOnAction(e -> {
            System.out.println("Current player: " + Game.getInstance().currentPlayer);

            System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));

            System.out.println(Arrays.deepToString(Game.getInstance().currentPlayer.getHand()));

            if (gameboard.stateNotChanged())
                rack.addToRack(Game.getInstance().getPool().remove(0));

            if (Game.getInstance().isValidBoard(gameboard.getState())) {
                if(!Game.getInstance().currentPlayer.getFirstMoveMade() ){
                    if(Game.getInstance().isValidFirstMove(rack.tiles, Game.getInstance().currentPlayer.getHand())){
                        Game.getInstance().currentPlayer.firstMoveMade();
                    }
                    else{
                        //break;
                    }
                }
                Tile[][] rackTileCopy = new Tile[2][15];
                for (int i = 0; i < rack.tiles.length; i++) {
                    rackTileCopy[i] = rack.tiles[i].clone();
                }
                //System.out.println("Hand to set for " + Game.getInstance().currentPlayer + ": " + Game.printBoard(rackTileCopy));
                Game.getInstance().currentPlayer.setHand(rackTileCopy);
                GameboardGUI.getInstance().setPrevState();
                Game.getInstance().nextPlayer();
                players.update();
                //System.out.println("Current player is now: " + Game.getInstance().currentPlayer);
                //System.out.println("Hand is : " + Game.printBoard(Game.getInstance().currentPlayer.getHand()));
                rack.handToRack(Game.getInstance().currentPlayer.getHand());
                //System.out.println("Rack is : " + Game.printBoard(rack.tiles));

            } else {
                System.out.println("Board is not in a valid state!");
            }







        });





    }


    }




