package com.rummikub.game.MCTS;

import java.util.ArrayList;
import java.util.List;
import com.rummikub.game.Tile;



public class TreeSearchCollaborative {
    public static ArrayList<ArrayList<Tile>> treeSearch(ArrayList<ArrayList<Tile>> stateBoard, ArrayList<Tile> stateHand){
        Node root = new Node(stateBoard, stateHand);
        return selectBestMove(root).getStateBoard();
    }
    public static Node selectBestMove(Node root){
        List<Node> children = root.getChildren();
        double bestReward = 0;
        double childReward = 0;
        Node bestNode = root;
        for(Node child : children){
            for(ArrayList<Tile> set : child.getStateBoard()){
                childReward += set.size();
            }
            if(childReward > bestReward){
                bestReward = childReward;
                bestNode = child;
            }
            childReward = 0;
        }
        return bestNode;
    }

    private  ArrayList<Tile> getNewHand(ArrayList<ArrayList<Tile>> solutionBoard, ArrayList<ArrayList<Tile>> intialBoard, ArrayList<Tile> hand){
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

    private  ArrayList<Tile> removeUsedTile(ArrayList<Tile> hand, ArrayList<Tile> used){
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

    private  boolean isTilePresent(Tile tile, ArrayList<ArrayList<Tile>> list) {
        // Check if the tile is present in any sublist of the list
        for (ArrayList<Tile> sublist : list) {
            if (sublist.contains(tile)) {
                return true;
            }
        }
        return false;
    }


    public static ArrayList<ArrayList<Tile>> bestBoard(ArrayList<ArrayList<ArrayList<Tile>>> solutions) {
        int valueOfBoard = 0;
        ArrayList<ArrayList<Tile>> bestBoard = new ArrayList<ArrayList<Tile>>();

        for (ArrayList<ArrayList<Tile>> board : solutions) {
            int value = getValueOfBoard(board);
            if (value > valueOfBoard) {
                bestBoard = board;
                valueOfBoard = value;
            }
        }
        return bestBoard;
    }

    static int getValueOfBoard(ArrayList<ArrayList<Tile>> board) {
        int numberOfTiles = 0;

        for (ArrayList<Tile> move : board) {
            numberOfTiles += move.size();
        }
        return numberOfTiles;
    }
}
