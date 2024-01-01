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
    public static ArrayList<Tile> baselineAgent(Tile[][] dupRack){
        ArrayList<Tile> rack = new ArrayList<Tile>(new HashSet<Tile>(TwodArrayToArrayList(dupRack)));

        ArrayList<Tile> groups = new ArrayList<>(rack); 
        Game.orderRackByGroup(groups); // arraylist of ordered tiles by groups

        ArrayList<Tile> runs = new ArrayList<>(rack); 
        Game.orderRackByStairs(runs); 

        ArrayList<Tile> move = new ArrayList<>(); 
        ArrayList<ArrayList<Tile>> listOfMoves = new ArrayList<ArrayList<Tile>>();
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
                    listOfMoves.add(new ArrayList<>(move));
                }
                move.clear();
            }
            for (int i = 0; i < runs.size() - move_size + 1; i++) {
                for (int j = 0; j < move_size; j++) {
                    move.add(runs.get(i + j));
                }
                if (Game.checkIfGroup(move) || Game.checkIfStairs(move)) {
                    System.out.println(printListTiles(move));
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

        listOfMoves = findNonOverlappingMoves(listOfMoves);
        finalMove = nestedArrayListToArrayList(listOfMoves);
        return finalMove;
    }

    public static ArrayList<Tile> jokerBaselineAgent(Tile[][] dupRack) {
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
        ArrayList<Tile> finalMove = new ArrayList<>();
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
                        System.out.println(printListTiles(move));
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
                        System.out.println(printListTiles(move));
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
                        System.out.println(printListTiles(move));
                        listOfMoves.add(new ArrayList<>(move));
                    }
                    move.clear();
                }
                for (int i = 0; i < runs.size() - move_size + 1; i++) {
                    for (int j = 0; j < move_size; j++) {
                        move.add(runs.get(i + j));
                    }
                    if (Game.checkIfGroup(move) || Game.checkIfStairs(move)) {
                        System.out.println(printListTiles(move));
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
        
        listOfMoves = findNonOverlappingMoves(listOfMoves);
        finalMove = nestedArrayListToArrayList(listOfMoves);
        return finalMove;
    }


    public static ArrayList<Tile> singleTilemove(Tile[][] dupRack, Tile[][] board) {
        if (board == null) {
            return null;
        }
    
        ArrayList<ArrayList<Tile>> movesGroups = listOfMovesGroups(board);
        ArrayList<ArrayList<Tile>> movesRuns = listOfMovesRuns(board);
        ArrayList<ArrayList<Tile>> extendedRuns = null;
        ArrayList<ArrayList<Tile>> extendedGroups = null;
        ArrayList<Tile> extendedMove = new ArrayList<>();
    
        // Step 3: Check and extend runs
        if (movesRuns != null) {
            extendedRuns = runMoves(dupRack, movesRuns);
        }
    
        // Step 4: Check and extend groups
        if (movesGroups != null) {
            extendedGroups = groupMoves(dupRack, movesGroups);
        }
    
        // Step 5: Combine extended groups and runs into the final move
        if (extendedGroups != null && extendedRuns != null) {
            extendedMove = listOfMoves(extendedGroups, extendedRuns, board);
        }
    
        return extendedMove;
    }
    

    // step 1 and 2
    static ArrayList<ArrayList<Tile>> listOfMovesGroups(Tile[][] board) {
        if (board == null) {
            return null;
        }

        ArrayList<ArrayList<Tile>> movesGroups = new ArrayList<ArrayList<Tile>>();
        ArrayList<Tile> move = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                Tile tile = board[i][y];
                if (tile != null) {
                    move.add(tile);
                } else { // tile is null
                    if (move.size() > 2) {
                        if (Game.checkIfGroup(move)) { // move is group
                            movesGroups.add(new ArrayList<>(move)); 
                        }
                    }
                    move.clear();
                }
            }
        }
        return movesGroups;
    }

    // step 1 and 2
    static ArrayList<ArrayList<Tile>> listOfMovesRuns(Tile[][] board) {
        if (board == null) {
            return null;
        }

        ArrayList<ArrayList<Tile>> movesRuns = new ArrayList<ArrayList<Tile>>();
        ArrayList<Tile> move = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                Tile tile = board[i][y];
                if (tile != null) {
                    move.add(tile);
                } else { // tile is null
                    if (move.size() > 2) {
                        if (Game.checkIfStairs(move)) { // move is stairs
                            movesRuns.add(new ArrayList<>(move)); 
                        }
                    }
                    move.clear();
                }
            }
        }
        return movesRuns;
    }


    // step 3 : for moves that are stairs check the back and the front
    static ArrayList<ArrayList<Tile>> runMoves(Tile[][] dupRack, ArrayList<ArrayList<Tile>> runMoves) {
        ArrayList<Tile> rack = new ArrayList<>(TwodArrayToArrayList(dupRack));
        ArrayList<Tile> possibleTiles = new ArrayList<>();
        ArrayList<ArrayList<Tile>> finalMove = new ArrayList<ArrayList<Tile>>();
        
        for (ArrayList<Tile> move : runMoves) {
            for (Tile tile : rack) {
                if (move.get(0).getColorString() == tile.getColorString()) { // if tile in rack with same color add it to possible tiles
                    possibleTiles.add(tile);
                }
            }
        }

        if (possibleTiles.size() == 0) { // stop looking now its not possible
            return null;
        } else {
            for (ArrayList<Tile> move : runMoves) {
                if (move.size() > 2) { //possible to add tile to group
                    ArrayList<Tile> copy = new ArrayList<>(move);
                    for (Tile tile : possibleTiles) {
                        int firstNum = move.get(0).getNumber();
                        int lastNum = move.get(move.size() - 1).getNumber();
                        if (firstNum > 1 && tile.getNumber() == firstNum - 1) {
                            move.add(0, tile); // add tile at the beginning of the list
                            rack.remove(tile);
                        }
                        if (lastNum < 13 && tile.getNumber() == lastNum + 1) {
                            move.add(tile); // add tile at the end of the list
                            rack.remove(tile);
                        }
                        if (copy != null && move.size() != copy.size() ) {
                            finalMove.add(move);
                            copy = null;
                        }
                    }
                }
            }
        }
        return finalMove;
    }  

    // step 4
    static ArrayList<ArrayList<Tile>> groupMoves(Tile[][] dupRack, ArrayList<ArrayList<Tile>> groupMoves) {
        ArrayList<Tile> rack = new ArrayList<>(TwodArrayToArrayList(dupRack));
        ArrayList<Tile> possibleTiles = new ArrayList<>();
        ArrayList<ArrayList<Tile>> finalMove = new ArrayList<>();
    
        for (ArrayList<Tile> move : groupMoves) {
            for (Tile tile : rack) {
                if (tile.getNumber() == move.get(0).getNumber()) {
                    possibleTiles.add(tile);
                }
            }
        }

        if (possibleTiles.size() == 0) {
            return null;
        } else { // there are possible tiles
            for (ArrayList<Tile> move : groupMoves) {
                if (move.size() == 3) { // possible to add tile to group
                    for (Tile tile : possibleTiles) {
                        move.add(tile);
                        if (Game.checkIfGroup(move)) { // move is correct with the tile added
                            finalMove.add(move);
                            rack.remove(tile); // tile used so removed from rack
                            break;
                        } else {
                            move.remove(move.size() - 1); // remove the last tile
                        }
                    }
                }
            }
        }
        return finalMove;
    }

    // step 5
    static ArrayList<Tile> listOfMoves(ArrayList<ArrayList<Tile>> extendedGroups, ArrayList<ArrayList<Tile>> extendedRuns, Tile[][] board) {
        if (extendedGroups == null && extendedRuns == null) {
            return null;
        }

        ArrayList<Tile> groupMov = new ArrayList<>();
        if (extendedGroups != null) {
            groupMov = new ArrayList<>(nestedArrayListToArrayList(extendedGroups));
        }
    
        ArrayList<Tile> runMov = new ArrayList<>();
        if (extendedRuns != null) {
            runMov = new ArrayList<>(nestedArrayListToArrayList(extendedRuns));
        }
    
        if (!isTwoMovesPossible(runMov, groupMov)) {
            runMov.removeAll(groupMov);
        }
 
        ArrayList<Tile> moves = new ArrayList<>(groupMov);
        moves.add(null);
        moves.addAll(runMov);
 
        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                if (moves.contains(board[i][y])) {
                    board[i][y] = null;
                    
                }
            }
        }
        return moves;
    }
    

    /**
     * takes the rack and the board ordered them and makes moves with them comined and ordered
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
        finalMove = BaselineAgent.nestedArrayListToArrayList(listOfMoves);
        
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
                return twoMoves;
            } else {
                System.out.println("Choosing the largest move");
                if (movesGroup.size() > movesRun.size()) {
                    return movesGroup;
                }else{
                    return movesRun;
                }
            }
        }
    }

    public static void main(String[] args) {

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

        // Tile[][]  rack = {{t4R, n, t10R, n, n, t7R, n, t10O, n, t11R, t13R, t12R, j, t10B, n}};
        //baselineAgent(rack);

        Tile t5R = new Tile((byte) 5, Color.RED);
        Tile t6R = new Tile((byte) 6, Color.RED);

        Tile t2O = new Tile((byte) 2, Color.ORANGE);
        Tile t2B = new Tile((byte) 2, Color.BLUE);
        Tile t2R = new Tile((byte) 2, Color.RED);
        Tile[][]  board = {{n, t5R, t6R, t7R, n, n, n, t2O, t2B, t2R, n, n, n, n, n}};

        //possibleMoveAddingRackToBoard(rack, board);

        Tile t8R = new Tile((byte) 8, Color.RED);
        Tile t2Bl = new Tile((byte) 2, Color.BLACK);

        Tile[][]  rack = {{t4R, t8R, t10R, n, n, t7R, n, t10O, n, t11R, t13R, t12R, j, t10B, t2Bl}};

        ArrayList<Tile> list = new ArrayList<>(singleTilemove(rack, board));
        printListTiles(list);
    }
}
