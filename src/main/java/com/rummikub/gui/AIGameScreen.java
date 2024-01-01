package com.rummikub.gui;

import com.rummikub.game.Game;
import com.rummikub.game.Tile;
import com.rummikub.model.AgentImplementation;
import com.rummikub.model.BaselineAgent;
import com.rummikub.model.Type;
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
    ArrayList<Tile> aimove;

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
            if (Game.getInstance().currentPlayer.isAI() && Game.getInstance().currentPlayer.getAiType() == Type.BASELINE) { // player is AI
                System.out.println("BaseLine Agent:");
                aimove = null;
                aimove = BaselineAgent.baselineAgent(Game.getInstance().currentPlayer.getHand()); 
                //System.out.println(aimove);
                if (aimove != null && !aimove.isEmpty()) {
                    System.out.println("yes ");
                        makeBaselineMove(aimove, GameboardGUI.getInstance().getState());
                        //BaselineAgent.printListTiles(aimove); // update the board in memory
                        GameboardGUI.getInstance().renderAIMove(); // update the board in the GUI
                        Tile[][] newHand = removeTiles(aimove); // remove tiles used for the AI move
                        RackGUI.getInstance().handToRack(newHand); 
                        //System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));
                }else{ // no move so draw
                    System.out.println("no move possible for computer");
                    if(!Game.getInstance().getPool().isEmpty()) {
                        System.out.println("pool is NOT empty");
                        Game.getInstance().currentPlayer.draw(Game.getInstance().getPool().remove(0));
                    }
                }
                finishMove();}
            else if (Game.getInstance().currentPlayer.isAI() && Game.getInstance().currentPlayer.getAiType() == Type.MAIN) { // player is AI;
                System.out.println("");
                System.out.println("MAIN AGENT:");
                Object[] aimove;
                aimove = AgentImplementation.makeMove(Game.getInstance().currentPlayer.getHand(), GameboardGUI.getInstance().getState()); 
            
                if (aimove != null) {
                    Tile[][] rack = (Tile[][]) aimove[0];
                    Tile[][] board = (Tile[][]) aimove[1];
                    //TO DO aiMove[0] is the new hand and aiMove[1] is the 
                    GameboardGUI.getInstance().setState(board);
                    GameboardGUI.getInstance().renderNewBoard();
                    Game.getInstance().currentPlayer.setHand(rack);
                    RackGUI.getInstance().handToRack(rack);

                }else{ 
                    System.out.println("no move possible for computer");
                    if(!Game.getInstance().getPool().isEmpty()) {
                        System.out.println("pool is NOT empty");
                        Game.getInstance().currentPlayer.draw(Game.getInstance().getPool().remove(0));
                    }
                }
                finishMove();
            }else{ // player is computer
                if (gameboard.stateNotChanged()) {
                    if(!Game.getInstance().getPool().isEmpty()) {
                        System.out.println("pool is NOT empty");
                        Game.getInstance().currentPlayer.draw(Game.getInstance().getPool().remove(0));
                    }
                    finishMove();
                } else {
                    humanPlayerMove();
                }
            }
        });
        
    }
        
    private void humanPlayerMove() {
        if (gameboard.stateNotChanged()) {
            if(!Game.getInstance().getPool().isEmpty()) {
                System.out.println("pool is NOT empty");
                Game.getInstance().currentPlayer.draw(Game.getInstance().getPool().remove(0));
            }
            finishMove();
        } else {
            if (Game.getInstance().isValidBoard(gameboard.getState())) {
                finishMove();
            } else {
                //System.out.println(GameboardGUI.getInstance().getState());
                System.out.println("move is not valid");
                //finishMove();
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

    private Tile[][] makeBaselineMove(ArrayList<Tile> aiMove, Tile[][] board) {
        if (aiMove == null) {
            return board;
        }

        int space = 0;
        boolean done = false;
        int leaveSpace = 0;
        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                if (board[i][y] == null) {
                    if (leaveSpace < 1) leaveSpace++;
                    else space++;
                } else {
                    leaveSpace = 0;
                    space = 0;
                }
                if (!done && space >= aiMove.size()) { // enough space for the move
                    for (int z = y - space + 1, aiIndex = 0; aiIndex < aiMove.size(); z++, aiIndex++) {
                        board[i][z] = aiMove.get(aiIndex);
                    }
                    done = true;
                    break;
                }
            }
            if (done) break;
            space = 0;
        }
        //BaselineAgent.printListTiles(aiMove);
        return board;
    }
    
    private Tile[][] removeTiles(ArrayList<Tile> aiMove){
        
        for (Tile tile : aiMove) {
            for (int i = 0; i < Game.getInstance().currentPlayer.getHand().length; i++) {
                for (int j = 0; j < Game.getInstance().currentPlayer.getHand()[i].length; j++) {
                    if (Game.getInstance().currentPlayer.getHand()[i][j] != null && Game.getInstance().currentPlayer.getHand()[i][j].equals(tile)) {
                        Game.getInstance().currentPlayer.getHand()[i][j] = null;
                    }
                }
            }
        }
        
        return Game.getInstance().currentPlayer.getHand();
    }
    
}
