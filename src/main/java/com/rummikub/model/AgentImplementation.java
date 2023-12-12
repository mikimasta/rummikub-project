package com.rummikub.model;

import com.rummikub.game.Tile;

import java.util.ArrayList;

public class AgentImplementation {

    public static Object[] makeMove(Tile[][] intialHand, Tile[][] intialBoard){
       ArrayList<ArrayList<Tile>> arrayBoard =  board2ArrayList(intialBoard);
       ArrayList<Tile> arrayHand = hand2ArrayList(intialHand);

       for (int i = 0; i < intialHand.length; i++) {
        for (int j = 0; j < intialHand[i].length; j++) {
            if (intialHand[i][j] != null) {
                System.out.print(intialHand[i][j] + " ");
            } else {
                System.out.print("null ");
            }
        }
        System.out.println(); // Move to the next line after each row
    }
       
    
       ArrayList<Tile> allTilesBoard = new ArrayList<>();
       for (ArrayList<Tile> list : arrayBoard) {
            allTilesBoard.addAll(list);
        }

        int numOfTilesInPlay = allTilesBoard.size() + arrayHand.size();
        System.out.println("number of tiles in play = " + numOfTilesInPlay);
        ArrayList<ArrayList<Tile>> solutionBoard = new ArrayList<>();

        if(numOfTilesInPlay < 25){
            solutionBoard = SolutionFinder.findSolution(arrayHand, arrayBoard);
        }
        else{
            solutionBoard = AgentNB.possibleMoves(arrayHand, arrayBoard);
        }
        if(solutionBoard == null || solutionBoard.isEmpty()){
            System.out.println("solution NOT found");
            return null;
        }
        System.out.println("solution found");
        ArrayList<Tile> solutionHand = getHand(board2ArrayList(intialBoard), solutionBoard, arrayHand);
        
        Tile[][] finalHand = hand2matrix(solutionHand);
        Tile[][] finalBoard = board2matrix(solutionBoard);

        
          for (int i = 0; i < finalHand.length; i++) {
        for (int j = 0; j < finalHand[i].length; j++) {
            if (finalHand[i][j] != null) {
                System.out.print(finalHand[i][j] + " ");
            } else {
                System.out.print("null ");
            }
        }
        System.out.println(); // Move to the next line after each row
    }

        return new Object[]{finalHand, finalBoard};

    }

    private static ArrayList<Tile> getHand(ArrayList<ArrayList<Tile>> intialBoard, ArrayList<ArrayList<Tile>> solutionBoard, ArrayList<Tile> hand){
        ArrayList<Tile> usedTiles = new ArrayList<>();

        //ArrayList<Tile> deepCopy = deepCopyHand(hand);
   
        // Iterate through list1
        for (ArrayList<Tile> sublist1 : solutionBoard) {
            for (Tile tile : sublist1) {
                // Check if the tile is not present in list2
                if (!isTilePresent(tile, intialBoard)) {
                    usedTiles.add(tile);
                }
            }
        }
        System.out.println("used tiles" + usedTiles.toString());
        
        ArrayList<Tile> finalHand = removeUsedTile(hand, usedTiles);
        
        return finalHand;
    //}
    }

    public static ArrayList<Tile> removeUsedTile(ArrayList<Tile> hand, ArrayList<Tile> used){
        ArrayList<Tile> result = new ArrayList<>();
        for(int i = 0; i < hand.size(); i++){
                Tile currentFromHand = hand.get(i);
                boolean tileUsed = false;
            for(int j = 0; j < used.size(); j++){
                Tile currentFromused = used.get(j);
                if((currentFromHand.getColorString().equals(currentFromused.getColorString()))){
                    if(currentFromHand.getNumber() == currentFromused.getNumber()){
                        tileUsed = true;
                    }   
                }
            }
            if(!tileUsed){
                result.add(currentFromHand);
            }
        }
        return result;
    }
    

    public static boolean isTilePresent(Tile tile, ArrayList<ArrayList<Tile>> list) {
        // Check if the tile is present in any sublist of the list
        for (ArrayList<Tile> sublist : list) {
            if (sublist.contains(tile)) {
                return true;
            }
        }
        return false;
    }

    

    private static Tile[][] board2matrix(ArrayList<ArrayList<Tile>> board) {
        Tile[][] matrix = new Tile[7][20];
    
        int rowIndex = 0;
        int colIndex = 0;
    
        for (ArrayList<Tile> innerList : board) {
            for (Tile tile : innerList) {
                matrix[rowIndex][colIndex] = tile;
                colIndex++;
    
                // Check if the row is full
                if (colIndex == 20) {
                    rowIndex++;
                    colIndex = 0;
    
                    // Check if the matrix is full
                    if (rowIndex == 7) {
                        break;
                    }
                }
            }
            // Reset colIndex to 0 after every inner list
            colIndex = 0;
    
            // Check if the matrix is full
            if (rowIndex == 20) {
                break;
            }
        }
        return matrix;
    }
    

    private static Tile[][] hand2matrix(ArrayList<Tile> hand){
         Tile[][] matrix = new Tile[2][15];

        int rowIndex = 0;
        int colIndex = 0;

            for (Tile tile : hand) {
                matrix[rowIndex][colIndex] = tile;
                colIndex++;

                // Check if the row is full or if a 0 is encountered
                if (colIndex == 15) {
                    rowIndex++;
                    colIndex = 0;

                    // Check if the matrix is full
                    if (rowIndex == 2) {
                        break;
                    }
                }
            }
        return matrix;
    }
        
    
    private static ArrayList<ArrayList<Tile>> board2ArrayList( Tile[][] board){
        //make board into arrray list of array list
        boolean currentSet = false;
        ArrayList<Tile> set = new ArrayList<>();
        ArrayList<ArrayList<Tile>> allSets = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] != null){
                    currentSet = true;
                    set.add(board[i][j]);
                }
                if(board[i][j] == null && currentSet){
                    allSets.add(set);
                    set = new ArrayList<>();
                    currentSet = false;
                }
                
            }
        }

        if (set.size() != 0){
         allSets.add(set);
        }
     
        return allSets;

 }

    private static ArrayList<Tile> hand2ArrayList (Tile[][] hand){
         //make board into arrray list of array list
           ArrayList<Tile> handArray = new ArrayList<>();
   
           for (int i = 0; i < hand.length; i++) {
               for (int j = 0; j < hand[i].length; j++) {
                   if(hand[i][j] != null){
                       handArray.add(hand[i][j]);
                   }

                   
               }
           }
   
           return handArray;
    }

      /**
     * Deep copy a given game board.
     * @param hand The original game board to be copied.
     * @return The deep copy of the game board.
     */
    public static ArrayList<Tile> deepCopyHand(ArrayList<Tile> hand) {
        if (hand == null) {
            return null;
        }

    ArrayList<Tile> newHand = new ArrayList<>();
     
            for (Tile tile : hand) {
                Tile newTile = new Tile(tile.getNumber(), tile.getColor());
                newHand.add(newTile);
            }
          

        return newHand;
    }

}
