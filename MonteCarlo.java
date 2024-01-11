package com.rummikub.game;

import java.util.ArrayList;
import java.util.List;

import com.rummikub.gui.GameboardGUI;
import com.rummikub.gui.RackGUI;

public class MonteCarlo {

    public static final int TIMEOUT = 100000; 

    public Node root;

    public static Game game;
    public static GameboardGUI gameboard; // here need change GameboardGUI to public
    public static RackGUI rack;
    public static BaselineAgent baselineagent;
    public static SingleTileAgent singletileagent;
    
    // For UCT algorithm
    public static final double C = 1.0;


    public MonteCarlo(Node root) {
        this.root = root;
    }

    public void runMCTS() {

        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < TIMEOUT) {
            Node selectedNode = branch(root);
            int simulationResult = simulate(selectedNode.getGameState());
            backPropagate(selectedNode, simulationResult);
        }
    }

    // branch
    public Node branch(Node node) {

        while (!node.isTerminal() && node.isFullyExpanded()) {
            node = node.selectChildNode();
        }
        return node;
        
    }


    // is this method shoul add to GUI part? because drawtile need the state of board and rack
    public int simulate(GameState state) { 

        gameboard = GameboardGUI.getInstance(); 

        while (!state.isOver()) {  // game.isGameOver()

            ArrayList<ArrayList<Tile>> legalMoves = state.getLegalMoves(); 

            if (!legalMoves.isEmpty()) {
                ArrayList<ArrayList<Tile>> moves = legalMoves; 
                state.drawTile(moves); // drawTile(rack, board);
                state.getState();
            }
        }
        return state.getState(); 
    }

    public void backPropagate(Node node, int result) {

        while (node != null) {
            node.updateScore(result);
            node = node.getParentNode();
        }

    }



    // Define game state and move
    static class GameState{

        public static Game game;
        public GameboardGUI gameboard;

        // check the state of game
        public boolean isOver(){
            return game.isGameOver();
        }

    
        // check the legal move in rack
        // can first check baselineagent, then check singletileagent, if there are legal move, put them to board.
        public ArrayList<ArrayList<Tile>> getLegalMoves() {

            ArrayList<ArrayList<Tile>> moves = baselineagent.baselineAgent(Game.getInstance().currentPlayer.getHand());

            if(moves.isEmpty()){
                moves = singletileagent.singleTilemove(game.getInstance().currentPlayer.getHand(), gameboard.getState());
            }

            return moves;
           

        }


        // put tiles to the board
        public ArrayList<ArrayList<Tile>> drawTile(ArrayList<ArrayList<Tile>> moves){ // Tile[][] rack, Tile[][] board

            return baselineagent.possibleMoveAddingRackToBoard(game.getInstance().currentPlayer.getHand(), gameboard.getState());
            //baselineagent.possibleMoveAddingRackToBoard(rack, board);
        }



        public int getState(){

            if (game.isGameOver()) {
                return Integer.MAX_VALUE; 
            } else {
                return Integer.MIN_VALUE; 
            }

        }
        
        
    } 



    // Define the Node class for the Monte Carlo Tree
    static class Node {

        public Node parent;
        public List<Node> children;
        public int visits;
        public int totalScore;
        public GameState state; 

        public Node(Node parent, GameState state) { 
            this.parent = parent;
            this.state = state;
            this.children = new ArrayList<>();
        }

        // checks whether all possible children node have been expanded
        public boolean isFullyExpanded() {
            return children.size() == state.getLegalMoves().size();

        }

         // check if the game over or not
        public boolean isTerminal() {
            return state.isOver();

        }

        // Select a child based on the UCT (Upper Confidence Bound for Trees) algorithm
        public Node selectChildNode() {

            double maxUCT = Double.MIN_VALUE;
            Node selectedChild = null;

            for (Node child : children) {

                double uct = UCT(child);

                if (uct > maxUCT) {
                    maxUCT = uct;
                    selectedChild = child;
                }
            }

            return selectedChild;
        }

        // Implement UCT (Upper Confidence Bound for Trees) algorithm
        public double UCT(Node child) {

            double ti = Math.sqrt(Math.log(visits) / child.visits);
            double vi = (double) child.totalScore / child.visits;
        
            double uct = vi + C * ti;
        
            return uct;
        }


        public void updateScore(int result) {
            visits++;
            totalScore += result;

        }

        public Node getParentNode() {
            return parent;
        }

        public GameState getGameState() { 
            return state;
        }



    }



}
