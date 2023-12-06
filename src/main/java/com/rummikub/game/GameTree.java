package com.rummikub.game;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameTree {
/* Create a method that transform the board into set of valid sets
 */
    public static void main(String[] args) {
        Tile[][] board = new Tile[Game.GRID_ROWS][Game.GRID_COLS];
        board[0][0] = new Tile((byte) 1, Color.RED);
        board[0][1] = new Tile((byte) 1, Color.BLUE);
        board[0][2] = new Tile((byte) 1, Color.BLACK);
        boardToSet(board);
    }
    public static ArrayList<String> boardToSet(Tile[][] gameBoard){
            ArrayList<String> board = new ArrayList<>();
            String set = new String();
            String setKey = new String();


        for (int i = 0; i < gameBoard.length; i++) {
            setKey = "";
            set = "";
            String color = "";
            for (int y = 0; y < gameBoard[i].length; y++) {
                Tile tile = gameBoard[i][y];
                if(tile.getColor()==Color.RED){
                    color = "Red";
                }else if(tile.getColor()==Color.BLUE){
                    color = "Blue";
                }else if(tile.getColor()==Color.BLACK){
                    color = "Black";
                }else if(tile.getColor()==Color.ORANGE){
                    color = "Orange";
                }else{
                    color = "None";
                }
                if (tile != null) {
                    set += ("("+ tile.getNumber()+","+color+")");
                    System.out.println("error");
                }
            }
            System.out.println(set);


        }
        return board;
    }

//    public boolean isValidBoard(Tile[][] gameBoard) {
//        ArrayList<Tile> set = new ArrayList<>();
//
//        for (int i = 0; i < gameBoard.length; i++) {
//            set.clear();
//
//            for (int y = 0; y < gameBoard[i].length; y++) {
//                Tile tile = gameBoard[i][y];
//                // Checks for illegal subset of tiles of length 1 or 2
//                if ((tile == null) && (set.size() > 0) && (set.size() < 3)) {
//                    return false;
//                } else if (tile == null && set.size() > 2) {
//                    if (!checkTile(set)) {
//                        return false; // Check if given tile is either stairs or group, return false if not
//                    }
//                    set.clear();
//                } else {
//                }
//                if (tile != null) {
//                    set.add(tile);
//                }
//            }
//
//            if (set.size() < 3 && set.size() != 0) {
//                return false; // The block of tiles is too short
//            }
//
//        }
//        return true; // Checked all tiles, all are correct
//    }
//
//    // check if given tile is either group or stairs, i.e. is it legal?
//    boolean checkTile(ArrayList<Tile> set) {
//        return checkIfGroup(set) || checkIfStairs(set);
//    }
//
//    /**
//     * checks if they all have the same number and different colors, i.e., they are a group.
//     * @param set set of tiles
//     * @return true if the tiles form a group false otherwise
//     */
//    boolean checkIfGroup(ArrayList<Tile> set){
//
//        // up to 4 tiles (thats how many colors there are)
//        if (set.size() > 4) {
//            return false;
//        }
//
//        // check if same number
//        byte numOfFirst = set.get(0).getNumber();
//        for (int i = 1; i < set.size(); i++) {
//            byte numTmp = set.get(i).getNumber();
//            if (numOfFirst == -1){ // tile is a joker
//                numOfFirst = numTmp;
//            }else if (numOfFirst != -1 && numOfFirst != numTmp && numTmp != -1){
//                return false;
//            }
//        }
//        // check if different colors
//        int red = 0, green = 0, blue = 0, black = 0;
//        for (int i = 0; i < set.size() ; i++){
//            if (set.get(i).getNumber() != -1){ // tile is not a joker
//                Color color = set.get(i).getColor();
//                if (color == Color.RED){
//                    red++;
//                }else if (color == Color.GREEN){
//                    green++;
//                }else if (color == Color.BLUE){
//                    blue++;
//                }else if (color == Color.BLACK){
//                    black++;
//                }
//            }
//        }
//        if (red > 1 || green > 1 || blue > 1 || black > 1){
//            return false;
//        }
//
//        return true;
//    }
//
//    /**
//     * check if the set is stairs, i.e., same color, incrementing blocks.
//     * @param set set of tiles
//     * @return true if the tiles form a run, false otherwise
//     */
//    boolean checkIfStairs(ArrayList<Tile> set){
//
//        // check if all same color
//        Color colorOfFirst = set.get(0).getColor();
//        for (int i = 1; i < set.size(); i++) {
//            Color color = set.get(i).getColor();
//            if (set.get(0).getNumber() == -1){ // first tile is a joker
//                colorOfFirst = color;
//            }else if (set.get(i).getNumber() != -1 && !colorOfFirst.equals(color)) {
//                return false;
//            }
//        }
//
//        // check if incrementing
//        byte numOfFirst = set.get(0).getNumber();
//        for (int i = 1; i < set.size(); i++) {
//            byte numTmp = set.get(i).getNumber();
//            if (numOfFirst == -1) { // first number is a joker
//                numOfFirst = (byte) (numTmp - 1);
//            }
//            if (numTmp == -1) { // current number is a joker
//                numTmp = (byte) (numOfFirst + 1);
//            }
//            if (numTmp - 1 != numOfFirst || numTmp > 13 || numOfFirst < 1) {
//                return false;
//            }
//            numOfFirst = numTmp;
//        }
//        return true;
//    }

//    ArrayList<Tile> hand;
//    ArrayList<ArrayList<Tile>> board;
//    static Game game;
//
//    public GameTree(ArrayList<Tile> hand,ArrayList<ArrayList<Tile>> board ) {
//        this.hand = hand;
//        this.board = board;
//        this.game = Game.getInstance();
//
//    }
//
//
//
//
//    private ArrayList<ArrayList<ArrayList<Tile>>>  getSolutionsPerHand(ArrayList<Tile> hand, ArrayList<Tile> allTilesBoard) {
//        ArrayList<Tile> allTiles = new ArrayList<Tile>();
//        allTiles.addAll(hand);
//        allTiles.addAll(allTilesBoard);
//
//
//        ArrayList<ArrayList<ArrayList<Tile>>> allMoves = generatePossibleMoves(allTiles);
//
//        return allMoves;
//
//    }
//
//
//
//    private ArrayList<ArrayList<ArrayList<Tile>>> generatePossibleMoves(ArrayList<Tile> allTiles){
//        ArrayList<ArrayList<ArrayList<Tile>>> allBoards = new ArrayList<>();
//        generateAllMovesHelper(allTiles, new ArrayList<>(), allBoards);
//        return allBoards;
//    }
//
//    static void generateAllMovesHelper(ArrayList<Tile> tiles, ArrayList<ArrayList<Tile>> currentBoard, ArrayList<ArrayList<ArrayList<Tile>>> allBoards) {
//        if (tiles.isEmpty()) {
//            if (isValid(currentBoard)) {
//                allBoards.add(new ArrayList<>(currentBoard));
//            }
//            return;
//        }
//
//        for (int i = 0; i < tiles.size(); i++) {
//            Tile tile = tiles.get(i);
//
//            // Try adding the tile to the current board
//            ArrayList<Tile> newRow = new ArrayList<>();
//            newRow.add(tile);
//            currentBoard.add(newRow);
//
//            // Recursively explore with the remaining tiles
//            ArrayList<Tile> remainingTiles = new ArrayList<>(tiles.subList(0, i));
//            remainingTiles.addAll(tiles.subList(i + 1, tiles.size()));
//
//            generateAllMovesHelper(remainingTiles, currentBoard, allBoards);
//
//            // Backtrack by removing the last added tile
//            currentBoard.remove(currentBoard.size() - 1);
//        }
//    }
//
//    private static boolean isValid(ArrayList<ArrayList<Tile>> currentBoard){
//        //check each subset if its a valid group or set.
//        for(int k = 0; k < currentBoard.size(); k++){
//            ArrayList<Tile> subSet = currentBoard.get(k);
//            if(!game.checkSubSet(subSet)){
//                return false;
//            }
//        }
//        return true; // Checked all tiles, all are correct
//    }
}

