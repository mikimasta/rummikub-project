package com.rummikub.game;
import java.util.ArrayList;
import java.util.Comparator;

public class GameTree {
 
    static Game game;
    static ArrayList<ArrayList<ArrayList<Tile>>> solutions;
    public GameTree() {
       
        this.game = Game.getInstance((byte)2);
        this.solutions = new ArrayList<>();

    }


    


   
    ArrayList<ArrayList<ArrayList<Tile>>>  getSolutionsPerHand(ArrayList<Tile> hand, ArrayList<Tile> allTilesBoard) {
        ArrayList<Tile> allTiles = new ArrayList<Tile>();
        allTiles.addAll(hand);
        allTiles.addAll(allTilesBoard);
        
        allTiles.sort(Comparator.comparing(Tile::getColorString).thenComparing(Tile::getNumber));

        ArrayList<ArrayList<ArrayList<Tile>>> allMoves = generatePossibleMovesPerHand(allTiles);

        return allMoves;

     }



    private ArrayList<ArrayList<ArrayList<Tile>>> generatePossibleMovesPerHand(ArrayList<Tile> allTiles){
        //ArrayList<ArrayList<ArrayList<Tile>>> allBoards = new ArrayList<>();
        generateAllMovesHelper(allTiles, new ArrayList<>());
        return solutions;
    }

    static void generateAllMovesHelper(ArrayList<Tile> tiles, ArrayList<ArrayList<Tile>> currentBoard) {
        if (tiles.isEmpty()) {
            if (isValid(currentBoard)) {
        
                ArrayList<ArrayList<Tile>> deepCopy = deepCopyBoard(currentBoard);
                solutions.add(deepCopy);
              
            }
            return;
        }
    
        for (int i = 0; i < currentBoard.size(); i++) {
            Tile tile = tiles.get(0); // Take the first tile from the remaining tiles
    
            // Try adding the tile to the current row
            currentBoard.get(i).add(tile);

            // Debug print statement
            //System.out.println("Trying tile " + tile.toString() + " in row " + i);
    
            // Check if the current row is valid as a group or run
            if (currentBoard.get(i).size() < 3 || (game.checkIfGroup(currentBoard.get(i)) || game.checkIfStairs(currentBoard.get(i)))) {
                
                // Debug print statement
                // System.out.println("Valid configuration so far: ");
                // for(int k = 0; k < currentBoard.get(i).size(); k++ ){
                //     System.out.println(k + " " +currentBoard.get(i).get(k).toString());
                // }
                
                // Recursively explore with the remaining tiles
                ArrayList<Tile> remainingTiles = new ArrayList<>(tiles.subList(1, tiles.size()));
                generateAllMovesHelper(remainingTiles, currentBoard);
            }
    
            // Backtrack by removing the last added tile from the current row
            currentBoard.get(i).remove(currentBoard.get(i).size() - 1);
        }
    
        // If needed, create a new row and add the tile
        ArrayList<Tile> newRow = new ArrayList<>();
        newRow.add(tiles.get(0));
        currentBoard.add(newRow);
    
        // Check if the new row is valid as a group or run
        if (newRow.size() < 3 || (game.checkIfGroup(newRow) || game.checkIfStairs(newRow))) {
            // Recursively explore with the remaining tiles
            ArrayList<Tile> remainingTiles = new ArrayList<>(tiles.subList(1, tiles.size()));
            generateAllMovesHelper(remainingTiles, currentBoard);
        }
    
        // Backtrack by removing the last added row
        currentBoard.remove(currentBoard.size() - 1);
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

    public static ArrayList<ArrayList<Tile>> deepCopyBoard(ArrayList<ArrayList<Tile>> originalBoard) {
        if (originalBoard == null) {
            return null;
        }

        ArrayList<ArrayList<Tile>> newBoard = new ArrayList<>();

        for (ArrayList<Tile> row : originalBoard) {
            ArrayList<Tile> newRow = new ArrayList<>();
            for (Tile tile : row) {
                // Assuming Tile has a copy constructor or implements Cloneable
                Tile newTile = new Tile(tile.getNumber(), tile.getColor());
                // Or use clone method if Tile implements Cloneable
                // Tile newTile = tile.clone();
                newRow.add(newTile);
            }
            newBoard.add(newRow);
        }

        return newBoard;
    }
}
