package com.rummikub.model;

import java.util.ArrayList;
import javafx.scene.paint.Color;

import com.rummikub.game.Tile;
import com.rummikub.game.Game;


public class BaseImplementation {

    /**
     * looks for good moves
     * @param rack rack of tiles 
     * @param board board of the game
     * @return the best outcome adding the board to the rack
     */
    public ArrayList<Tile> possibleMoveAddingRackToBoard(Tile[][] rack, Tile[][] board){

        ArrayList<Tile> combined = new ArrayList<>(BaselineAgent.TwodArrayToArrayList(rack)); 
        combined.addAll(BaselineAgent.TwodArrayToArrayList(board)); // arraylist combining rack and board tiles

        ArrayList<Tile> groups = Game.orderRackByGroup(combined);

        ArrayList<Tile> runs = new ArrayList<>(combined); 
        Game.orderRackByStairs(runs); // if joker it will be at the front

        // now check if there is a move possible for groups
        ArrayList<Tile> move = new ArrayList<>(); 
        ArrayList<Tile> actualMoveGroup = new ArrayList<>();
        ArrayList<ArrayList<Tile>> listOfMovesGroups = new ArrayList<ArrayList<Tile>>();
        for (int i = 0; i < groups.size(); i++) {
            Tile tile = groups.get(i);
            move.add(tile);
            if (move.size() > 2) {
                if (Game.checkIfGroup(move)) {
                    actualMoveGroup = new ArrayList<>(move);
                    listOfMovesGroups.add(actualMoveGroup);
                } else {
                    move.clear();
                    move.add(groups.get(i-1)); // added
                    move.add(tile);
                }
            } 
        }
        //actualMoveGroup = BaselineAgent.findLargestMove(listOfMovesGroups); // largest move for groups
        ArrayList<Tile> groupMove = nestedArrayListToArrayList(findNonOverlappingMoves(listOfMovesGroups));
        System.out.println("moves for groups");        
        BaselineAgent.printListTiles(groupMove);
        
        // now check if there is a move possible for runs
        move.clear();
        ArrayList<Tile> actualMoveRun = new ArrayList<>();
        ArrayList<ArrayList<Tile>> listOfMovesRuns = new ArrayList<ArrayList<Tile>>();
        for (int i = 0; i < runs.size(); i++) {
            Tile tile = runs.get(i);
            move.add(tile);
            if (move.size() > 2) {
                if (Game.checkIfStairs(move)) {
                    actualMoveRun = new ArrayList<>(move);
                    listOfMovesRuns.add(actualMoveRun); 
                } else {
                    move.clear();
                    move.add(runs.get(i-1));
                    move.add(tile);
                }
            } 
        }
        //actualMoveRun = BaselineAgent.findLargestMove(listOfMovesRuns);
        ArrayList<Tile> runMove = nestedArrayListToArrayList(findNonOverlappingMoves(listOfMovesRuns));
        System.out.println("moves for runs");
        BaselineAgent.printListTiles(runMove);

        ArrayList<Tile> finalMove = BaselineAgent.chooseBestMove(runMove, groupMove);

        // check if tiles on the board are still on the board
        if (finalMove.containsAll(BaselineAgent.TwodArrayToArrayList(board))){
            return finalMove;
        }
        return null;
        
    }

    /**
     * transforms a list of moves to an arraylist of moves with null tiles between each of them
     * @param listOfMoves list of moves
     * @return an arraylist of moves
     */
    static ArrayList<Tile> nestedArrayListToArrayList(ArrayList<ArrayList<Tile>> listOfMoves){
        ArrayList<Tile> move = new ArrayList<>();
        for (ArrayList<Tile> moveRun : listOfMoves) {
            move.addAll(moveRun);
            move.add(null);
        }
        return move;
    }

    /**
     * finds all moves that don't use the same tiles
     * @param listOfMovesGroups list of moves
     * @return list of moves that don't use the same tiles
     */
    static ArrayList<ArrayList<Tile>> findNonOverlappingMoves(ArrayList<ArrayList<Tile>> listOfMoves) {
        ArrayList<ArrayList<Tile>> notOverlappingMoves = new ArrayList<>();

        for (int i = 0; i < listOfMoves.size(); i++) {
            ArrayList<Tile> currentMove = listOfMoves.get(i);
            boolean isOverlapping = false;
            // Compare currentMove with previously selected non-overlapping moves
            for (ArrayList<Tile> selectedMove : notOverlappingMoves) {
                if (!BaselineAgent.isTwoMovesPossible(currentMove, selectedMove)) {
                    isOverlapping = true;
                    break;
                }
            }
            // If currentMove doesn't overlap, add it to nonOverlappingMoves
            if (!isOverlapping) {
                notOverlappingMoves.add(currentMove);
            }
        }

        return notOverlappingMoves;
    }

    public static void main(String[] args) {
        BaseImplementation test = new BaseImplementation();

        Tile n = null;
        Tile j = new Tile((byte) -1, Color.RED);
        Tile t7R = new Tile((byte) 7, Color.RED);
        Tile t8R = new Tile((byte) 8, Color.RED);
        Tile t9R = new Tile((byte) 9, Color.RED);
        Tile t10R = new Tile((byte) 10, Color.RED);

        Tile t10O = new Tile((byte) 10, Color.ORANGE);
        Tile t10B = new Tile((byte) 10, Color.BLUE);
        Tile t10Bl = new Tile((byte) 10, Color.BLACK);

        Tile t4R = new Tile((byte) 4, Color.RED);

        Tile[][]  rack = {{t4R, n, t10R, n, n, t9R, n, t10O, n, t7R, t9R, t8R, n, t10B, n}};

        Tile t5R = new Tile((byte) 5, Color.RED);
        Tile t6R = new Tile((byte) 6, Color.RED);

        Tile t2O = new Tile((byte) 2, Color.ORANGE);
        Tile t2B = new Tile((byte) 2, Color.BLUE);
        Tile t2R = new Tile((byte) 2, Color.BLACK);
        Tile[][]  board = {{n, t5R, t6R, t7R, n, n, n, t2O, t2B, t2R, n, n, n, n, n}};

        test.possibleMoveAddingRackToBoard(rack, board);
    }
}
