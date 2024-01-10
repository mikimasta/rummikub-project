package com.rummikub.model;

import com.rummikub.game.Tile;

import java.util.ArrayList;

public class AgentImplementation {

    public static Object[] makeMove(Tile[][] initialHand, Tile[][] initialBoard){
        ArrayList<ArrayList<Tile>> arrayBoard =  board2ArrayList(initialBoard);
        ArrayList<Tile> arrayHand = hand2ArrayList(initialHand);
        ArrayList<ArrayList<Tile>> solutionBoard = new ArrayList<>();

        solutionBoard = AgentNB.possibleMoves(arrayHand, arrayBoard);

        //System.out.println(solutionBoard);
        if(solutionBoard == null){
            //System.out.println("solution NOT found");
            return null;
        }
        //System.out.println("solution found");
        ArrayList<Tile> solutionHand = getHand(board2ArrayList(initialBoard), solutionBoard, arrayHand);

        Tile[][] finalHand = hand2matrix(solutionHand);
        Tile[][] finalBoard = board2matrix(solutionBoard);

        return new Object[]{finalHand, finalBoard};

    }

    private static ArrayList<Tile> getHand(ArrayList<ArrayList<Tile>> intialBoard, ArrayList<ArrayList<Tile>> solutionBoard, ArrayList<Tile> hand){
        ArrayList<Tile> usedTiles = new ArrayList<>();
   
        // Iterate through list1
        for (ArrayList<Tile> sublist1 : solutionBoard) {
            for (Tile tile : sublist1) {
                // Check if the tile is not present in list2
                if (!isTilePresent(tile, intialBoard)) {
                    usedTiles.add(tile);
                }
            }
        }
        
        ArrayList<Tile> finalHand = removeUsedTile(hand, usedTiles);
        
        return finalHand;
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
            if(colIndex+ innerList.size() > 20){
                //System.out.println("change row");
                rowIndex++;
                colIndex = 0;
            }
            for (Tile tile : innerList) {
                matrix[rowIndex][colIndex] = tile;
                colIndex++;
            }
            colIndex++;
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

    public static ArrayList<ArrayList<Tile>> board2ArrayList( Tile[][] board){
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

}
