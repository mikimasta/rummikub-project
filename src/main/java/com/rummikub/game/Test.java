package com.rummikub.game;
import java.util.*;

import javafx.scene.paint.Color;

public class Test {
    private static Tile[][] board2matrix(ArrayList<ArrayList<Tile>> board){
        Tile[][] matrix = new Tile[20][7];

        int rowIndex = 0;
        int colIndex = 0;

        for (ArrayList<Tile> innerList : board) {
            for (Tile tile : innerList) {
                matrix[rowIndex][colIndex] = tile;
                colIndex++;

                // Check if the row is full or if a 0 is encountered
                if (colIndex == 7) {
                    rowIndex++;
                    colIndex = 0;

                    // Check if the matrix is full
                    if (rowIndex == 20) {
                        break;
                    }
                }
            }
            //after every inner list it should be moved
            if (!(colIndex == 0)) {
                  colIndex++;
        }
        
    }
        return matrix;
    }

    public static void main(String[] args) {
        ArrayList<Tile> set1 = new ArrayList<>();
        Tile tile1 = new Tile((byte) 1, Color.RED);
        Tile tile2 = new Tile((byte) 2,Color.RED);
        Tile tile3 = new Tile((byte) 3, Color.RED);
        set1.add(tile1);
        set1.add(tile2);
        set1.add(tile3);

        ArrayList<Tile> set2 = new ArrayList<>();
        Tile tile4 = new Tile((byte) 1, Color.ORANGE);
        Tile tile5 = new Tile((byte) 2,Color.ORANGE);
        Tile tile6 = new Tile((byte) 3, Color.ORANGE);
        set2.add(tile4);
        set2.add(tile5);
        set2.add(tile6);

        ArrayList<Tile> set3 = new ArrayList<>();
        Tile tile7 = new Tile((byte) 3, Color.RED);
        Tile tile8 = new Tile((byte) -1,Color.BLACK);
        Tile tile9 = new Tile((byte) 5, Color.RED);
        set3.add(tile7);
        set3.add(tile8);
        set3.add(tile9);
   
        ArrayList<Tile> set4 = new ArrayList<>();
        Tile tile10 = new Tile((byte) 3, Color.RED);
        Tile tile11 = new Tile((byte) 4,Color.RED);
        Tile tile12 = new Tile((byte) 5, Color.RED);
        set4.add(tile10);
        set4.add(tile11);
        set4.add(tile12);

        ArrayList<ArrayList<Tile>> fullBoard = new ArrayList<>();
        fullBoard.add(set1);
        fullBoard.add(set2);
        fullBoard.add(set3);
        fullBoard.add(set4);

        Tile[][] test = board2matrix(fullBoard);

         // Display the matrix
         for (Tile[] row : test) {
            for (Tile tile : row) {
                if(tile != null){
                 System.out.print(tile.toString() + " ");

                }
                else{
                   System.out.print(0 + " ");
 
                }
            }
            System.out.println();
        }
    }
}
