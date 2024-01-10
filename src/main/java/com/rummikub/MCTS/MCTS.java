package com.rummikub.MCTS;

import java.util.ArrayList;

import com.rummikub.game.Tile;



public class MCTS {
    private int NUM_SIMULATIONS = 1000;

    public void mcts(ArrayList<ArrayList<Tile>> stateBoard, ArrayList<Tile> stateHand){
        Node root = new Node(stateBoard, stateHand);
        for(int i = 0; i < NUM_SIMULATIONS; i++){
            Node node = select(root);
            Node child = expand(node);
            int reward = simulate(child.getStateBoard(), child.getStateHand());
            backpropagate(child, reward);
        }
        return selectBestMove(root);
    }

    private Node select(Node node) {
       
    while(node.getChildren() != null){
            node = bestUCT(node);
    }
        return node;

    }

    private  Node bestUCT(Node node) {
        double maxUCT = Double.MIN_VALUE;

        for (Node child : node.getChildren()) {

            double uct = UCT(child);

            if (uct > maxUCT) {
                maxUCT = uct;
                node = child;
            }
        }
        return node;
    }
    
    private double UCT(Node child) {

        double explotation= (double) child.getReward() / child.getVisits();

        double constant = 1.0;
        double exploration = constant *   Math.sqrt(Math.log(child.getParent().getVisits()) / child.getVisits());      
        double uct = explotation + exploration;
        
        return uct;
    }
    
    private Node expand(Node node){
        // Get the current game state (board and hand)
        ArrayList<ArrayList<Tile>> stateBoard = node.getStateBoard();
        ArrayList<Tile> stateHand = node.getStateHand();
        
        // Generate all possible child nodes
        ArrayList<ArrayList<ArrayList<Tile>>> validMoves = generateValidMove(stateBoard, stateHand);
        for(ArrayList<ArrayList<Tile>> moveBoard : validMoves){
            ArrayList<Tile> moveHand = getNewHand(moveBoard, stateBoard, stateHand);
            Node child = new Node(moveBoard, moveHand);
            node.addChild(child);
            child.setParent(node);
        }
        
        return child;
    }

    private  ArrayList<ArrayList<ArrayList<Tile>>> generateValidMoves(ArrayList<ArrayList<Tile>> stateBoard, ArrayList<Tile> stateHand){
        ArrayList<ArrayList<ArrayList<Tile>>> validMoves = new ArrayList<ArrayList<ArrayList<Tile>>>();
        //fill this in
        return validMoves;
    }

    private static ArrayList<Tile> getNewHand(ArrayList<ArrayList<Tile>> solutionBoard, ArrayList<ArrayList<Tile>> intialBoard, ArrayList<Tile> hand){
        ArrayList<Tile> usedTiles = new ArrayList<>();

        //ArrayList<Tile> deepCopy = deepCopyHand(hand);
   
        // Iterate through list1
        for (ArrayList<Tile> sublist1 : solutionBoard) {
            for (Tile tile : sublist1) {
                // Check if the tile is not present in list2
                if (!isTilePresent(tile, intialBoard)) {
                    usedTiles.add(tile);
                }
            }
        }        
        ArrayList<Tile> finalHand = removeUsedTile(hand, usedTiles);
        return finalHand;
    }

    public static ArrayList<Tile> removeUsedTile(ArrayList<Tile> hand, ArrayList<Tile> used){
        ArrayList<Tile> result = new ArrayList<>();
        for(int i = 0; i < hand.size(); i++){
                Tile currentFromHand = hand.get(i);
                boolean tileUsed = false;
            for(int j = 0; j < used.size(); j++){
                Tile currentFromused = used.get(j);
                if((currentFromHand.getColorString().equals(currentFromused.getColorString()))){
                    if(currentFromHand.getNumber() == currentFromused.getNumber()){
                        tileUsed = true;
                    }   
                }
            }
            if(!tileUsed){
                result.add(currentFromHand);
            }
        }
        return result;
    }

    public static boolean isTilePresent(Tile tile, ArrayList<ArrayList<Tile>> list) {
        // Check if the tile is present in any sublist of the list
        for (ArrayList<Tile> sublist : list) {
            if (sublist.contains(tile)) {
                return true;
            }
        }
        return false;
    }
}
