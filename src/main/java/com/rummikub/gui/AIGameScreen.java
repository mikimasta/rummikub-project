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
    ArrayList<Tile> aimove1;
    ArrayList<Tile> aimove2;
    ArrayList<Tile> aimove3;

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
            if (Game.getInstance().currentPlayer.isAI()) {
                aimove1 = null;
                aimove1 = BaselineAgent.possibleMoveAddingRackToBoard(Game.getInstance().currentPlayer.getHand(), GameboardGUI.getInstance().getState());
                System.out.println("move avec rack + board");
                System.out.println(BaselineAgent.printListTiles(aimove1));
                if (aimove1 != null && !aimove1.isEmpty()) {
                    makeAIMove(aimove1, GameboardGUI.getInstance().getState());
                    BaselineAgent.printListTiles(aimove1);
                    GameboardGUI.getInstance().renderAIMove();
                    Tile[][] newHand = removeTilesFromRack(aimove1);
                    RackGUI.getInstance().handToRack(newHand);
                    System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));
                } else {
                    // If the first move is not possible, try the second move
                    aimove2 = null;
                    aimove2 = BaselineAgent.baselineAgent(Game.getInstance().currentPlayer.getHand());
                    System.out.println("move avec baseline agent");
                    System.out.println(BaselineAgent.printListTiles(aimove2));
                    if (aimove2 != null && !aimove2.isEmpty()) {
                        makeAIMove(aimove2, GameboardGUI.getInstance().getState());
                        BaselineAgent.printListTiles(aimove2);
                        GameboardGUI.getInstance().renderAIMove();
                        Tile[][] newHand = removeTilesFromRack(aimove2);
                        RackGUI.getInstance().handToRack(newHand);
                        System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));
                    } else {
                        // If the second move is not possible, try the third move
                        aimove3 = null;
                        aimove3 = BaselineAgent.singleTilemove(Game.getInstance().currentPlayer.getHand(), GameboardGUI.getInstance().getState());
                        if (aimove3 != null && !aimove3.isEmpty() && aimove3.size() > 2) {
                            System.out.println("move avec single tiles");
                            System.out.println(BaselineAgent.printListTiles(aimove3));
                            removeTilesFromBoard(aimove3); // TODO doesn't do it i think
                            GameboardGUI.getInstance().removeAIMove();
                            makeAIMove(aimove3, GameboardGUI.getInstance().getState());
                            GameboardGUI.getInstance().renderAIMove();
                            Tile[][] newHand = removeTilesFromRack(aimove3);
                            RackGUI.getInstance().handToRack(newHand);
                            System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));
                        } else { // no move is possible draw a tile
                            System.out.println("No move possible for computer. Drawing a tile...");
                            Game.getInstance().currentPlayer.draw(Game.getInstance().getPool().remove(0));
                        }
                    }
                }
                finishMove();
            }else{ // player is human
                
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

    /**
     * updates the gameboard in memory by finding a space on the board to place the ai move
     * @param aiMove list of tiles of the move
     * @param board gameboard
     * @return the updated gameboard
     */
    private Tile[][] makeAIMove(ArrayList<Tile> aiMove, Tile[][] board) {
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
        BaselineAgent.printListTiles(aiMove);
        return board;
    }
    
    /**
     * method to remove the tiles used in the AI move from its rack  
     * @param aiMove list of tiles of the move
     * @return the updated rack
     */
    private Tile[][] removeTilesFromRack(ArrayList<Tile> aiMove){
        
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

    public static Tile[][] removeTilesFromBoard(ArrayList<Tile> aiMove) {
        
        for (int i = 0; i < GameboardGUI.getInstance().getState().length; i++) {
            for (int j = 0; j < GameboardGUI.getInstance().getState()[i].length; j++) {
                if (aiMove.contains(GameboardGUI.getInstance().getState()[i][j])) {
                    GameboardGUI.getInstance().getState()[i][j] = null;
                }
            }
        }
        return GameboardGUI.getInstance().getState();
    }

    
}