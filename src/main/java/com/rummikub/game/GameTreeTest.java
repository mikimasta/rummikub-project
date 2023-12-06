package com.rummikub.game;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class GameTreeTest {
    public static void main(String[] args) {
        // Assuming you have a Game instance, Tile class, and proper implementations
        ArrayList<Tile> hand = new ArrayList<>();
        ArrayList<Tile> board = new ArrayList<>();

        Tile tile1 = new Tile((byte) 1, Color.RED);
        Tile tile2 = new Tile((byte) 1, Color.BLUE);
        Tile tile3 = new Tile((byte) 1, Color.ORANGE);

        Tile tile7 = new Tile((byte) 2, Color.BLACK);
        Tile tile8 = new Tile((byte) 3, Color.BLACK);
        Tile tile9 = new Tile((byte) 4, Color.BLACK);
        board.add(tile1);
        board.add(tile2);
        board.add(tile3);
        board.add(tile7);
        board.add(tile8);
        board.add(tile9);
        // Add tiles to hand and board
        Tile tile5 = new Tile((byte) 1, Color.BLACK);

        hand.add(tile5);
        // ...

        GameTree gameTree = new GameTree();
        ArrayList<ArrayList<ArrayList<Tile>>> solutions = gameTree.getSolutionsPerHand(hand, board);
        System.out.print(solutions);
        //Print or process the generated solutions
        for (ArrayList<ArrayList<Tile>> solution : solutions) {
            System.out.println("Solution:");
            for (ArrayList<Tile> row : solution) {
                System.out.println("new row");
                System.out.println(row);
            }
            System.out.println("----");
        }
    }
}
