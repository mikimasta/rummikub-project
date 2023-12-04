package com.rummikub.game;
import java.util.ArrayList;

public class GameTree {
    ArrayList<Tile> hand;
    ArrayList<ArrayList<Tile>> board;
    static Game game;

    public GameTree(ArrayList<Tile> hand,ArrayList<ArrayList<Tile>> board ) {
        this.hand = hand;
        this.board = board;
        this.game = Game.getInstance();

    }


    
   
    private ArrayList<ArrayList<ArrayList<Tile>>>  getSolutionsPerHand(ArrayList<Tile> hand, ArrayList<Tile> allTilesBoard) {
        ArrayList<Tile> allTiles = new ArrayList<Tile>();
        allTiles.addAll(hand);
        allTiles.addAll(allTilesBoard);
        
            
        ArrayList<ArrayList<ArrayList<Tile>>> allMoves = generatePossibleMoves(allTiles);

        return allMoves;

     }



    private ArrayList<ArrayList<ArrayList<Tile>>> generatePossibleMoves(ArrayList<Tile> allTiles){
        ArrayList<ArrayList<ArrayList<Tile>>> allBoards = new ArrayList<>();
        generateAllMovesHelper(allTiles, new ArrayList<>(), allBoards);
        return allBoards;
    }

    static void generateAllMovesHelper(ArrayList<Tile> tiles, ArrayList<ArrayList<Tile>> currentBoard, ArrayList<ArrayList<ArrayList<Tile>>> allBoards) {
        if (tiles.isEmpty()) {
            if (isValid(currentBoard)) {
                allBoards.add(new ArrayList<>(currentBoard));
            }
            return;
        }

        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);

            // Try adding the tile to the current board
            ArrayList<Tile> newRow = new ArrayList<>();
            newRow.add(tile);
            currentBoard.add(newRow);

            // Recursively explore with the remaining tiles
            ArrayList<Tile> remainingTiles = new ArrayList<>(tiles.subList(0, i));
            remainingTiles.addAll(tiles.subList(i + 1, tiles.size()));

            generateAllMovesHelper(remainingTiles, currentBoard, allBoards);

            // Backtrack by removing the last added tile
            currentBoard.remove(currentBoard.size() - 1);
        }
    }

    private static boolean isValid(ArrayList<ArrayList<Tile>> currentBoard){
        //check each subset if its a valid group or set.
        for(int k = 0; k < currentBoard.size(); k++){
            ArrayList<Tile> subSet = currentBoard.get(k);
            if(!game.checkSubSet(subSet)){
                return false;
            }
        }
        return true; // Checked all tiles, all are correct
    }
}
