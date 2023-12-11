package com.rummikub.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.rummikub.gui.GameboardGUI;
import com.rummikub.gui.RackGUI;

public class MonteCarlo {

    public static final int TIMEOUT = 100; // Adjust as needed

    public Node root;

    public static Game game;
    public static GameboardGUI gameboard;
    public static RackGUI rack;


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
    private Node branch(Node node) {

        while (!node.isTerminal() && node.isFullyExpanded()) {
            node = node.selectChildNode();
        }
        return node;
        
    }

    // should change some sruff to implement to game
    private int simulate(GameState state) { // GameState state / state is game, move is legal move 
        // Random simulation or a simple heuristic can be used here
        // Simulate until a terminal state is reached

        gameboard = GameboardGUI.getInstance(); //

        while (!state.isOver()) { // game.isGameOver()

            ArrayList<Tile> legalMoves = state.getLegalMoves(); // ArrayList<Move> legalMoves

            if (!legalMoves.isEmpty()) {
                ArrayList<Tile> randomMove = legalMoves; //Move randomMove  legalMoves.get(new Random().nextInt(legalMoves.size()))
                state.drawTile(randomMove);
                state.getState();
            }
        }
        return state.getState(); // Return the utility of the terminal state
    }

    private void backPropagate(Node node, int result) {

        while (node != null) {
            node.updateScore(result);
            node = node.getParentNode();
        }

    }




    // Define game state representation and move generation
    // this class is use method form Game class or some GUI class
    static class GameState{

        // Implement game state representation and move generation logic
        public static Game game;
        public GameboardGUI gameboard;

        // check the state of game
        public boolean isOver(){
            return game.isGameOver();
        }

        // this method should change 
        // check the legal move form rack to board and rearrange tile in board
        public ArrayList<Tile> getLegalMoves() {

            //
            ArrayList<Tile> l = new ArrayList<Tile>();
            ArrayList<Tile> lm = new ArrayList<Tile>();
            Tile[][] hand = game.getInstance().currentPlayer.getHand();
            int a = 0;
            int v = 0;

            for(int i = 0; i < hand.length; i++){
                for(int j = 0; j < hand[i].length; j++){
                    if(hand[i][j] != null){
                        l.add(a, hand[i][j]);
                    }
                    else {
                        a++;
                    }
                }
            }
            for(int k = 0; k < l.size(); k++){
                if(game.checkIfGroup(l)){
                    lm.addAll(l);
                } else if (game.checkIfStairs(l)){
                    lm.addAll(l);
                }
            }

            // put the legal move tile to board from hand
            for(int z = 0; z < lm.size(); z++){
                for(int b = 0; b < lm.size(); b++){
                    if(gameboard.getState()[z][b] == null){
                        gameboard.getState()[z][b] = lm.get(v++);
                    }

                }
            }

            return lm;
        }

        // impltment method
        // put tiles to the board from rack
        public GameState drawTile(ArrayList<Tile> randomMove){ //Tile[][] rack

            GameState state = new GameState();
            int v = 0;

            // put the legal move tile to board from hand
            for(int z = 0; z < getLegalMoves().size(); z++){
                for(int b = 0; b < getLegalMoves().size(); b++){
                    if(gameboard.getState()[z][b] == null){
                        gameboard.getState()[z][b] = getLegalMoves().get(v++);
                    }

                }
            }

            return state;
            
        }

        // be used to calculate a utility value for a particular game state, indicating how favorable or desirable that state is
        // improve the code
        public int getState(){

            if (game.isGameOver()) {
                return Integer.MAX_VALUE; 
            } else if (!game.isGameOver()) {
                return Integer.MIN_VALUE; 
            } else {
                // use other considerations for utility, like score
                return 0;
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

        // checks whether all possible children of a node have been expanded
        public boolean isFullyExpanded() {
            // Implement logic to check if all children have been expanded
            return children.size() == state.getLegalMoves().size();

        }

        public boolean isTerminal() {
            // Implement logic to check if the state is terminal
            return state.isOver();

        }

        public Node selectChildNode() {
            // Implement logic to select a child based on the UCT (Upper Confidence Bound for Trees) algorithm

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

        private double UCT(Node child) {

            double ti = Math.sqrt(Math.log(visits) / child.visits);
            double vi = (double) child.totalScore / child.visits;
            double C = 1.0; // C can change to different value.
        
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
