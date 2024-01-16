package com.rummikub.MCTS;

import com.rummikub.game.Tile;
import javafx.scene.paint.Color;
import java.util.ArrayList;


public class test {
    public static void main(String[] args) {
        Tile tile1r = new Tile((byte) 1, Color.RED);
        Tile tile2r = new Tile((byte) 2, Color.RED);
        Tile tile3r = new Tile((byte) 3, Color.RED);
        Tile tile4r = new Tile((byte) 4, Color.RED);
        Tile tile5r = new Tile((byte) 5, Color.RED);

        Tile tile1b = new Tile((byte) 1, Color.BLUE);
        Tile tile2b = new Tile((byte) 2, Color.BLUE);
        Tile tile3b = new Tile((byte) 3, Color.BLUE);
        Tile tile4b = new Tile((byte) 4, Color.BLUE);
        Tile tile5b = new Tile((byte) 5, Color.BLUE);

        Tile tile1bl = new Tile((byte) 1, Color.BLACK);
        Tile tile2bl = new Tile((byte) 2, Color.BLACK);
        Tile tile3bl = new Tile((byte) 3, Color.BLACK);
        Tile tile4bl = new Tile((byte) 4, Color.BLACK);
        Tile tile5bl = new Tile((byte) 5, Color.BLACK);

        Tile tile10 = new Tile((byte) 1, Color.ORANGE);
        Tile tile20 = new Tile((byte) 2, Color.ORANGE);
        Tile tile30 = new Tile((byte) 3, Color.ORANGE);
        Tile tile40 = new Tile((byte) 4, Color.ORANGE);
        Tile tile50 = new Tile((byte) 5, Color.ORANGE);

        ArrayList<Tile> initalHand = new ArrayList<>();
        initalHand.add(tile1r); 
        initalHand.add(tile2r); 
        initalHand.add(tile3r); 
        initalHand.add(tile4r); 
        initalHand.add(tile5r); 
        initalHand.add(tile1b);
        initalHand.add(tile2b);
        initalHand.add(tile3b);
        initalHand.add(tile4b); 
        initalHand.add(tile5b); 
        initalHand.add(tile1bl);
        initalHand.add(tile2bl);
        initalHand.add(tile3bl);
        initalHand.add(tile4bl);
        initalHand.add(tile5bl);
        initalHand.add(tile10); 
        initalHand.add(tile20); 
        initalHand.add(tile30); 
        initalHand.add(tile40); 
        initalHand.add(tile50);     

    ArrayList<ArrayList<Tile>> initalBoard = new ArrayList<ArrayList<Tile>>();

    SolutionFinderAll finder = new SolutionFinderAll();

    ArrayList<ArrayList<ArrayList<Tile>>> allSolutions = finder.getAllSolutions(initalHand, initalBoard);

    for(ArrayList<ArrayList<Tile>> solution : allSolutions){
        System.out.println("Solution:");
        for(ArrayList<Tile> set : solution){
           System.out.print("Set: ");
           for( Tile tile : set){
            System.out.print(tile.toString());
           }
           System.out.println(" ");
        }
        System.out.println(" ");
    }
}
    
}
