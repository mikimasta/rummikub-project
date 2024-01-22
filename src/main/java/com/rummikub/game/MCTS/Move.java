package com.rummikub.game.MCTS;


import com.rummikub.game.BaselineAgent;
import com.rummikub.game.SingleTileAgent;
import com.rummikub.game.SplittingAgent;
import com.rummikub.game.Tile;
import com.rummikub.gui.RackGUI;

import java.util.ArrayList;
import java.util.HashSet;


public class Move {

    public static ArrayList<ArrayList<Tile>> getMove(ArrayList<Tile> initalHand, ArrayList<ArrayList<Tile>> initalBoard){
        
        Tile[][] rack = hand2ArrayList(initalHand);
        Tile[][] board = board2matrix(initalBoard);
       
        ArrayList<ArrayList<Tile>> aimove = BaselineAgent.baselineAgent(rack);
        if (aimove != null && !aimove.isEmpty() && aimove.size() > 0) {
            System.out.println("move with baseline agent");
           // System.out.println(BaselineAgent.printMoves(aimove));
            Tile[][] newHand = removeTilesFromRack(aimove, rack);
            RackGUI.getInstance().handToRack(newHand);
            //System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));
        } 
        aimove = SingleTileAgent.singleTilemove(rack,board);
        if (aimove != null && !aimove.isEmpty()) { // && aimove.size() > 0
            System.out.println("Move with single tiles");
            System.out.println(BaselineAgent.printMoves(aimove));
        }
        aimove = SplittingAgent.splittingMoves(rack, board);
        if (aimove != null && !aimove.isEmpty()) { // && aimove.size() > 0
            System.out.println("Move with splitting method");
            System.out.println(BaselineAgent.printMoves(aimove));
        }

        return aimove;
    }
    public static Tile[][] hand2ArrayList(ArrayList<Tile> hand){
        Tile[][] matrix = new Tile[2][15];

        int rowIndex = 0;
        int colIndex = 0;

        for (Tile tile : hand) {
            matrix[rowIndex][colIndex] = tile;
            colIndex++;

            // Check if the row is full or if a 0 is encountered
            if (colIndex == 15) {
                rowIndex++;
                colIndex = 0;

                // Check if the matrix is full
                if (rowIndex == 2) {
                    break;
                }
            }
        }
        return matrix;
    }

    public static Tile[][] board2matrix(ArrayList<ArrayList<Tile>> board) {
        Tile[][] matrix = new Tile[7][20];

        int rowIndex = 0;
        int colIndex = 0;

        for (ArrayList<Tile> innerList : board) {
            if(colIndex+ innerList.size() > 20){
                //System.out.println("change row");
                rowIndex++;
                colIndex = 0;
            }
            for (Tile tile : innerList) {
                matrix[rowIndex][colIndex] = tile;
                colIndex++;
            }
            colIndex++;
        }
        return matrix;
    }


       /**
     * removes tiles used in the AI move from its rack 
     * @param aiMoves list of moves of tiles
     * @param rack gameboard
     * @return updated gameboard
     */
    static Tile[][] removeTilesFromRack(ArrayList<ArrayList<Tile>> aiMoves, Tile[][] rack) {
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

}

