package com.rummikub.game;
import java.util.ArrayList;
import java.util.Comparator;

public class GameTree {
 
    static Game game;
    static ArrayList<ArrayList<ArrayList<Tile>>> solutionsPerHand;
    static ArrayList<ArrayList<ArrayList<Tile>>> solutions;
    static ArrayList<ArrayList<ArrayList<Tile>>> prevSolution;
    static ArrayList<Tile> hand;
    static ArrayList<Tile> allTilesBoard;
     
    /**
     * Constructor for initializing the GameTree with a current state of the game via the hand and board.
     * @param hand The player's hand.
     * @param board The current state of the game board.
     */
    public GameTree(ArrayList<Tile> hand, ArrayList<ArrayList<Tile>> board) {
       
        this.game = Game.getInstance((byte)2);
        this.solutionsPerHand = new ArrayList<>();
        this.hand = hand;

        this.allTilesBoard = new ArrayList<>();
        // Flatten the 2D board into a single list of tiles
        for (ArrayList<Tile> list : board) {
            allTilesBoard.addAll(list);
        }

    }


        /**
     * Entry point to get solutions with iterative deepening depth-first search.
     * @return The best solutions in the form of a board using as many hand tiles as possible
     */
    static ArrayList<ArrayList<ArrayList<Tile>>> getSolution(int startDepth, int endDepth) {

        // Iterative deepening loop
        for (int segmentSize = startDepth; segmentSize <= endDepth; segmentSize++) {
            System.out.println("depth " + segmentSize);
            prevSolution = solutions;
            solutions = new ArrayList<>();  // Reset solutions at the start of each iteration
            
            segementHand(hand, segmentSize, 0, new ArrayList<>());
           

            // Check if a solution is not found for the current segment size
            if (solutions.isEmpty()) {
                System.out.println("depth " + segmentSize + " does not have a solution");
                // then return previous depth solutions
                return prevSolution;
            }
        }

        return solutions;  // If all tiles can be used
    }

     /**
     * Helper method to recursively segment the hand and find solutions.
     * @param hand The current segment of the player's hand.
     * @param size The size of the current segment.
     */

     private static void segementHand(ArrayList<Tile> hand, int numTiles, int start, ArrayList<Tile> current) {
        if (numTiles == 0) {
            //System.out.println("hand segment " + current);
            ArrayList<ArrayList<ArrayList<Tile>>> segmentSolution = getSolutionsPerSegment(current, allTilesBoard);
            if (!segmentSolution.isEmpty()) {
                 // If a solution is found, you can choose to terminate early
                 solutions = segmentSolution;
                return;

             }
            return;
        }

        for (int i = start; i < hand.size(); i++) {
            current.add(hand.get(i));
            segementHand(hand, numTiles - 1, i + 1, current);
            current.remove(current.size() - 1);
        }
     }
     /**
     * Get solutions for a given hand segment.
     * @param hand The current segment of the player's hand.
     * @param allTilesBoard All tiles on the game board.
     * @return The solutions for the given hand.
     */
   
    static ArrayList<ArrayList<ArrayList<Tile>>>  getSolutionsPerSegment(ArrayList<Tile> hand, ArrayList<Tile> allTilesBoard) {
        //Combine the hand segement and board into one set of tiles
        ArrayList<Tile> allTiles = new ArrayList<Tile>();
        allTiles.addAll(hand);
        allTiles.addAll(allTilesBoard);
        
        // Sort all tiles based on color and then number (aka by group)
        allTiles.sort(Comparator.comparing(Tile::getColorString).thenComparing(Tile::getNumber));

        // Generate all possible moves for the sorted tiles
        ArrayList<ArrayList<ArrayList<Tile>>> allMoves = generatePossibleMovesPerHand(allTiles);
        solutionsPerHand = new ArrayList<>();

        return allMoves;

     }

     /**
     * Generate possible moves for a given  hand segment.
     * @param allTiles The combined tiles from the hand and the game board.
     * @return The possible moves for the given hand.
     */

    private static ArrayList<ArrayList<ArrayList<Tile>>> generatePossibleMovesPerHand(ArrayList<Tile> allTiles){
        generateAllMovesHelper(allTiles, new ArrayList<>());
        return solutionsPerHand;
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
                solutionsPerHand.add(deepCopy);
              
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
