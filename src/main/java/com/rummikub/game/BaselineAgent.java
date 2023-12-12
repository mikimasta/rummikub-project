package com.rummikub.game;

import java.util.ArrayList;
import java.util.HashSet;

import javafx.scene.paint.Color;
public class BaselineAgent {

    /**
     * takes the rack and tries to make moves based on the tiles in that rack
     * @param rack rack of tiles
     * @return the best move to make based on that rack
     */
    public static ArrayList<Tile> BaselineAgent(Tile[][] dupRack){
        ArrayList<Tile> rack = new ArrayList<Tile>(new HashSet<Tile>(TwodArrayToArrayList(dupRack)));

        ArrayList<Tile> groups = new ArrayList<>(rack);
        Game.orderSetGroup(groups); // arraylist of ordered tiles by groups

        ArrayList<Tile> runs = new ArrayList<>(rack);
        Game.orderSetStairs(runs); // if joker it will be at the front

        // now check if there is a move possible for groups
        ArrayList<Tile> move = new ArrayList<>();
        ArrayList<Tile> actualMoveGroup = new ArrayList<>();
        ArrayList<ArrayList<Tile>> listOfMovesGroups = new ArrayList<ArrayList<Tile>>();
        ArrayList<ArrayList<Tile>> prevListOfMoves = new ArrayList<ArrayList<Tile>>();
        ArrayList<Tile> finalMove = new ArrayList<>();


        int move_size = 3;
        while (true) {
            System.out.println(move_size);
            for (int i = 0; i < groups.size() - move_size + 1; i++) {
                for (int j = 0; j < move_size; j++) {
                    move.add(groups.get(i + j));
                }
                if (Game.checkIfGroup(move) || Game.checkIfStairs(move)) {
                    System.out.println(printListTiles(move));
                    listOfMovesGroups.add(new ArrayList<>(move));
                }
                move.clear();
            }
            for (int i = 0; i < runs.size() - move_size + 1; i++) {
                for (int j = 0; j < move_size; j++) {
                    move.add(runs.get(i + j));
                }
                if (Game.checkIfGroup(move) || Game.checkIfStairs(move)) { // TODO discard a group if it has two tiles of the same color
                    System.out.println(printListTiles(move));
                    listOfMovesGroups.add(new ArrayList<>(move));
                }
                move.clear();
            }
            if (listOfMovesGroups.size() == 0) {
                listOfMovesGroups = prevListOfMoves;
                break;
            } else {
                prevListOfMoves = new ArrayList<>(listOfMovesGroups);
                listOfMovesGroups.clear();
            }
            move_size++;
        }
        System.out.println(listOfMovesGroups.size());


        //actualMoveGroup = findLargestMove(listOfMovesGroups); // largest move for groups
        // printListTiles(actualMoveGroup);

        // TODO check every combinaion of three, if there is one, check n+1 (while loop)


        // actualMoveRun = findLargestMove(listOfMovesRuns); // largest move for runs
        // printListTiles(actualMoveRun);

        // ArrayList<Tile> finalMove = chooseBestMove(actualMoveRun, actualMoveGroup);
        finalMove = AI.nestedArrayListToArrayList(listOfMovesGroups);
        return finalMove;
    }

    /**
     * changes an arraylist to a 2D array (the rack)
     * @param list list of tiles
     * @param rack rack of tiles
     * @return a rack of tiles
     */
    public static Tile[][] arrayListToRack(ArrayList<Tile> list, Tile[][] rack) {
        // Clear the rack
        for (int i = 0; i < rack.length; i++) {
            for (int y = 0; y < rack[i].length; y++) {
                rack[i][y] = null;
            }
        }

        // populate the 2D array with the sorted tiles
        int x = 0;
        for (int i = 0; i < rack.length; i++) {
            for (int y = 0; y < rack[0].length; y++) {
                rack[i][y] = list.get(x);
                x++;
            }
        }
        return rack;
    }

    /**
     * are two moves possible ? yes if they use distinct tiles
     * @param runMove move of tiles forming runs
     * @param groupMove move of tiles forming groups
     * @return true if two moves are possible
     */
    static boolean isTwoMovesPossible(ArrayList<Tile> runMove, ArrayList<Tile> groupMove) {
        if (runMove == null || groupMove == null){
            return false;
        } else {
            for (Tile tile : runMove) {
                if (groupMove.contains(tile) && tile != null) {
                    return false; // only one move is possible
                }
            }
            return true; // two moves are possible
        }

    }

    /**
     * find moves using the largest amount of tiles in a list of list of moves
     * @param listOfMoves list of moves
     * @return arraylist of largest moves
     */
    static ArrayList<Tile> findLargestMove(ArrayList<ArrayList<Tile>> listOfMoves) {
        if (listOfMoves.size() == 0){
            return null;
        }else{
            ArrayList<Tile> largestMove = listOfMoves.get(0);
            for (ArrayList<Tile> move : listOfMoves) {
                if (move.size() > largestMove.size()) {
                    largestMove = move;
                }
            }
            return largestMove;
        }
    }

    /**
     * transforms a 2D array to an arraylist
     * @param arr 2D array
     * @return an arraylist
     */
    public static ArrayList<Tile> TwodArrayToArrayList(Tile[][] arr){
        ArrayList<Tile> arrList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] != null) {
                    arrList.add(arr[i][j]);
                }
            }
        }
        return arrList;
    }

    /**
     * prints an arraylist of tiles
     * @param list list of tiles
     * @return string of tiles with number and color
     */
    public static String printListTiles(ArrayList<Tile> list){
        if (list == null){
            return null;
        }else{
            String s = "";
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null){
                    s += " null - ";
                }else{
                    s += list.get(i).getNumber() + " " + list.get(i).getColorString() + " - ";
                }
            }
            System.out.println(s);
            return s;
        }

    }


    /**
     * chooses the best move to make
     * @param movesGroup move of tiles forming groups
     * @param movesRun move of tiles forming runs
     * @return the best move
     */
    static ArrayList<Tile> chooseBestMove(ArrayList<Tile> movesGroup, ArrayList<Tile> movesRun) {
        if (movesGroup == null || movesRun == null){
            return null;
        } else {
            if (isTwoMovesPossible(movesGroup, movesRun)) {
                ArrayList<Tile> twoMoves = new ArrayList<>(movesGroup);
                twoMoves.add(null);
                twoMoves.addAll(movesRun);
                System.out.println("Two moves are possible");
                printListTiles(twoMoves);
                return twoMoves;
            } else {
                System.out.println("Choosing the largest move");
                if (movesGroup.size() > movesRun.size()) {
                    printListTiles(movesGroup);
                    return movesGroup;
                }else{
                    printListTiles(movesRun);
                    return movesRun;
                }
            }
        }
    }

    public static void main(String[] args) {
        BaselineAgent test = new BaselineAgent();

        Tile n = null;
        Tile j = new Tile((byte) -1, Color.RED);
        Tile t11R = new Tile((byte) 7, Color.RED);
        Tile t12R = new Tile((byte) 8, Color.RED);
        Tile t13R = new Tile((byte) 9, Color.RED);
        Tile t1R = new Tile((byte) 10, Color.RED);

        Tile t10O = new Tile((byte) 10, Color.ORANGE);
        Tile t10B = new Tile((byte) 10, Color.BLUE);
        Tile t10R = new Tile((byte) 10, Color.BLACK);

        Tile t4R = new Tile((byte) 4, Color.RED);
        Tile t7R = new Tile((byte) 7, Color.RED);

        Tile[][]  rack = {{t4R, n, t10R, n, n, t7R, n, t10O, n, t11R, t13R, t12R, j, t10B, n}};
        test.BaselineAgent(rack);
    }
}
