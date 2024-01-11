package com.rummikub.gui;

import com.rummikub.game.BaselineAgent;
import com.rummikub.game.Game;
import com.rummikub.game.SingleTileAgent;
import com.rummikub.game.Tile;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashSet;

class AIGameScreen extends Pane {

    RackGUI rack;
    GameboardGUI gameboard;
    PlayersGUI players;
    TimerGUI timer;
    HoverButton quit;

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

        getChildren().addAll(timer, players, gameboard, rackIV, rack, endTurn, orderByGroup, orderByStairs, quit);

        rack.handToRack(Game.getInstance().currentPlayer.getHand());

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

                ArrayList<ArrayList<Tile>> aimove = BaselineAgent.baselineAgent(Game.getInstance().currentPlayer.getHand());
                if (aimove != null && !aimove.isEmpty() && aimove.size() > 0) {
                    System.out.println("move with baseline agent");
                    System.out.println(BaselineAgent.printMoves(aimove));
                    makeAIMoves(aimove, GameboardGUI.getInstance().getState());
                    GameboardGUI.getInstance().renderAIMove();
                    Tile[][] newHand = removeTilesFromRack(aimove, Game.getInstance().currentPlayer.getHand());
                    RackGUI.getInstance().handToRack(newHand);
                    finishAIMove();
                    System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));
                } else {
                    aimove = SingleTileAgent.singleTilemove(Game.getInstance().currentPlayer.getHand(), GameboardGUI.getInstance().getState());
                    if (aimove != null && !aimove.isEmpty() && aimove.size() > 0) {
                        System.out.println("Move with single tiles");
                        System.out.println(BaselineAgent.printMoves(aimove));
                        processAIMove(aimove);
                    } else {
                        aimove = SingleTileAgent.singleTilemove(Game.getInstance().currentPlayer.getHand(), GameboardGUI.getInstance().getState());
                        if (aimove != null && !aimove.isEmpty() && aimove.size() > 0) {
                            System.out.println("Move with splitting method");
                            System.out.println(BaselineAgent.printMoves(aimove));
                            processAIMove(aimove);
                        } else { // no move is possible draw a tile
                            System.out.println("No move possible for computer. Drawing a tile...");
                            Game.getInstance().currentPlayer.draw(Game.getInstance().getPool().remove(0));
                        }
                    }
                    finishMove();
                }
            } else { // player is human
                if (gameboard.stateNotChanged() ) { // TODO if no tiles in the pool to draw 
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
                System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));
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

    void processAIMove(ArrayList<ArrayList<Tile>> aiMove) {

        removeTilesFromBoard(aiMove, GameboardGUI.getInstance().getState()); // remove tiles already on board which are used in the aimove in memory
        GameboardGUI.getInstance().removeAIMove(); 
        makeAIMoves(aiMove, GameboardGUI.getInstance().getState()); // add tiles from aimove2 to memory
        GameboardGUI.getInstance().renderAIMove(); // update the GUI
        Tile[][] newHand = removeTilesFromRack(aiMove, Game.getInstance().currentPlayer.getHand()); // update the hand in memory
        RackGUI.getInstance().handToRack(newHand); // update the hand in the GUI
        finishAIMove();
        System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));

    }

    void finishAIMove() {
        GameboardGUI.getInstance().setPrevState();
        GameboardGUI.getInstance().lockTiles();
    }

    /**
     * updates the gameboard in memory by finding spaces on the board to place the AI moves
     * @param aiMoves list of moves of tiles
     * @param board gameboard
     * @return updated gameboard
     */
    private Tile[][] makeAIMoves(ArrayList<ArrayList<Tile>> aiMoves, Tile[][] board) {
        if (aiMoves == null) {
            return board;
        }

        for (ArrayList<Tile> aiMove : aiMoves) {
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

                    if (!done && space > aiMove.size()) { // enough space for the move
                        for (int z = y - space + 1, aiIndex = 0; aiIndex < aiMove.size(); z++, aiIndex++) {
                            board[i][z] = aiMove.get(aiIndex);
                        }
                        done = true;
                        break;
                    }
                }

                if (done) break;
                space = 0; leaveSpace = 0;
            }
        }

        return board;
    }

    /**
     * removes tiles used in the AI move from its rack 
     * @param aiMoves list of moves of tiles
     * @param board gameboard
     * @return updated gameboard
     */
    private Tile[][] removeTilesFromRack(ArrayList<ArrayList<Tile>> aiMoves, Tile[][] rack) {
        if (aiMoves == null) {
            return rack;
        }

        HashSet<Tile> tilesToRemove = new HashSet<>();
        for (ArrayList<Tile> aiMove : aiMoves) {
            tilesToRemove.addAll(aiMove);
        }

        for (int i = 0; i < rack.length; i++) {
            for (int j = 0; j < rack[i].length; j++) {
                if (rack[i][j] != null && tilesToRemove.contains(rack[i][j])) {
                    rack[i][j] = null;
                }
            }
        }

        return rack;
    }

    /**
     * removes tiles from the gameboard in memory.
     * @param aiMoves list of moves of tiles
     * @param board gameboard
     * @return updated gameboard
     */
    public static Tile[][] removeTilesFromBoard(ArrayList<ArrayList<Tile>> aiMoves, Tile[][] board) {
        if (aiMoves == null) {
            return board;
        }

        HashSet<Tile> tilesToRemove = new HashSet<>();
        for (ArrayList<Tile> aiMove : aiMoves) {
            tilesToRemove.addAll(aiMove);
        }
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (tilesToRemove.contains(board[i][j])) {
                    board[i][j] = null;
                }
            }
        }

        return board;
    }

 
}