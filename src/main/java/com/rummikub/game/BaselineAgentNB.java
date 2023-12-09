package com.rummikub.game;

import javafx.scene.paint.Color;

import java.util.Iterator;
import java.util.ArrayList;

public class BaselineAgentNB {

    public static Object[] baselineAgentNB(ArrayList<Tile> hand, ArrayList<String> board){
        Object[] result = new Object[3];
        int count = 0;
        ArrayList<ArrayList<Tile>> newboard = new ArrayList<>();

        Iterator<String> boardIterator = board.iterator();
        while (boardIterator.hasNext()) {
            String set = boardIterator.next();
           //System.out.println(set);
            ArrayList<Tile> setTile = ValidSets.getSetForKey(set);
            //System.out.println(setTile);
            ArrayList<Tile> setNB = ValidSetsNB.getNBForKey(set);
            //System.out.println(setNB);
            for(Tile NB : setNB){
                Iterator<Tile> handIterator = hand.iterator();
                while (handIterator.hasNext()) {
                    Tile tileHand = handIterator.next();
                    if(NB==null){
                        break;
                    }
                    if ((NB.getColor()==tileHand.getColor())&&(NB.getNumber()== tileHand.getNumber())) {
                        //System.out.println("Match");
                        setTile.add(tileHand);
                        hand.remove(tileHand);
                        count++;
                        break;
                    }
                }
            }

            newboard.add(setTile);
        }

        result[0] = newboard;
        result[1] = hand;
        result[2] = count;

        //ArrayList<ArrayList<Tile>> printboard = (ArrayList<ArrayList<Tile>>) result[0];
        //System.out.println(Game.boardListToSetKey(printboard));
        return result;
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
        hand.add(tile29);
        hand.add(tile30);
        hand.add(tile31);
        baselineAgentNB(hand, Game.boardListToSetKey(fullBoard));
    }

}
