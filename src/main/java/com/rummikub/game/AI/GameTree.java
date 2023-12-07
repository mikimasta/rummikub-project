package com.rummikub.game.AI;

import com.rummikub.game.Tile;
import javafx.scene.paint.Color;
import java.util.Arrays;
import java.util.ArrayList;

public class GameTree {
    public static void main(String[] args) {
        ArrayList<String> board = new ArrayList<>();
        board.add("1Black1Orange1Red");
        board.add("-1None-1None2Orange");
        possibleNB(board);
        System.out.println(possibleNB(board));
    }

    public static ArrayList<Tile> possibleNB (ArrayList<String> board){
        ArrayList<Tile> possibleNBList = new ArrayList<>();
        for(String tileKey : board){
            for(Tile tile : tileNB(tileKey)){
                possibleNBList.add(tile);
            }
        }
        return possibleNBList;
    }

    public static ArrayList<Tile> tileNB(String key) {
        ArrayList<Tile> value = ValidSets.getValueForKey(key);
        return value;
    }

}

