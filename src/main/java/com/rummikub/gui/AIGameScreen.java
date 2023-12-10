package com.rummikub.gui;

import com.rummikub.game.BaselineAgent;
import com.rummikub.game.Game;
import com.rummikub.game.Tile;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

class AIGameScreen extends Pane {

    RackGUI rack;
    GameboardGUI gameboard;
    PlayersGUI players;
    TimerGUI timer;
    HoverButton quit;
    BaselineAgent baselineAgent;

    AIGameScreen() {

        setBackground(new Background(new BackgroundFill(Color.web("#4A6B3C"), null, null)));
        getStylesheets().add("styling.css");
        //setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

        rack = RackGUI.getInstance();

        players = new PlayersGUI(Game.getInstance().getPlayers());

        gameboard = GameboardGUI.getInstance(); 

        timer = new TimerGUI();

        quit = new HoverButton("Quit");
        quit.setLayoutY(1000);
        quit.setLayoutX(50);
        quit.setOnAction(e -> {
            Game.endGame();
            Rummikub.gameWindow.getScene().setRoot(new MainMenu());
        });

        Button endTurn = new HoverButton("End Turn");
        endTurn.setPrefSize(400, 100);
        endTurn.setLayoutX(RackGUI.RACK_X + RackGUI.RACK_WIDTH + 50);
        endTurn.setLayoutY(RackGUI.RACK_Y + RackGUI.RACK_HEIGHT / 2 - 50);

        Button orderByStairs = new HoverButton("111");
        orderByStairs.setPrefSize(300, 60);
        orderByStairs.setLayoutX(RackGUI.RACK_X + RackGUI.RACK_WIDTH + 100);
        orderByStairs.setLayoutY(RackGUI.RACK_Y + RackGUI.RACK_HEIGHT / 2 - 150);

        Button orderByGroup = new HoverButton("123");
        orderByGroup.setPrefSize(300, 60);
        orderByGroup.setLayoutX(RackGUI.RACK_X + RackGUI.RACK_WIDTH + 100);
        orderByGroup.setLayoutY(RackGUI.RACK_Y + RackGUI.RACK_HEIGHT / 2 - 250);

        ImageView rackIV = new ImageView(Images.rack);
        rackIV.setPreserveRatio(true);
        rackIV.setFitWidth(1000);
        rackIV.setLayoutX(Rummikub.xCenter - 500);
        rackIV.setLayoutY(888);
        //System.out.println(rackIV.getLayoutBounds().getHeight());

        getChildren().addAll(timer, players, gameboard, rackIV, rack, endTurn, orderByGroup, orderByStairs, quit);

        rack.handToRack(Game.getInstance().currentPlayer.getHand());
        //System.out.println(Game.getInstance().currentPlayer + "'s hand is " + Game.printBoard(Game.getInstance().currentPlayer.getHand()));
        //System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));

        orderByStairs.setOnAction(e -> {
            Game.orderRackByStairs(Game.getInstance().currentPlayer.getHand());
            rack.handToRack(Game.getInstance().currentPlayer.getHand());
        });
        
        orderByGroup.setOnAction(e -> {
            Game.orderRackByGroup(Game.getInstance().currentPlayer.getHand());
            rack.handToRack(Game.getInstance().currentPlayer.getHand());
        });

        endTurn.setOnAction(e -> {
            if (Game.getInstance().currentPlayer.isAI()) { // player is computer
                ArrayList<Tile> aimove = BaselineAgent.baselineAgent(Game.getInstance().currentPlayer.getHand()); 
                if (aimove == null) {
                        makeAIMove(aimove, GameboardGUI.getInstance().getState());
                        System.out.println(BaselineAgent.printListTiles(aimove));
                        GameboardGUI.getInstance().renderAIMove();
                        System.out.println(Game.getInstance().printBoard(GameboardGUI.getInstance().getState()));
                }else{ // no move so draw
                    System.out.println("no move possible for computer");
                    Game.getInstance().currentPlayer.draw(Game.getInstance().getPool().remove(0));
                }
                finishMove();
            }else{ // player is computer
                if (gameboard.stateNotChanged()) {
                    Game.getInstance().currentPlayer.draw(Game.getInstance().getPool().remove(0));
                    finishMove();
                } else {
                    humanPlayerMove();
                }
            }
        });
        
    }
        
    private void humanPlayerMove() {
        if (gameboard.stateNotChanged()) {
            Game.getInstance().currentPlayer.draw(Game.getInstance().getPool().remove(0));
            finishMove();
        } else {
            if (Game.getInstance().isValidBoard(gameboard.getState())) {
                if (!Game.getInstance().currentPlayer.getFirstMoveMade()) {
                    if (Game.getInstance().isValidFirstMove(gameboard.getState())) {
                        Game.getInstance().currentPlayer.firstMoveMade();
                        finishMove();
                    } else {
                        System.out.println("First move needs to be over 30.");
                    }
                } else {
                    finishMove();
                }
            } else {
                System.out.println("Board is not in a valid state!");
            }
        }
    }

    void finishMove() {

        GameboardGUI.getInstance().setPrevState();
        GameboardGUI.getInstance().lockTiles();
        Game.getInstance().nextPlayer();
        players.update();
        rack.handToRack(Game.getInstance().currentPlayer.getHand());
        timer.resetTimer();

    }

    private Tile[][] makeAIMove(ArrayList<Tile> aiMove, Tile[][] board) {
        if (aiMove == null) {
            return board;
        }

        System.out.println("FUCK MAN");
        int space = 0;
        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                if (board[i][y] == null) {
                    space++;
                }
                if (space > aiMove.size()) { // enough space for the move
                    for (int z = y - space + 1, aiIndex = 0; aiIndex < aiMove.size(); z++, aiIndex++) {
                        board[i][z] = aiMove.get(aiIndex);
                    }
                    break; 
                }
            }
            space = 0;
        }
        BaselineAgent.printListTiles(aiMove);
        return board;
    }
    

    
}