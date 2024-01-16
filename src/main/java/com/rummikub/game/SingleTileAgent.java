package com.rummikub.game;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class SingleTileAgent {
    
    /**
     * computer agent which places single tiles at the front or back of a existing move on the board
     * @param dupRack players rack 
     * @param board game board
     * @return an extended move or multiple with a null tile between each move
     */
    public static ArrayList<ArrayList<Tile>> singleTilemove(Tile[][] dupRack, Tile[][] board) {
        if (board == null) {
            return null;
        }

        ArrayList<ArrayList<Tile>> movesRuns = new ArrayList<ArrayList<Tile>>(listOfMovesRuns(board, 3)); // returns list of moves which are runs
        ArrayList<ArrayList<Tile>> movesGroups = new ArrayList<ArrayList<Tile>>(listOfMovesGroups(board, 3)); // returns list of moves which are groups
        ArrayList<ArrayList<Tile>> extendedRuns = null;
        ArrayList<ArrayList<Tile>> extendedGroups = null;
    
        if (movesRuns != null) {
            extendedRuns = runMoves(dupRack, movesRuns); // extend if possible run moves
        }
    
        if (movesGroups != null) {
            extendedGroups = groupMoves(dupRack, movesGroups); // extend if possible group moves
        }
    
        return listOfMoves(extendedRuns, extendedGroups);  // combine extended groups and runs into the final move
    }
    
    /**
     * takes the board and returns all the moves which are runs
     * @param board board
     * @return list of moves which are groups
     */
    static ArrayList<ArrayList<Tile>> listOfMovesRuns(Tile[][] board, int minSizeOfRun) {
        if (board == null) {
            return null;
        }
    
        ArrayList<ArrayList<Tile>> movesRuns = new ArrayList<>();
        ArrayList<Tile> move = new ArrayList<>();
    
        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                Tile tile = board[i][y];
                if (tile != null) {
                    move.add(tile);
                } else { // tile is null
                    if (move.size() >= minSizeOfRun && Game.checkIfStairs(move)) { // // move is stairs
                        movesRuns.add(new ArrayList<>(move));
                    }
                    move.clear();
                }
            }
    
            if (move.size() >= minSizeOfRun && Game.checkIfStairs(move)) { // check if the last sequence forms stairs
                movesRuns.add(new ArrayList<>(move));
            }
            move.clear(); // clear move for the next row
        }
    
        return movesRuns;
    }

    /**
     * takes the board and returns all the moves which are groups
     * @param board board
     * @return list of moves which are groups
     */
    static ArrayList<ArrayList<Tile>> listOfMovesGroups(Tile[][] board, int minSizeOfRun) {
        if (board == null) {
            return null;
        }
    
        ArrayList<ArrayList<Tile>> movesGroups = new ArrayList<>();
        ArrayList<Tile> move = new ArrayList<>();
    
        for (int i = 0; i < board.length; i++) {
            for (int y = 0; y < board[i].length; y++) {
                Tile tile = board[i][y];
                if (tile != null) {
                    move.add(tile);
                } else { // tile is null
                    if (move.size() >= minSizeOfRun && Game.checkIfGroup(move)) { // move is group
                        movesGroups.add(new ArrayList<>(move));
                    }
                        move.clear();
                }
            }

            if (move.size() >= minSizeOfRun && Game.checkIfGroup(move)) { // check if the last sequence forms a group
                movesGroups.add(new ArrayList<>(move));
            }
            move.clear(); // clear move for the next row
        }
    
        return movesGroups;
    }
    

    /**
     * tries to add tiles from the rack to the back or the front of existing run moves
     * @param dupRack rack
     * @param runMoves moves which are runs
     * @return extended moves 
     */
    static ArrayList<ArrayList<Tile>> runMoves(Tile[][] dupRack, ArrayList<ArrayList<Tile>> runMoves) {
        ArrayList<Tile> rack = new ArrayList<>(BaselineAgent.TwodArrayToArrayList(dupRack));
        ArrayList<ArrayList<Tile>> extendRunMoves = new ArrayList<ArrayList<Tile>>();

        ArrayList<Tile> possibleTiles = new ArrayList<>();
        for (ArrayList<Tile> move : runMoves) {
            String colorOfRun = move.get(0).getColorString();
            for (Tile t : move) {
                if (t.getNumber() != -1) {
                    colorOfRun = t.getColorString();
                    break;
                }
            }
            for (Tile tile : rack) {
                if ((tile.getColorString().equals(colorOfRun) && !possibleTiles.contains(tile)) || tile.getColorString().equals("z")) { // if tile in rack with same color add it to possible tiles
                    possibleTiles.add(tile);
                }
            }
        }

        if (possibleTiles.isEmpty()) { // no possible tiles
            return null;
        }
        for (ArrayList<Tile> move : runMoves) {
            ArrayList<Tile> copy = new ArrayList<>(move);
            boolean addedTile = true;
            while (addedTile) {
                addedTile = false;
                for (Tile tile : possibleTiles) {
                    int firstNum = move.get(0).getNumber();
                    int lastNum = move.get(move.size() - 1).getNumber();
                    if (firstNum > 1 && (tile.getNumber() == firstNum - 1 || tile.getColorString().equals("z"))) {
                        copy.add(0, tile); // add tile at the beginning of the list
                        addedTile = true;
                    }
                    if (move.size() != copy.size()) {
                        possibleTiles.remove(tile);
                        break;
                    }   
                    if (lastNum < 13 && (tile.getNumber() == lastNum + 1 || tile.getColorString().equals("z")) && !copy.contains(tile) ) {
                        copy.add(tile); // add tile at the end of the list
                        addedTile = true;
                    }
                    if (move.size() != copy.size()) {
                        possibleTiles.remove(tile);
                        break;
                    }                      
                }  
                if (move.size() != copy.size() && !addedTile && Game.checkIfStairs(copy)) { // || possibleTiles.isEmpty()
                    extendRunMoves.add(copy);
                    addedTile = false; 
                    break;             
                }
            }
        }

        return BaselineAgent.findNonOverlappingMoves(extendRunMoves);
    }  

    /**
     * tries to add tiles from the rack to existing group moves
     * @param dupRack rack
     * @param runMoves moves which are groups
     * @return extended moves 
     */
    static ArrayList<ArrayList<Tile>> groupMoves(Tile[][] dupRack, ArrayList<ArrayList<Tile>> groupMoves) {
        ArrayList<Tile> rack = new ArrayList<>(BaselineAgent.TwodArrayToArrayList(dupRack));
        ArrayList<ArrayList<Tile>> extendGroupMoves = new ArrayList<>();

        ArrayList<Tile> possibleTiles = new ArrayList<>();
        for (ArrayList<Tile> move : groupMoves) {
            byte numberOfGroup = move.get(0).getNumber();
            for (Tile t : move) {
                if (t.getNumber() != -1) {
                    numberOfGroup = t.getNumber();
                    break;
                }
            }
            for (Tile tile : rack) {
                if ((tile.getNumber() == numberOfGroup && !possibleTiles.contains(tile)) || tile.getColorString().equals("z")) { // if tile in rack with same color add it to possible tiles
                    possibleTiles.add(tile);
                }
            }
        }

        if (possibleTiles.size() == 0) {
            return null;
        } 
        for (ArrayList<Tile> move : groupMoves) {
            if (move.size() == 3) { // possible to add tile to group
                ArrayList<Tile> copy = new ArrayList<>(move);
                for (Tile tile : possibleTiles) {
                    copy.add(tile);
                    if (Game.checkIfGroup(copy)) { // move is correct with the tile added
                        extendGroupMoves.add(copy);
                        possibleTiles.remove(tile);
                        break;
                    }
                }
            }
        }
        // return extendGroupMoves;
        return BaselineAgent.findNonOverlappingMoves(extendGroupMoves);
    }

    /**
     * returns list of moves checks if the extended moves don't use the same tile and removes the tiles of the extended move which are on the board in memory
     * @param extendedGroups extended groups
     * @param extendedRuns extended runs
     * @return list of extended moves whith a null tile separating them
     */
    static ArrayList<ArrayList<Tile>> listOfMoves(ArrayList<ArrayList<Tile>> extendedRuns, ArrayList<ArrayList<Tile>> extendedGroups) {
        if (extendedRuns == null && extendedGroups == null) {
            return null;
        }
    
        if (extendedRuns == null) {
            return extendedGroups;
        } else if (extendedGroups == null) {
            return extendedRuns;
        }
    
        ArrayList<ArrayList<Tile>> moves = new ArrayList<>(extendedRuns);
        moves.addAll(extendedGroups);
        return BaselineAgent.findNonOverlappingMoves(moves); 
    }
    
    public static void main(String[] args) {

        Tile n = null;
        Tile j = new Tile((byte) -1, Color.RED);

        Tile t8B = new Tile((byte) 8, Color.BLACK);
        Tile t9B = new Tile((byte) 9, Color.BLACK);
        Tile t10B = new Tile((byte) 10, Color.BLACK);
        Tile t11B = new Tile((byte) 11, Color.BLACK);
        Tile t12Bl = new Tile((byte) 12, Color.BLUE);
        Tile t13B = new Tile((byte) 13, Color.BLACK);
        
        Tile t1B = new Tile((byte) 12, Color.BLUE);
        Tile t1R = new Tile((byte) 12, Color.RED);
        Tile t1O = new Tile((byte) 12, Color.ORANGE);
        Tile t1Bl = new Tile((byte) 12, Color.BLACK);

        Tile[][]  board = {
            {n, n, n,n, n, n, j, t9B, t10B, t11B, n, n, n, n, n}
        };
        System.out.println(Game.printBoard(board));
        Tile[][]  rack = {{n, t13B, n, n, t12Bl, n, n, n, n, n, n, n, n, n, n}};

        ArrayList<ArrayList<Tile>> singleTileMoves = singleTilemove(rack, board);

        if (singleTileMoves == null || singleTileMoves.isEmpty()) {
            System.out.println("no move");
        }else {
            System.out.println(BaselineAgent.printMoves(singleTileMoves));
        }

        
    }
}
