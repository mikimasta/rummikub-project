package com.rummikub.game;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import java.util.HashSet;

public class AI {

    /**
     * looks for good moves
     * @param rack rack of tiles 
     * @param board board of the game
     * @return the best outcome adding the board to the rack
     */
    public static ArrayList<Tile> possibleMoveAddingRackToBoard(Tile[][] dupRack, Tile[][] board){

        ArrayList<Tile> combined = new ArrayList<Tile>(new HashSet<Tile>(BaselineAgent.TwodArrayToArrayList(dupRack)));
        combined.addAll(BaselineAgent.TwodArrayToArrayList(board)); // arraylist combining rack and board tiles

        ArrayList<Tile> groups = new ArrayList<>(combined); 
        Game.orderRackByGroup(groups); // arraylist of ordered tiles by groups

        ArrayList<Tile> runs = new ArrayList<>(combined); 
        Game.orderRackByStairs(runs); // if joker it will be at the front

        ArrayList<Tile> move = new ArrayList<>(); 
        ArrayList<ArrayList<Tile>> listOfMoves = new ArrayList<ArrayList<Tile>>();
        ArrayList<ArrayList<Tile>> prevListOfMoves = new ArrayList<ArrayList<Tile>>();
        ArrayList<Tile> finalMove = new ArrayList<>();

        int move_size = 3;
        while (true) {
            for (int i = 0; i < groups.size() - move_size + 1; i++) {
                for (int j = 0; j < move_size; j++) {
                    move.add(groups.get(i + j));
                }
                if (Game.checkIfGroup(move) || Game.checkIfStairs(move)) { 
                    listOfMoves.add(new ArrayList<>(move));
                }
                move.clear();
            }
            for (int i = 0; i < runs.size() - move_size + 1; i++) {
                for (int j = 0; j < move_size; j++) {
                    move.add(runs.get(i + j));
                }
                if (Game.checkIfGroup(move) || Game.checkIfStairs(move)) {
                    listOfMoves.add(new ArrayList<>(move));
                }
                move.clear();
            }
            if (listOfMoves.size() == 0) {
                listOfMoves = prevListOfMoves;
                break;
            } else {
                prevListOfMoves = new ArrayList<>(listOfMoves);
                listOfMoves.clear();
            }
            move_size++;
        }

        listOfMoves = BaselineAgent.findNonOverlappingMoves(listOfMoves);
        finalMove = nestedArrayListToArrayList(listOfMoves);
        
        // check if tiles on the board are still on the board
        if (finalMove.containsAll(BaselineAgent.TwodArrayToArrayList(board))){
            System.out.println(BaselineAgent.printListTiles(finalMove));
            return finalMove;
        } else {
            System.out.println("unable to use it");
            return null;
        }
        
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

    public static void main(String[] args) {

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

        possibleMoveAddingRackToBoard(rack, board);
    }
}
