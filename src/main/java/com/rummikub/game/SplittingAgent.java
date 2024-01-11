package com.rummikub.game;

import java.util.ArrayList;
import java.util.Iterator;
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

        ArrayList<ArrayList<Tile>> runs = SingleTileAgent.listOfMovesRuns(board);
        if (runs == null || runs.isEmpty()) {
            return null;
        }
    
        ArrayList<ArrayList<Tile>> splitMove = new ArrayList<>();
        ArrayList<Tile> rack = new ArrayList<>();
    
        // remove moves with size less than 3
        Iterator<ArrayList<Tile>> iterator = runs.iterator();
        while (iterator.hasNext()) {
            ArrayList<Tile> move = iterator.next();
            if (move.size() < 3) {
                iterator.remove();
            }
        }
    
        for (ArrayList<Tile> move : runs) {
            ArrayList<ArrayList<Tile>> split = splitMoves(move);
        
            System.out.println("Splitted moves:  " + BaselineAgent.printMoves(split));
        
            for (ArrayList<Tile> splittedMove : split) {
                ArrayList<ArrayList<Tile>> extendMove = new ArrayList<>();
                extendMove.add(0, splittedMove);
                System.out.println("move before expansion : " + BaselineAgent.printMoves(extendMove));;
                extendMove = SingleTileAgent.runMoves(dupRack, extendMove);
                System.out.println("move after expansion : " + BaselineAgent.printMoves(extendMove));
    
                if (extendMove != null && !extendMove.isEmpty() && Game.checkIfStairs(extendMove.get(0))) {
                    rack.removeAll(extendMove.get(0));
                    dupRack = BaselineAgent.arrayListToRack(rack, dupRack);
                    System.out.println("Split is valid");
    
                    splitMove.add(extendMove.get(0));
    
                    // add the rest of the original move to the splitMove
                    ArrayList<Tile> restOfMove = new ArrayList<>(move);
                    restOfMove.removeAll(extendMove.get(0));
                    splitMove.add(restOfMove);
                } else {
                    System.out.println("Extend move is empty or not valid.");
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
        splittedMoves.add(new ArrayList<>(move));

        for (int i = 1; i < move.size(); i++) {
            splittedMoves.add(new ArrayList<>(move.subList(0, i)));
            splittedMoves.add(new ArrayList<>(move.subList(i, move.size())));
        }
    
        return splittedMoves;
    }

    public static void main(String[] args) {

        Tile n = null;
        Tile j = new Tile((byte) -1, Color.RED);

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
            {n, n, n,n, n, n, n, t6B, t7B, t8B, t9B, t10B, t11B, t12B, t13B}
        };
        
        System.out.println(Game.printBoard(board));
        Tile[][]  rack = {{n, n, n, t9Bbis, n, n, n, n, n, n, n, n, n, n, n}};

        ArrayList<ArrayList<Tile>> splitMoves = splittingMoves(rack, board);
        if (splitMoves != null && !splitMoves.isEmpty()) {
            System.out.println("The move split : " + BaselineAgent.printMoves(splitMoves));
        }else {
            System.out.println("no move");
        }
        

        
    }
}
