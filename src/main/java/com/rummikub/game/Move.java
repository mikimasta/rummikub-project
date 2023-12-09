package com.rummikub.game;

import java.util.ArrayList;

/**
 * This class is a representation of a possible move in the game
 */
public class Move {
    private ArrayList<Tile> set;
    private ArrayList<Tile> tileAdded;
    public Move (ArrayList<Tile> set, ArrayList<Tile> tileAdded) {
        this.set = set;
        this.tileAdded = tileAdded;

    }
    public void printMove(){
        System.out.println(getSet()+""+getTileAdded());
    }
    public ArrayList<Tile> getSet() {
        return set;
    }

    public ArrayList<Tile> getTileAdded() {
        return tileAdded;
    }
}
