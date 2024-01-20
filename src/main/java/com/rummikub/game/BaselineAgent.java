package com.rummikub.game;

import java.util.ArrayList;
import java.util.HashSet;
import javafx.scene.paint.Color;

public class BaselineAgent {
    
    /**
     * takes the rack and tries to make moves based on the tiles in that rack
     * @param rack rack of tiles
     * @return the possible moves to make based on the players rack
     */
    public static ArrayList<ArrayList<Tile>> baselineAgent(Tile[][] dupRack){
        ArrayList<Tile> rack = new ArrayList<Tile>(new HashSet<Tile>(TwodArrayToArrayList(dupRack)));

        ArrayList<Tile> groups = new ArrayList<>(rack); 
        Game.orderRackByGroup(groups); // arraylist of ordered tiles by groups

        ArrayList<Tile> runs = new ArrayList<>(rack); 
        Game.orderRackByStairs(runs); 

        ArrayList<Tile> move = new ArrayList<>(); 
        ArrayList<ArrayList<Tile>> listOfMoves = new ArrayList<ArrayList<Tile>>();
        ArrayList<ArrayList<Tile>> prevListOfMoves = new ArrayList<ArrayList<Tile>>();

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

        return findNonOverlappingMoves(listOfMoves);
    }


    /**
     * takes the rack and the board ordered them and makes moves with them comined and ordered
     * @param rack rack of tiles 
     * @param board board of the game
     * @return the best outcome adding the board to the rack
     */
    public static ArrayList<ArrayList<Tile>> possibleMoveAddingRackToBoard(Tile[][] dupRack, Tile[][] board){

        ArrayList<Tile> combined = new ArrayList<Tile>(new HashSet<Tile>(BaselineAgent.TwodArrayToArrayList(dupRack)));
        combined.addAll(BaselineAgent.TwodArrayToArrayList(board)); // arraylist combining rack and board tiles

        ArrayList<Tile> groups = new ArrayList<>(combined); 
        Game.orderRackByGroup(groups); // arraylist of ordered tiles by groups

        ArrayList<Tile> runs = new ArrayList<>(combined); 
        Game.orderRackByStairs(runs); // if joker it will be at the front

        ArrayList<Tile> move = new ArrayList<>(); 
        ArrayList<ArrayList<Tile>> listOfMoves = new ArrayList<ArrayList<Tile>>();
        ArrayList<ArrayList<Tile>> prevListOfMoves = new ArrayList<ArrayList<Tile>>();
        ArrayList<ArrayList<Tile>> finalMove = new ArrayList<ArrayList<Tile>>();

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

        finalMove = findNonOverlappingMoves(listOfMoves);
        // finalMove = SingleTileAgent.singleTilemove(dupRack, AIGameScreen.);

        // TODO : add the single tiles to that and the split method

        // check if tiles on the board are still on the board
        if ((BaselineAgent.nestedArrayListToArrayList(finalMove)).containsAll(BaselineAgent.TwodArrayToArrayList(board))){
            return finalMove;
        } else {
            return null;
        }
        
    }

    /**
     * changes an arraylist to a 2D array (the rack)
     * @param list list of tiles
     * @param rack rack of tiles
     * @return a rack of tiles
     */
    public static Tile[][] arrayListToRack(ArrayList<Tile> list, Tile[][] rack) {
        // Clear the rack
        int x = 0;
        for (int i = 0; i < rack.length; i++) {
            for (int y = 0; y < rack[i].length; y++) {
                if (list.size() != x) {
                    rack[i][y] = list.get(x);
                    x++;
                } else {
                    rack[i][y] = null;
                }
            }
        }

        return rack;
    }
    
    /**
     * transforms a list of moves to an arraylist of moves with null tiles between each of them
     * @param listOfMoves list of moves
     * @return an arraylist of moves
     */
    static ArrayList<Tile> nestedArrayListToArrayList(ArrayList<ArrayList<Tile>> listOfMoves){
        if (listOfMoves == null) {
            return null;
        } else {
            ArrayList<Tile> move = new ArrayList<>();
            for (ArrayList<Tile> mov : listOfMoves) {
                move.addAll(mov);
                move.add(null);
            }
            if (move.size() != 0 && move.get(move.size()-1) == null) {
                move.remove(move.size()-1);
            }
            return move;
        }
        
    }

    /**
     * find moves using the largest amount of tiles in a list of list of moves
     * @param listOfMoves list of moves
     * @return arraylist of largest moves
     */
    static ArrayList<Tile> findLargestMove(ArrayList<ArrayList<Tile>> listOfMoves) {
        if (listOfMoves.size() == 0) {
            return null;
        } else {
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
    public static String printMove(ArrayList<Tile> list){
        if (list == null){
            return null;
        }else{
            String s = "";
            for (Tile t : list) {
                if (t == null){
                    s += " null - ";
                }else{
                    s += t.getNumber() + " " + t.getColorString() + " - ";
                }
            }
            return s;
        }
    }

    public static String printMoves(ArrayList<ArrayList<Tile>> listOfMoves){
        if (listOfMoves == null){
            return null;
        }else{
            String s = "";
            for (ArrayList<Tile> move : listOfMoves) {
                for (Tile t : move) {
                    if (t != null) {
                        s += t.getNumber() + " " + t.getColorString() + " - ";
                    } else {
                        s += " null - ";
                    }
                }
                s += " AND - ";
            }
            return s;
        }
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
            // compare currentMove with previously selected non-overlapping moves
            for (ArrayList<Tile> selectedMove : notOverlappingMoves) {
                if (!isTwoMovesPossible(currentMove, selectedMove)) {
                    if (sum(currentMove) >= sum(selectedMove)) {
                        notOverlappingMoves.remove(selectedMove);
                        notOverlappingMoves.add(currentMove);
                    }
                    isOverlapping = true;
                    break;
                }
            }
            // if currentMove doesn't overlap, add it to nonOverlappingMoves
            if (!isOverlapping) {
                notOverlappingMoves.add(currentMove);
            }
        }

        return notOverlappingMoves;
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

    static int sum(ArrayList<Tile> move) {
        int sum = 0;
        for (Tile t : move) {
            sum += t.getNumber();
        }
        return sum;
    }
    public static boolean isGameOver2(Tile[][] rack) {
        if (rack == null) {
            System.out.println("game is over");
            return true;
        }
        System.out.println("le rack est  bon");
   return false;
}
 public static void main(String[] args) {

     Tile n = null;
     Tile j = new Tile((byte) -1, Color.RED);

     Tile t9B = new Tile((byte) 9, Color.BLACK);
     Tile t10B = new Tile((byte) 10, Color.BLACK);
     Tile t11B = new Tile((byte) 11, Color.BLACK);
     Tile t12Bl = new Tile((byte) 12, Color.BLUE);
     Tile t13B = new Tile((byte) 13, Color.BLACK);
     
     Tile[][]  board = {
         {n, n, n,n, n, n, j, t9B, t10B, t11B, t12Bl, n, n, n, n}
     };

     Tile[][]  rack = {{n, n, n, n, n, n, n, n, n, n, n, n, n, n, n}};

     Game test = new Game((byte) 3, false, (byte)1);
     System.out.println(" game is Over " + isGameOver2(rack));
 }

    /*
     *  
     * 
     * @param dupRack players rack
     * @return moves that the player can play using the rack with jokers@   q
     */
    /* 
    public static ArrayList<ArrayList<Tile>> jokerBaselineAgent(Tile[][] dupRack) {
        ArrayList<Tile> rackk = new ArrayList<>(TwodArrayToArrayList(dupRack));
        ArrayList<Tile> jokers = new ArrayList<>();
        
        for (int i = 0; i < rackk.size(); i++) {
            if (rackk.get(i).getNumber() == (byte) -1) {
                jokers.add(rackk.get(i));
            }
        }

        ArrayList<Tile> rack = new ArrayList<Tile>(new HashSet<Tile>(TwodArrayToArrayList(dupRack)));
        rack.removeAll(jokers); // remove all jokers
        ArrayList<Tile> groups = new ArrayList<>(rack); 
        Game.orderRackByGroup(groups); // arraylist of ordered tiles by groups

        ArrayList<Tile> runs = new ArrayList<>(rack); 
        Game.orderRackByStairs(runs); 

        ArrayList<Tile> move = new ArrayList<>(); 
        ArrayList<ArrayList<Tile>> listOfMoves = new ArrayList<ArrayList<Tile>>();
        ArrayList<ArrayList<Tile>> prevListOfMoves = new ArrayList<ArrayList<Tile>>();
        ArrayList<Tile> jokersCopy = new ArrayList<>(jokers);
        
        if (jokers.size() > 0) {
            int move_size = 2;
            while (true) {
                System.out.println(move_size);
                jokers.clear();
                jokers = jokersCopy;
                for (int i = 0; i < groups.size() - move_size + 1; i++) {
                    for (int j = 0; j < move_size; j++) {
                        move.add(groups.get(i + j));
                    }
                    move.add(jokers.get(0)); // add joker to set TODO problem here
                    if (Game.checkIfGroup(move) || Game.checkIfStairs(move)) { 
                        printMove(move);
                        listOfMoves.add(new ArrayList<>(move));
                        jokers.remove(0);
                    }
                    move.clear();
                }
                for (int i = 0; i < runs.size() - move_size + 1; i++) {
                    for (int j = 0; j < move_size; j++) {
                        move.add(runs.get(i + j));
                    }
                    move.add(jokers.get(0));
                    if (Game.checkIfGroup(move) || Game.checkIfStairs(move)) {
                        printMove(move);
                        listOfMoves.add(new ArrayList<>(move));
                        jokers.remove(0);
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
            System.out.println(listOfMoves.size());
        } else {
            int move_size = 3;
            while (true) {
                System.out.println(move_size);
                for (int i = 0; i < groups.size() - move_size + 1; i++) {
                    for (int j = 0; j < move_size; j++) {
                        move.add(groups.get(i + j));
                    }
                    if (Game.checkIfGroup(move) || Game.checkIfStairs(move)) { 
                        printMove(move);
                        listOfMoves.add(new ArrayList<>(move));
                    }
                    move.clear();
                }
                for (int i = 0; i < runs.size() - move_size + 1; i++) {
                    for (int j = 0; j < move_size; j++) {
                        move.add(runs.get(i + j));
                    }
                    if (Game.checkIfGroup(move) || Game.checkIfStairs(move)) {
                        printMove(move);
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
            System.out.println(listOfMoves.size());
        } 
        
        return findNonOverlappingMoves(listOfMoves);
    }
     */
}
