package com.rummikub.gui;

import com.rummikub.game.BaselineAgent;
import com.rummikub.game.TreeSearchBaseline;
import com.rummikub.game.Game;
import com.rummikub.game.Node;
import com.rummikub.game.SingleTileAgent;
import com.rummikub.game.SplittingAgent;
import com.rummikub.game.Tile;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashSet;

public class AIGameScreen extends Pane {

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
                //AImoveAgents();
                MCTS();
                if (gameboard.stateNotChanged() && Game.getInstance().getPoolSize(Game.getInstance().getPool()) > 0) {
                    System.out.println("No move possible for computer. Drawing a tile...");
                    Game.getInstance().currentPlayer.draw(Game.getInstance().getPool().remove(0));
                } else if (Game.getInstance().getPoolSize(Game.getInstance().getPool()) == 0){
                    System.out.println("No more tiles in the pool");
                } 

            // System.out.println("the size of the pool is : " + Game.getInstance().getPoolSize(Game.getInstance().getPool()));
            finishMove();
            
            } else { // player is human
                if (gameboard.stateNotChanged() && Game.getInstance().getPoolSize(Game.getInstance().getPool()) > 0) { 
                    Game.getInstance().currentPlayer.draw(Game.getInstance().getPool().remove(0));
                    finishMove();
                } else if (Game.getInstance().getPoolSize(Game.getInstance().getPool()) == 0){ 
                    System.out.println("No more tiles in the pool");
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

        if(Game.getInstance().isGameOver()){
            Rummikub.gameWindow.getScene().setRoot(new EndGameScreen(Game.getInstance().currentPlayer.getName()));
        }
        GameboardGUI.getInstance().setPrevState();
        GameboardGUI.getInstance().lockTiles();
        Game.getInstance().nextPlayer();
        players.update();
        rack.handToRack(Game.getInstance().currentPlayer.getHand());
        timer.resetTimer();

    }

    
    void AImoveAgents() {
        /* 
        aimove = BaselineAgent.possibleMoveAddingRackToBoard(Game.getInstance().currentPlayer.getHand(), GameboardGUI.getInstance().getState());
        if (aimove != null && !aimove.isEmpty() && aimove.size() > 0){
            System.out.println("Move with single tiles");
            System.out.println(BaselineAgent.printMoves(aimove));
            processAIMove(aimove);
        }
        */
        ArrayList<ArrayList<Tile>> aimove = BaselineAgent.baselineAgent(Game.getInstance().currentPlayer.getHand());
        if (aimove != null && !aimove.isEmpty() && aimove.size() > 0) {
            System.out.println("move with baseline agent");
            System.out.println(BaselineAgent.printMoves(aimove));
            processAIMove1(aimove);
        } 
        aimove = SingleTileAgent.singleTilemove(Game.getInstance().currentPlayer.getHand(), GameboardGUI.getInstance().getState());
        if (aimove != null && !aimove.isEmpty()) { // && aimove.size() > 0
            System.out.println("Move with single tiles");
            System.out.println(BaselineAgent.printMoves(aimove));
            processAIMove2_3(aimove);
        }
        aimove = SplittingAgent.splittingMoves(Game.getInstance().currentPlayer.getHand(), GameboardGUI.getInstance().getState());
        if (aimove != null && !aimove.isEmpty()) { // && aimove.size() > 0
            System.out.println("Move with splitting method");
            System.out.println(BaselineAgent.printMoves(aimove));
            processAIMove2_3(aimove);
        }
    }

    void processAIMove1(ArrayList<ArrayList<Tile>> aiMove) {

        makeAIMoves(aiMove, GameboardGUI.getInstance().getState());
        GameboardGUI.getInstance().renderAIMove();
        Tile[][] newHand = removeTilesFromRack(aiMove, Game.getInstance().currentPlayer.getHand());
        RackGUI.getInstance().handToRack(newHand);
        finishAIMove();
        System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));

    }

    void processAIMove2_3(ArrayList<ArrayList<Tile>> aiMove) {
        
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

        if(Game.getInstance().isGameOver()){
            Rummikub.gameWindow.getScene().setRoot(new EndGameScreen(Game.getInstance().currentPlayer.getName()));
        }
        GameboardGUI.getInstance().setState(GameboardGUI.getInstance().getState());
        GameboardGUI.getInstance().lockTiles();

    }

    void MCTS() {

        ArrayList<Node> MCTSList = TreeSearchBaseline.findBestMove(GameboardGUI.getInstance().getState(), Game.getInstance().currentPlayer.getHand());
        MCTSList = removeDuplicateLists(MCTSList);

        for (Node node : MCTSList) {
            if (node.getAgentUsed() != 100) {
                ArrayList<ArrayList<Tile>> MCTSmove = new ArrayList<ArrayList<Tile>>();
                if (node.getAgentUsed() == 0) {
                    MCTSmove.add(node.getMove());
                    processAIMove1(MCTSmove);
                } else if ((node.getAgentUsed() == 2)) {
                    MCTSmove = arrayListToNestedArrayList(node.getMove());
                    processAIMove2_3(MCTSmove);
                } else {
                    MCTSmove.add(node.getMove());
                    processAIMove2_3(MCTSmove);
                }
            }
        }
    }

    /**
     * updates the gameboard in memory by finding spaces on the board to place the AI moves
     * @param aiMoves list of moves of tiles
     * @param board gameboard
     * @return updated gameboard
     */
    public static Tile[][] makeAIMoves(ArrayList<ArrayList<Tile>> aiMoves, Tile[][] board) {
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


    public static ArrayList<ArrayList<Tile>> arrayListToNestedArrayList(ArrayList<Tile> aiMove) {
        ArrayList<ArrayList<Tile>> moves = new ArrayList<>();
        ArrayList<Tile> list = new ArrayList<>();
    
        for (Tile tile : aiMove) {
            if (tile != null) {
                list.add(tile);
            } else {
                moves.add(list);
                list = new ArrayList<>();
            }
        }
        if (!list.isEmpty()) {
            moves.add(list);
        }
    
        return moves;
    }

    /**
     * checks if the move of a node have have common elements, if they do keep the node with biggest move
     * @param MCTSList list of nodes
     * @return new list containing unique nodes
     */
    private ArrayList<Node> removeDuplicateLists(ArrayList<Node> MCTSList) {
        ArrayList<Node> resultList = new ArrayList<>();

        for (int i = 0; i < MCTSList.size(); i++) {
            Node node = MCTSList.get(i);
            boolean isDuplicate = false;

            for (Node resultNode : resultList) {
                if (haveCommonElement(resultNode.getMove(), node.getMove())) {
                    isDuplicate = true;
                    if (node.getMove().size() > resultNode.getMove().size()) {
                            resultList.remove(resultNode);
                            resultList.add(node);
                    }
                    break;
                }
            }
            if (!isDuplicate) {
                resultList.add(node);
            }
        }
        return resultList;
    }

    /**
     * checks if two lists have commun elements
     * @param move move
     * @param move2 move2
     * @return true if the moves have commun elements
     */
    private boolean haveCommonElement(ArrayList<Tile> move, ArrayList<Tile> move2) {
        HashSet<Tile> set = new HashSet<>(move);
        
        for (Tile t : move2) {
            if (set.contains(t)) {
                return true;
            }
        }
        return false;
    }


}