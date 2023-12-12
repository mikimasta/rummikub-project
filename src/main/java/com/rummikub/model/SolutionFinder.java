package com.rummikub.model;
import java.util.ArrayList;
import java.util.Comparator;

import com.rummikub.game.Game;
import com.rummikub.game.Tile;

public class SolutionFinder {
 
    static Game game;
    static ArrayList<ArrayList<Tile>> solutionPerHand;
    static ArrayList<ArrayList<Tile>> solution;
    static ArrayList<ArrayList<Tile>> prevSolution;
    static ArrayList<Tile> hand;
    static ArrayList<Tile> allTilesBoard;
    static boolean solutionFound;
     


    static ArrayList<ArrayList<Tile>> findSolution(ArrayList<Tile> initalHand, ArrayList<ArrayList<Tile>> initalBoard){
        game = Game.getInstance((byte)2, false);        
        hand = initalHand;
        allTilesBoard = new ArrayList<>();
        solutionPerHand = new ArrayList<>();
        solutionFound = false;

        // Flatten the 2D board into a single list of tiles
        for (ArrayList<Tile> list : initalBoard) {
            allTilesBoard.addAll(list);
        }

        return getSolution();
    }

        /**
     * Entry point to get solutions with iterative deepening depth-first search, returns on solution of certin depth.
     * @return The best solutions in the form of a board using as many hand tiles as possible
     */
    static ArrayList<ArrayList<Tile>> getSolution() {

        // Iterative deepening loop
        for (int segmentSize = 1; segmentSize <= hand.size(); segmentSize++) {
            //System.out.println("segmenent size (depth) " + segmentSize);
            solutionFound = false;
            prevSolution = solution;
            solution = new ArrayList<>();  // Reset solutions at the start of each iteration
            
            segementHand(hand, segmentSize, 0, new ArrayList<>());
           

            // Check if a solution is not found for the current segment size
            if (solution.isEmpty() && segmentSize >= 3) {
                //System.out.println("segement size " + segmentSize + " does not have a solution");
                // then return previous depth solutions
                return prevSolution;
            }
        }

        return solution;  // if all of the max amount of  tiles can be used (segementsSize = endDepth)
    }

    

     private static void segementHand(ArrayList<Tile> hand, int numTiles, int start, ArrayList<Tile> current) {
        if (numTiles == 0) {
            //System.out.println("hand segment " + current);
            ArrayList<ArrayList<Tile>> segmentSolution = getSolutionPerSegment(current, allTilesBoard);
            if (!segmentSolution.isEmpty()) {
                //System.out.println("solution at segment size: " + current.size()); 
                 solution = segmentSolution;
                 return;

             }
            return;
        }
        
        for (int i = start; i < hand.size(); i++) {
            current.add(hand.get(i));
            segementHand(hand, numTiles - 1, i + 1, current);
            current.remove(current.size() - 1);

            if(solutionFound){
                return;
            }
        }
     }
     
     /**
     * Get solutions for a given hand segment.
     * @param hand The current segment of the player's hand.
     * @param allTilesBoard All tiles on the game board.
     * @return The solutions for the given hand.
     */
   
    static ArrayList<ArrayList<Tile>>  getSolutionPerSegment(ArrayList<Tile> hand, ArrayList<Tile> allTilesBoard) {
        //Combine the hand segement and board into one set of tiles
        ArrayList<Tile> allTiles = new ArrayList<Tile>();
        allTiles.addAll(hand);
        allTiles.addAll(allTilesBoard);
        
        // Sort all tiles based on color and then number (aka by group)
        allTiles.sort(Comparator.comparing(Tile::getColorString).thenComparing(Tile::getNumber));

        // Generate all possible moves for the sorted tiles
        ArrayList<ArrayList<Tile>> segmentSolution = generatePossibleMovePerHand(allTiles);
        solutionPerHand = new ArrayList<>();

        return segmentSolution;

     }

     /**
     * Generate possible moves for a given  hand segment.
     * @param allTiles The combined tiles from the hand and the game board.
     * @return The possible moves for the given hand.
     */

    private static ArrayList<ArrayList<Tile>> generatePossibleMovePerHand(ArrayList<Tile> allTiles){
        solutionFound = false;
        generateAllMovesHelper(allTiles, new ArrayList<>());
        return solutionPerHand;
    }

     /**
     * Helper method to recursively generate all possible moves for a given set of tiles.
     * @param tiles The remaining tiles to be placed on the board.
     * @param currentBoard The current state of the game board.
     */
    static void generateAllMovesHelper(ArrayList<Tile> tiles, ArrayList<ArrayList<Tile>> currentBoard) {
        //Base case:  All tiles are used and places on the board
        if (tiles.isEmpty()) {
            //Only recored solution if it valid
            if (isValid(currentBoard)) {
        
                ArrayList<ArrayList<Tile>> deepCopy = deepCopyBoard(currentBoard);
                solutionPerHand = deepCopy;
                solutionFound = true;
                //System.out.print("solution found at hand segment level");
              
            }
            return;
        }
    
        for (int i = 0; i < currentBoard.size(); i++) {
            Tile tile = tiles.get(0); // Take the first tile from the remaining tiles
    
            // Try adding the tile to the current row
            currentBoard.get(i).add(tile);

     
    
            // Check if the current row is valid as a group or run unless it is under 3 tiles
            if (currentBoard.get(i).size() < 3 || (game.checkIfGroup(currentBoard.get(i)) || game.checkIfStairs(currentBoard.get(i)))) {
                
                // Recursively explore with the remaining tiles
                ArrayList<Tile> remainingTiles = new ArrayList<>(tiles.subList(1, tiles.size()));
                generateAllMovesHelper(remainingTiles, currentBoard);
            }
    
            // Backtrack by removing the last added tile from the current row
            currentBoard.get(i).remove(currentBoard.get(i).size() - 1);
        // Check the flag to see if a solution has been found, and return if true
        if (solutionFound) {
            return;
        }
        }
    
        // If needed, create a new row and add the tile
        ArrayList<Tile> newRow = new ArrayList<>();
        newRow.add(tiles.get(0));
        currentBoard.add(newRow);
    
        // Check if the current row is valid as a group or run unless it is under 3 tiles
        if (newRow.size() < 3 || (game.checkIfGroup(newRow) || game.checkIfStairs(newRow))) {
            // Recursively explore with the remaining tiles
            ArrayList<Tile> remainingTiles = new ArrayList<>(tiles.subList(1, tiles.size()));
            generateAllMovesHelper(remainingTiles, currentBoard);
        }
    
        // Backtrack by removing the last added row
        currentBoard.remove(currentBoard.size() - 1);
        // Check the flag to see if a solution has been found, and return if true
    if (solutionFound) {
        return;
    }
    }
    
     /**
     * Check if the current state of the game board is valid.
     * @param currentBoard The current state of the game board.
     * @return True if the current board is valid, false otherwise.
     */
    private static boolean isValid(ArrayList<ArrayList<Tile>> currentBoard){
        for (int k = 0; k < currentBoard.size(); k++) {
            ArrayList<Tile> subSet = currentBoard.get(k);

            if (subSet.size() > 2) {
                if (!game.checkTile(subSet)) {
                    return false; // Check if given tile is either stairs or group, return false if not
                }
            } 
         else if (subSet.size() < 3 && subSet.size() != 0) {
            return false;
        } 

    }
    return true; // Checked all tiles, all are correct
    }


    /**
     * Deep copy a given game board.
     * @param originalBoard The original game board to be copied.
     * @return The deep copy of the game board.
     */
    public static ArrayList<ArrayList<Tile>> deepCopyBoard(ArrayList<ArrayList<Tile>> originalBoard) {
        if (originalBoard == null) {
            return null;
        }

        ArrayList<ArrayList<Tile>> newBoard = new ArrayList<>();
        // Iterate through each row and tile, creating a new copy
        for (ArrayList<Tile> row : originalBoard) {
            ArrayList<Tile> newRow = new ArrayList<>();
            for (Tile tile : row) {
                Tile newTile = new Tile(tile.getNumber(), tile.getColor());
                newRow.add(newTile);
            }
            newBoard.add(newRow);
        }

        return newBoard;
    }
}
