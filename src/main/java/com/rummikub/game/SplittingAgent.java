package com.rummikub.game;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class SplittingAgent {

    /**
     * splits runs into consecutive sub-moves and validates them for staircase patterns
     * @param dupRack players rack
     * @param board board
     * @return list of moves representing the split moves
     */
    public static ArrayList<ArrayList<Tile>> splittingMoves(Tile[][] dupRack, Tile[][] board) {
        if (board == null) {
            return null;
        }

        ArrayList<ArrayList<Tile>> runs = new ArrayList<ArrayList<Tile>>(SingleTileAgent.listOfMovesRuns(board, 4));
        if (runs == null || runs.isEmpty()) {
            return null;
        }
    
        ArrayList<ArrayList<Tile>> splitMove = new ArrayList<ArrayList<Tile>>();
        ArrayList<Tile> rack = new ArrayList<>();
    
        for (ArrayList<Tile> move : runs) {
            ArrayList<ArrayList<Tile>> split = new ArrayList<ArrayList<Tile>>(splitMoves(move));
            
            // System.out.println("Splitted moves:  " + BaselineAgent.printMoves(split));
        
            for (ArrayList<Tile> splittedMove : split) {
                ArrayList<ArrayList<Tile>> extendMove = new ArrayList<ArrayList<Tile>>();
                extendMove.add(0, splittedMove);
                extendMove = SingleTileAgent.runMoves(dupRack, extendMove);
                ArrayList<Tile> restOfMove = new ArrayList<>(move);

                if (extendMove != null && !extendMove.isEmpty() && Game.checkIfStairs(extendMove.get(0))) {
                    restOfMove.removeAll(extendMove.get(0));
                    
                    if (restOfMove != null && !restOfMove.isEmpty() && Game.checkIfStairs(restOfMove)) {
                        rack.removeAll(extendMove.get(0));
                        dupRack = BaselineAgent.arrayListToRack(rack, dupRack);

                        System.out.println("Split is valid");
                        System.out.println("Original move : " + BaselineAgent.printMove(move) + " split into : " + BaselineAgent.printMove(extendMove.get(0)) + " and : " + BaselineAgent.printMove(restOfMove));

                        splitMove.add(extendMove.get(0));
                        splitMove.add(restOfMove); // add the rest of the original move to the splitMove
                    }
                } else {
                    // System.out.println("Extended move is empty or not valid");
                }
            }
        }
    
        return splitMove;
    }
    
    /**
     * splits a given move into consecutive sub-moves, for example, a move of size 3 (1-2-3) would be split into 5 moves: 1, 2-3, 1-2, 3, 1-2-3
     * @param move the move to be split
     * @return list containing the sub-moves generated by splitting the original move
     */
    static ArrayList<ArrayList<Tile>> splitMoves(ArrayList<Tile> move) {
        ArrayList<ArrayList<Tile>> splittedMoves = new ArrayList<ArrayList<Tile>>();
        //splittedMoves.add(new ArrayList<>(move));

        for (int i = 1; i < move.size(); i++) {
            splittedMoves.add(new ArrayList<>(move.subList(0, i)));
            splittedMoves.add(new ArrayList<>(move.subList(i, move.size())));
        }
    
        return splittedMoves;
    }

    // step 1: take groups of tile that are size 4
    // step 2: reorder in different combinations ex:(1, 2, 3, 4) -> 
    // step 3: 

    /* 
    public static ArrayList<ArrayList<Tile>> splittingMovesForRuns(Tile[][] dupRack, Tile[][] board) {
        if (board == null) {
            return null;
        }

        ArrayList<ArrayList<Tile>> groups = new ArrayList<ArrayList<Tile>>(SingleTileAgent.listOfMovesGroups(board, 4));
        if (groups == null || groups.isEmpty()) {
            return null;
        }

        for (ArrayList<Tile> group : groups) {
            ArrayList<Tile> possibleTiles = 
        }
        
    }
*/
    // For experiments, run many time how many times does it win, 
    // estimating how many times it wins

    public static void main(String[] args) {

        Tile n = null;
        Tile j = new Tile((byte) -1, Color.RED);

        Tile t4B = new Tile((byte) 4, Color.BLACK);
        Tile t5B = new Tile((byte) 5, Color.BLACK);
        Tile t6B = new Tile((byte) 6, Color.BLACK);
        Tile t7B = new Tile((byte) 7, Color.BLACK);
        Tile t8B = new Tile((byte) 8, Color.BLACK);
        Tile t9B = new Tile((byte) 9, Color.BLACK);
        Tile t10B = new Tile((byte) 10, Color.BLACK);
        Tile t11B = new Tile((byte) 11, Color.BLACK);
        Tile t12B = new Tile((byte) 12, Color.BLACK);
        Tile t13B = new Tile((byte) 13, Color.BLACK);


        Tile t9Bbis = new Tile((byte) 9, Color.BLACK);
        
        Tile[][]  board = {
            {n, n, n,n, n, t4B, t5B, j, t7B, t8B, n, n, n, n, n}
        };
        
        System.out.println(Game.printBoard(board));
        Tile[][]  rack = {{n, n, n, t6B, n, n, n, n, n, n, n, n, n, n, n}};

        ArrayList<ArrayList<Tile>> splitMoves = splittingMoves(rack, board);
        if (splitMoves != null && !splitMoves.isEmpty()) {
            System.out.println("The move split : " + BaselineAgent.printMoves(splitMoves));
        }else {
            System.out.println("no move");
        }
        

        
    }
}
