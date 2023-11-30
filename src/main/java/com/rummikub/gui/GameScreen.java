package com.rummikub.gui;

import com.rummikub.game.Game;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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

        Button orderByStairs = new HoverButton("Order By Stairs");
        orderByStairs.setPrefSize(400, 100);
        orderByStairs.setLayoutX(RackGUI.RACK_X + RackGUI.RACK_WIDTH );
        orderByStairs.setLayoutY(RackGUI.RACK_Y + RackGUI.RACK_HEIGHT / 2 - 250);

        Button orderByGroup = new HoverButton("Order By Group");
        orderByGroup.setPrefSize(400, 100);
        orderByGroup.setLayoutX(RackGUI.RACK_X + RackGUI.RACK_WIDTH );
        orderByGroup.setLayoutY(RackGUI.RACK_Y + RackGUI.RACK_HEIGHT / 2 - 150);

        ImageView rackIV = new ImageView(Images.rack);
        rackIV.setPreserveRatio(true);
        rackIV.setFitWidth(1000);
        rackIV.setLayoutX(Rummikub.xCenter - 500);
        rackIV.setLayoutY(888);
        //System.out.println(rackIV.getLayoutBounds().getHeight());

        getChildren().addAll(players, gameboard, rackIV, rack, endTurn, orderByGroup, orderByStairs);


        rack.handToRack(Game.getInstance().currentPlayer.getHand());
        //System.out.println(Game.getInstance().currentPlayer + "'s hand is " + Game.printBoard(Game.getInstance().currentPlayer.getHand()));
        //System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));

        orderByStairs.setOnAction(e -> {
            System.out.println("okay dude");
            Game.orderRackByStairs(Game.getInstance().currentPlayer.getHand());
            rack.handToRack(Game.getInstance().currentPlayer.getHand());
        });
        
        orderByGroup.setOnAction(e -> {
            System.out.println("okay dude");
            Game.orderRackByStairs(rack.handToRack(Game.getInstance().currentPlayer.getHand()));
            Game.orderRackByGroup(rack.getInstance().tiles);
            
            rack.handToRack(Game.getInstance().currentPlayer.getHand());
        });

        endTurn.setOnAction(e -> {
            //System.out.println("Current player: " + Game.getInstance().currentPlayer);

            //System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));

            //System.out.println(Arrays.deepToString(Game.getInstance().currentPlayer.getHand()));

            if (gameboard.stateNotChanged())
                Game.getInstance().currentPlayer.draw(Game.getInstance().getPool().remove(0));

            if (Game.getInstance().isValidBoard(gameboard.getState())) {
//                if (!Game.getInstance().currentPlayer.getFirstMoveMade() ) {
//                    if (Game.getInstance().isValidFirstMove(rack.tiles, Game.getInstance().currentPlayer.getHand())) {
//                        Game.getInstance().currentPlayer.firstMoveMade();
//                    }
//                    else{
//                        //break;
//                    }
//                }

                GameboardGUI.getInstance().setPrevState();
                GameboardGUI.getInstance().lockTiles();
                Game.getInstance().nextPlayer();
                players.update();
                rack.handToRack(Game.getInstance().currentPlayer.getHand());

            } else {
                System.out.println("Board is not in a valid state!");
            }




        });





    }


    }




