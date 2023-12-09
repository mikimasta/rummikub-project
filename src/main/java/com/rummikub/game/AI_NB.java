package com.rummikub.game;

import java.util.ArrayList;
import javafx.scene.paint.Color;

public class AI_NB {

    public static void possibleMoves(ArrayList<Tile> rack, ArrayList<ArrayList<Tile>> boardArray){
        /*
        * Add code for firstMove base on the BaselineAgentRack
        * */
        ArrayList<String> board = Game.boardListToSetKey(boardArray);
        System.out.println("Initial board");
        System.out.println(board);
        Object[] baselineResult = BaselineAgentNB.baselineAgentNB(rack, board);
        ArrayList<ArrayList<Tile>> newBoard = (ArrayList<ArrayList<Tile>>) baselineResult[0];
        ArrayList<Tile> newRack = (ArrayList<Tile>) baselineResult[1];
        int count = (int) baselineResult[2];
        ArrayList<Move> moves = (ArrayList<Move>) baselineResult[3];

        do {
            baselineResult = BaselineAgentNB.baselineAgentNB(newRack, Game.boardListToSetKey(newBoard));
            newBoard = (ArrayList<ArrayList<Tile>>) baselineResult[0];
            newRack = (ArrayList<Tile>) baselineResult[1];
            count = (int) baselineResult[2];
            moves.addAll((ArrayList<Move>)baselineResult[3]);

        } while (count != 0);

        if(moves.size() == 0){ //no tiles from the hand are valid
            System.out.println("no tiles from the hand can be added to the board");
            //newRack.add(Player.draw); //draw a tile from the pool
        }
        System.out.println("Final board");
        System.out.println(Game.boardListToSetKey(newBoard));
        System.out.println("Possible moves");
        System.out.println(moves);
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
        Tile tile8 = new Tile((byte) 4,Color.RED);
        Tile tile9 = new Tile((byte) 5, Color.RED);
        set3.add(tile7);
        set3.add(tile8);
        set3.add(tile9);

        ArrayList<Tile> set4 = new ArrayList<>();
        Tile tile10 = new Tile((byte) 3, Color.BLACK);
        Tile tile11 = new Tile((byte) -1,Color.BROWN);
        Tile tile12 = new Tile((byte) 5, Color.BLACK);
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
        fullBoard.add(set3);
        fullBoard.add(set4);
        fullBoard.add(set5);
        fullBoard.add(set6);
        fullBoard.add(set7);


        ArrayList<Tile> hand = new ArrayList<>();
        Tile tile26 = new Tile((byte) 4, Color.RED);
        Tile tile27 = new Tile((byte) 11, Color.ORANGE);
        Tile tile28 = new Tile((byte) 7, Color.ORANGE);
        Tile tile29 = new Tile((byte) 5, Color.BLACK);
        Tile tile30 = new Tile((byte) 13, Color.BLUE);
        Tile tile31 = new Tile((byte) 6, Color.BLACK);

        hand.add(tile26);
        hand.add(tile27);
        hand.add(tile28);
        hand.add(tile31);
        hand.add(tile30);
        hand.add(tile29);
        possibleMoves(hand,fullBoard);
    }


}