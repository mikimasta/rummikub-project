package com.rummikub.game;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class SingleTileMove {
    
    /**
     * computer agent which places single tiles at the front or back of a existing move 
     * @param dupRack players rack 
     * @param board game board
     * @return an extended move or multiple with a null tile between each move
     */
    public static ArrayList<ArrayList<Tile>> singleTilemove(Tile[][] dupRack, Tile[][] board) {
        if (board == null) {
            return null;
        }

        ArrayList<ArrayList<Tile>> movesRuns = listOfMovesRuns(board); // returns list of moves which are runs
        ArrayList<ArrayList<Tile>> movesGroups = listOfMovesGroups(board); // returns list of moves which are groups
        ArrayList<ArrayList<Tile>> extendedRuns = null;
        ArrayList<ArrayList<Tile>> extendedGroups = null;
        ArrayList<ArrayList<Tile>> extendedMove = new ArrayList<ArrayList<Tile>>();
    
        if (movesRuns != null) {
            extendedRuns = runMoves(dupRack, movesRuns); // extend if possible run moves
        }
    
        if (movesGroups != null) {
            extendedGroups = groupMoves(dupRack, movesGroups); // extend if possible group moves
        }
    
        if (extendedGroups != null && extendedRuns != null) {
            extendedMove = listOfMoves(extendedGroups, extendedRuns);  // combine extended groups and runs into the final move
        }
    
        return extendedMove;
    }
    
    /**
     * takes the board and returns all the moves which are runs
     * @param board board
     * @return list of moves which are groups
     */
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

    /**
     * takes the board and returns all the moves which are groups
     * @param board board
     * @return list of moves which are groups
     */
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

    /**
     * tries to add tiles from the rack to the back or the front of existing run moves
     * @param dupRack rack
     * @param runMoves moves which are runs
     * @return extended moves 
     */
    static ArrayList<ArrayList<Tile>> runMoves(Tile[][] dupRack, ArrayList<ArrayList<Tile>> runMoves) {
        ArrayList<Tile> rack = new ArrayList<>(BaselineAgent.TwodArrayToArrayList(dupRack));
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
                ArrayList<Tile> copy = new ArrayList<>(move);
                boolean addedTile = true;
                while (addedTile) {
                    addedTile = false;
                    for (Tile tile : possibleTiles) {
                        int firstNum = move.get(0).getNumber();
                        int lastNum = move.get(move.size() - 1).getNumber();
                        if (firstNum > 1 && tile.getNumber() == firstNum - 1) {
                            move.add(0, tile); // add tile at the beginning of the list
                            rack.remove(tile);
                            addedTile = true;
                        }
                        if (lastNum < 13 && tile.getNumber() == lastNum + 1) {
                            move.add(tile); // add tile at the end of the list
                            rack.remove(tile);
                            addedTile = true;
                        }
                        if (copy != null && move.size() != copy.size() && addedTile == false) {
                            finalMove.add(move);
                            copy = null;                        
                        }                        
                    }  
                }
            }
        }

        return BaselineAgent.findNonOverlappingMoves(finalMove);
    }  

    /**
     * tries to add tiles from the rack to existing group moves
     * @param dupRack rack
     * @param runMoves moves which are groups
     * @return extended moves 
     */
    static ArrayList<ArrayList<Tile>> groupMoves(Tile[][] dupRack, ArrayList<ArrayList<Tile>> groupMoves) {
        ArrayList<Tile> rack = new ArrayList<>(BaselineAgent.TwodArrayToArrayList(dupRack));
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

        return BaselineAgent.findNonOverlappingMoves(finalMove);
    }

    /**
     * returns list of moves checks if the extended moves don't use the same tile and removes the tiles of the extended move which are on the board in memory
     * @param extendedGroups extended groups
     * @param extendedRuns extended runs
     * @return list of extended moves whith a null tile separating them
     */
    static ArrayList<ArrayList<Tile>> listOfMoves(ArrayList<ArrayList<Tile>> extendedGroups, ArrayList<ArrayList<Tile>> extendedRuns) {
        if (extendedGroups == null && extendedRuns == null) {
            return null;
        }
    
        if (extendedGroups == null) {
            return extendedRuns;
        } else if (extendedRuns == null) {
            return extendedGroups;
        }
    
        ArrayList<ArrayList<Tile>> moves = new ArrayList<>(extendedGroups);
        moves.addAll(extendedRuns);
        return BaselineAgent.findNonOverlappingMoves(moves);
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

        Tile t5R = new Tile((byte) 5, Color.RED);
        Tile t6R = new Tile((byte) 6, Color.RED);

        Tile t2O = new Tile((byte) 2, Color.ORANGE);
        Tile t2B = new Tile((byte) 2, Color.BLUE);
        Tile t2R = new Tile((byte) 2, Color.RED);
        Tile[][]  board = {{n, t5R, t6R, t7R, n, n, n, t2O, t2B, t2R, n, n, n, n, n}};

        Tile t8R = new Tile((byte) 8, Color.RED);
        Tile t2Bl = new Tile((byte) 2, Color.BLACK);

        Tile[][]  rack = {{t4R, t8R, t1R, n, n, t7R, n, t10O, n, t11R, t13R, t12R, j, t10B, t2Bl}};

        ArrayList<ArrayList<Tile>> list = new ArrayList<ArrayList<Tile>>(singleTilemove(rack, board));
        BaselineAgent.printMoves(list);
    }
}
