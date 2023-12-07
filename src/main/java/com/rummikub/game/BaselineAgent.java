package com.rummikub.game;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class BaselineAgent {
    
    public ArrayList<Tile> baselineAgent(Tile[][] rack){

        ArrayList<Tile> groups = new ArrayList<>(TwodArrayToArrayList(rack)); 
        Game.orderRackByGroup(groups); // arraylist of ordered tiles by groups
        printListTiles(groups);

        ArrayList<Tile> runs = new ArrayList<>(TwodArrayToArrayList(rack)); 
        Game.orderRackByStairs(runs); // if joker it will be at the front, 
        printListTiles(runs);

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
                }
            } 
        }
        actualMoveGroup = findLargestMove(listOfMovesGroups);
        printListTiles(actualMoveGroup);
                
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
        actualMoveRun = findLargestMove(listOfMovesRuns);
        printListTiles(actualMoveRun);

        if (isTwoMovesPossible(actualMoveRun, actualMoveGroup)) { // two moves are possible
            ArrayList<Tile> twoMoves = new ArrayList<>();
            twoMoves.addAll(actualMoveRun); 
            twoMoves.add(null);
            twoMoves.addAll(actualMoveGroup);
            printListTiles(actualMoveGroup); System.out.print("AND ");
            printListTiles(actualMoveRun);
            return twoMoves;
        }else{ // two moves are not possible do largest move
            if (actualMoveGroup.size() > actualMoveRun.size()) {
                printListTiles(actualMoveGroup);
                return actualMoveGroup;
            }else{
                printListTiles(actualMoveRun);
                return actualMoveRun;
            }
        }
        // no moves possible
    }

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

    boolean isTwoMovesPossible(ArrayList<Tile> runMove, ArrayList<Tile> groupMove) {
        for (Tile tile : runMove) {
            if (groupMove.contains(tile)) {
                return false; // only one move is possible
            }
        }
        return true; // two moves are possible
    }
    
    ArrayList<Tile> findLargestMove(ArrayList<ArrayList<Tile>> listOfMovesRuns) {
        ArrayList<Tile> largestMove = listOfMovesRuns.get(0);
        for (ArrayList<Tile> move : listOfMovesRuns) {
            if (move.size() > largestMove.size()) {
                largestMove = move;
            }
        }
        return largestMove;
    }
        
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

    String printListTiles(ArrayList<Tile> list){
        String s = "";
        for (int i = 0; i < list.size(); i++) {
            s += list.get(i).getNumber() + " " + list.get(i).getColorString() + " - ";
        }
        System.out.println(s);
        return s;
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
        test.baselineAgent(rack);

    }
}
