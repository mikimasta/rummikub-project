package com.rummikub.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import com.rummikub.game.Game;
import com.rummikub.game.Tile;

import javafx.scene.paint.Color;

public class AgentNB {

    public static ArrayList<ArrayList<Tile>> possibleMoves(ArrayList<Tile> rack, ArrayList<ArrayList<Tile>> boardArray){
        //System.out.println("Agent NB used");
        ArrayList<String> board = Game.boardListToSetKey(boardArray);

        System.out.println("Initial board");
        System.out.println(board);
        //System.out.println("Initial hand");
        //System.out.println(rack);

        int searchNum = 0;
        do {
            if(boardArray!=null) {
                Object[] baselineResult = CaseBased.baselineAgentNB(rack, Game.boardListToSetKey(boardArray));
                boardArray = (ArrayList<ArrayList<Tile>>) baselineResult[0];
                rack = (ArrayList<Tile>) baselineResult[1];
                int count = (int) baselineResult[2];
                searchNum++;
                if(count==0){
                    break;
                }
                System.out.println("loop "+ searchNum);
                System.out.println(Game.boardListToSetKey(boardArray));
            }
            //System.out.println(count);
        } while (true);

        //System.out.println(searchNum);
        if(searchNum < 2){ //no tiles from the hand are valid
            System.out.println("no tiles from the hand can be added to the board");
            return null;
        }



        System.out.println("Final board");
        ArrayList<String> finalBoardString = (Game.boardListToSetKey(boardArray));
        System.out.println(finalBoardString);
        //System.out.println("Final hand size");
        //System.out.println(rack);
        ArrayList<ArrayList<Tile >> finalBoard = new ArrayList<>();
        for(String finalSet: finalBoardString){
            finalBoard.add(ValidSets.getSetForKey(finalSet));
        }
        System.out.println(finalBoard);
        return finalBoard;

    }

    public static void main(String[] args) {

        Tile joker = new Tile((byte) -1, Color.BROWN);

        Tile rr1 = new Tile((byte) 1, Color.RED);
        Tile rr2 = new Tile((byte) 2,Color.RED);
        Tile rr3 = new Tile((byte) 3, Color.RED);
        Tile rr4 = new Tile((byte) 4,Color.RED);
        Tile rr5 = new Tile((byte) 5, Color.RED);
        Tile rr6 = new Tile((byte) 6,Color.RED);
        Tile rr7 = new Tile((byte) 7, Color.RED);
        Tile rr8 = new Tile((byte) 8,Color.RED);
        Tile rr9 = new Tile((byte) 9, Color.RED);
        Tile rr10 = new Tile((byte) 10,Color.RED);
        Tile rr11 = new Tile((byte) 11, Color.RED);
        Tile rr12 = new Tile((byte) 12,Color.RED);
        Tile rr13 = new Tile((byte) 13, Color.RED);

        Tile oo1 = new Tile((byte) 1, Color.ORANGE);
        Tile oo2 = new Tile((byte) 2,Color.ORANGE);
        Tile oo3 = new Tile((byte) 3, Color.ORANGE);
        Tile oo4 = new Tile((byte) 4,Color.ORANGE);
        Tile oo5 = new Tile((byte) 5, Color.ORANGE);
        Tile oo6 = new Tile((byte) 6,Color.ORANGE);
        Tile oo7 = new Tile((byte) 7, Color.ORANGE);
        Tile oo8 = new Tile((byte) 8,Color.ORANGE);
        Tile oo9 = new Tile((byte) 9, Color.ORANGE);
        Tile oo10 = new Tile((byte) 10,Color.ORANGE);
        Tile oo11 = new Tile((byte) 11, Color.ORANGE);
        Tile oo12 = new Tile((byte) 12,Color.ORANGE);
        Tile oo13 = new Tile((byte) 13, Color.ORANGE);

        Tile bu1 = new Tile((byte) 1, Color.BLUE);
        Tile bu2 = new Tile((byte) 2,Color.BLUE);
        Tile bu3 = new Tile((byte) 3, Color.BLUE);
        Tile bu4 = new Tile((byte) 4,Color.BLUE);
        Tile bu5 = new Tile((byte) 5, Color.BLUE);
        Tile bu6 = new Tile((byte) 6,Color.BLUE);
        Tile bu7 = new Tile((byte) 7, Color.BLUE);
        Tile bu8 = new Tile((byte) 8,Color.BLUE);
        Tile bu9 = new Tile((byte) 9, Color.BLUE);
        Tile bu10 = new Tile((byte) 10,Color.BLUE);
        Tile bu11 = new Tile((byte) 11, Color.BLUE);
        Tile bu12 = new Tile((byte) 12,Color.BLUE);
        Tile bu13 = new Tile((byte) 13, Color.BLUE);

        Tile bl1 = new Tile((byte) 1, Color.BLACK);
        Tile bl2 = new Tile((byte) 2,Color.BLACK);
        Tile bl3 = new Tile((byte) 3, Color.BLACK);
        Tile bl4 = new Tile((byte) 4,Color.BLACK);
        Tile bl5 = new Tile((byte) 5, Color.BLACK);
        Tile bl6 = new Tile((byte) 6,Color.BLACK);
        Tile bl7 = new Tile((byte) 7, Color.BLACK);
        Tile bl8 = new Tile((byte) 8,Color.BLACK);
        Tile bl9 = new Tile((byte) 9, Color.BLACK);
        Tile bl10 = new Tile((byte) 10,Color.BLACK);
        Tile bl11 = new Tile((byte) 11, Color.BLACK);
        Tile bl12 = new Tile((byte) 12,Color.BLACK);
        Tile bl13 = new Tile((byte) 13, Color.BLACK);

        ArrayList<Tile> set1 = new ArrayList<>();
        set1.add(bl5);
        set1.add(bl6);
        set1.add(joker);
        set1.add(bl8);
        set1.add(bl9);

        ArrayList<Tile> set2 = new ArrayList<>();
        set2.add(bu5);
        set2.add(bu6);
        set2.add(bu7);
        set2.add(bu4);

        ArrayList<Tile> set3 = new ArrayList<>();
        set3.add(rr1);
        set3.add(rr2);
        set3.add(rr3);
        set3.add(joker);

        ArrayList<Tile> set4 = new ArrayList<>();
        set4.add(oo10);
        set4.add(oo11);
        set4.add(oo12);
        set4.add(oo13);

        ArrayList<Tile> set5 = new ArrayList<>();
        set5.add(rr11);
        set5.add(oo11);
        set5.add(bu11);
        set5.add(bl11);

        ArrayList<Tile> set6 = new ArrayList<>();
        set6.add(rr7);
        set6.add(bl7);
        set6.add(bu7);

        ArrayList<ArrayList<Tile>> fullBoard = new ArrayList<>();
        fullBoard.add(set1);
        fullBoard.add(set2);
        fullBoard.add(set3);
        fullBoard.add(set4);
        fullBoard.add(set5);
        fullBoard.add(set6);

        ArrayList<Tile> hand = new ArrayList<>();
        hand.add(oo1);
        hand.add(oo11);
        hand.add(bl9);
        hand.add(joker);
        hand.add(oo9);
        hand.add(rr7);
        hand.add(rr3);
        hand.add(bl3);
        hand.add(bl8);
        hand.add(bu5);
        hand.add(bl13);

        ArrayList<Tile> set8 = new ArrayList<>();
        set8.add(bu2);
        set8.add(bl2);
        set8.add(oo2);


        ArrayList<ArrayList<Tile>> fullBoard2 = new ArrayList<>();
        fullBoard2.add(set8);

        ArrayList<Tile> hand2 = new ArrayList<>();
        hand2.add(bu4);
        hand2.add(bu6);
        hand2.add(bu7);
        //hand2.add(rr5);
        possibleMoves(hand, fullBoard2);
        //CaseBased.playTheRack(hand);
        //System.out.print(CaseBased.playTheRack(hand));
        //System.out.println(hand);
        //System.out.println(CaseBased.getTilesWithSameNumbers(hand));
        //System.out.println(CaseBased.getTilesWithConsecutiveNumbers(hand));
        //System.out.println(hand2);
        //System.out.println(CaseBased.addPartialRun(bl3,hand2));
        //System.out.println(CaseBased.getTilesWithConsecutiveNumbers(hand2));
        //System.out.println(CaseBased.addPartialGroup(oo11,hand2));
        //System.out.print(CaseBased.playTheRack(hand));
    }

}
