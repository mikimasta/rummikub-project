package com.rummikub.game;

import java.util.ArrayList;

public class AI {

    public void possibleMoveAddingRackToBoard(Tile[][] rack, Tile[][] board){
        
       //Tile[][] rack = Game.getInstance().currentPlayer.getHand(); // 2D array the hand of the player
        //Tile[][] board = GameboardGUI.getState(); // 2D array state of the board

        ArrayList<Tile> combined = new ArrayList<>(); // arraylist of board and 

        for (int i = 0; i < rack.length; i++) {
            for (int j = 0; j < rack[i].length; j++) {
                if (rack[i][j] != null) {
                    combined.add(rack[i][j]);
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    combined.add(board[i][j]);
                }
            }
        }

        ArrayList<Tile> groups = new ArrayList<>(); 
        groups = Game.orderRackByGroup(combined); // arraylist of combined ordered by groups
        
        String s = "";
        for (int i = 0; i < groups.size(); i++) {
            s += groups.get(i).getNumber() + " " + groups.get(i).getColorString() + " ";
        }
        System.out.println(s);

        ArrayList<Tile> runs = new ArrayList<>(); 
        runs = Game.orderRackByStairs(combined); // if joker will be at the front, 

        String ss = "";
        for (int i = 0; i < groups.size(); i++) {
            ss += groups.get(i).getNumber() + " " + groups.get(i).getColorString() + " ";
        }
        System.out.println(ss);


    }
}
