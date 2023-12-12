package com.rummikub.game;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class GameTreeTest {
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
        set1.add(tile7);
        set1.add(tile8);
        set1.add(tile9);
   
        ArrayList<Tile> set4 = new ArrayList<>();
        Tile tile10 = new Tile((byte) 3, Color.RED);
        Tile tile11 = new Tile((byte) 4,Color.RED);
        Tile tile12 = new Tile((byte) 5, Color.RED);
        set4.add(tile10);
        set4.add(tile11);
        set4.add(tile12);

        ArrayList<Tile> set5 = new ArrayList<>();
        Tile tile13 = new Tile((byte) 7, Color.BLACK);
        Tile tile14 = new Tile((byte) 8,Color.BLACK);
        Tile tile15 = new Tile((byte) 9, Color.BLACK);
        Tile tile16 = new Tile((byte) 10, Color.BLACK);
        Tile tile17 = new Tile((byte) 11, Color.BLACK);
        Tile tile18 = new Tile((byte) 12, Color.BLACK);
        set5.add(tile13);
        set5.add(tile14);
        set5.add(tile15);
        set5.add(tile16);
        set5.add(tile17);
        set5.add(tile18);
    
        ArrayList<Tile> set6 = new ArrayList<>();
        Tile tile19 = new Tile((byte) 7, Color.RED);
        Tile tile20 = new Tile((byte) 7,Color.BLACK);
        Tile tile21 = new Tile((byte) 7, Color.BLUE);
        set6.add(tile19);
        set6.add(tile20);
        set6.add(tile21);

        ArrayList<Tile> set7 = new ArrayList<>();
        Tile tile22 = new Tile((byte) 11, Color.RED);
        Tile tile23 = new Tile((byte) 11,Color.BLACK);
        Tile tile24 = new Tile((byte) 11, Color.BLUE);
        Tile tile25 = new Tile((byte) 11, Color.ORANGE);

        set7.add(tile22);
        set7.add(tile23);
        set7.add(tile24);
        set7.add(tile25);
        

        ArrayList<ArrayList<Tile>> fullBoard = new ArrayList<>();
        fullBoard.add(set1);
        fullBoard.add(set2);
        //fullBoard.add(set3);
        fullBoard.add(set4);
        //fullBoard.add(set5);
        //fullBoard.add(set6);

        ArrayList<Tile> hand = new ArrayList<>();
        Tile tileA = new Tile((byte) 10, Color.RED);
        Tile tileB = new Tile((byte) 10, Color.ORANGE);
        Tile tileC = new Tile((byte) 10, Color.BLACK);
        Tile tileD = new Tile((byte) 4, Color.RED);
        Tile tileE = new Tile((byte) 11, Color.ORANGE);
        Tile tile28 = new Tile((byte) 7, Color.ORANGE);
        Tile tile29 = new Tile((byte) 5, Color.BLACK);
        Tile tile30 = new Tile((byte) 13, Color.BLUE);

        hand.add(tileA);
        hand.add(tileB);
        hand.add(tileC);
        hand.add(tileD);
        hand.add(tileE);

        GameTreeNew gameTree = new GameTreeNew(hand, fullBoard);
        ArrayList<ArrayList<Tile>> solution = gameTree.getSolution(1, hand.size());
        //Print or process the generated solutions
        //for (ArrayList<ArrayList<Tile>> solution : solutions) {
        //MonteCarlo mc = new MonteCarlo(hand, fullBoard);
        //ArrayList<ArrayList<Tile>> solution = mc.monteCarloSearch();
            System.out.println("Solution:");
            for (ArrayList<Tile> row : solution) {
                System.out.println("new row");
                System.out.println(row);
            }
            System.out.println("----");
        }
   // }
}
