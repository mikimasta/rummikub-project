package com.rummikub.game.MCTS;

import java.util.ArrayList;
import java.util.List;

import com.rummikub.game.Tile;



public class MCTS {
    private static int NUM_SIMULATIONS = 1000;

    public static ArrayList<ArrayList<Tile>> mcts(ArrayList<ArrayList<Tile>> stateBoard, ArrayList<Tile> stateHand, ArrayList<Tile> opponentHand, ArrayList<Tile> pile){
        Node root = new Node(stateBoard, stateHand);
        for(int i = 0; i < NUM_SIMULATIONS; i++){
            Node node = select(root);
            List<Node>  children = expand(node).getChildren();
            for(Node child : children){
                double reward = simulateNormal(child.getStateBoard(), child.getStateHand(), opponentHand, pile);
                child.updateScore(reward);
                backpropagate(child, reward);

            }
        }
        return selectBestMove(root).getStateBoard();
        //return selectBestMove(root).getStateBoard;
    }

    private static Node select(Node node) {
       
    while(node.getChildren() != null){
            node = bestUCT(node);
    }
        return node;

    }

    private static Node bestUCT(Node node) {
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
    
    private static double UCT(Node child) {

        double explotation= (double) child.getReward() / child.getVisits();
        double constant = 1.0;
        double exploration = constant *   Math.sqrt(Math.log(child.getParent().getVisits()) / child.getVisits());      
        double uct = explotation + exploration;
        
        return uct;
    }
    
    private static Node expand(Node node){
        // Get the current game state (board and hand)
        ArrayList<ArrayList<Tile>> stateBoard = node.getStateBoard();
        ArrayList<Tile> stateHand = node.getStateHand();

        SolutionFinderAll finder = new SolutionFinderAll();
        
        // Generate all possible child nodes
        ArrayList<ArrayList<ArrayList<Tile>>> validMoves = finder.getAllSolutions(stateHand, stateBoard);
        for(ArrayList<ArrayList<Tile>> moveBoard : validMoves){
            ArrayList<Tile> moveHand = getNewHand(moveBoard, stateBoard, stateHand);
            Node child = new Node(moveBoard, moveHand);
            child.setParent(node);
            node.addChild(child);
        }
        
        return node;
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

    private static ArrayList<Tile> removeUsedTile(ArrayList<Tile> hand, ArrayList<Tile> used){
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

    private static boolean isTilePresent(Tile tile, ArrayList<ArrayList<Tile>> list) {
        // Check if the tile is present in any sublist of the list
        for (ArrayList<Tile> sublist : list) {
            if (sublist.contains(tile)) {
                return true;
            }
        }
        return false;
    }

    private static double simulateNormal(ArrayList<ArrayList<Tile>> stateBoard, ArrayList<Tile> stateHand, ArrayList<Tile> opponentHand, ArrayList<Tile> pile){
        int player = 0;
        ArrayList<ArrayList<Tile>> initalBoard = new ArrayList<ArrayList<Tile>>();
        initalBoard = stateBoard;
        while((stateHand != null && opponentHand != null) && !pile.isEmpty() ){
            if(player == 0){
                ArrayList<ArrayList<Tile>> move = Move.getMove(stateHand, stateBoard);
                if(move != null){
                stateBoard = move;
                stateHand = getNewHand(initalBoard, stateBoard, stateHand);
                }
                else{
                    stateHand.add(pile.remove(0));
                }
                player = 1;
            }
            else if(player == 1){
                ArrayList<ArrayList<Tile>> move = Move.getMove(opponentHand, stateBoard);
                if(move != null){
                stateBoard = move;
                opponentHand = getNewHand(initalBoard, stateBoard, opponentHand);
                }
                else{
                    opponentHand.add(pile.remove(0));
                }
                player = 0;
            }
        }

         int handtally = tallyHand(stateHand);
        return (100 - handtally)/100; 
    }

    private static int tallyHand(ArrayList<Tile> stateHand){
        int count = 0;
        for(Tile tile : stateHand){
            count = count + tile.getNumber();
        }
        return count;
    }

    private double simulateNN(ArrayList<ArrayList<Tile>> stateBoard, ArrayList<Tile> stateHand, ArrayList<Tile> opponentHand, ArrayList<Tile> pile){

        return 0;
    }


    private static void backpropagate(Node node, double reward){
        while (node.getParent() != null) {
            
            node.updateScore(reward);
            node = node.getParent();
        }
    }

    private static Node selectBestMove(Node root){
        List<Node> children = root.getChildren();
        double bestReward = 0;
        Node bestNode = null;
        for(Node child : children){
            if(child.getReward() > bestReward){
                bestReward = child.getReward();
                bestNode = child;
            }
        }
        return bestNode;
    }

}
