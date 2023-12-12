package com.rummikub.game;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;


public class Experiments {

    private static ArrayList<ArrayList<Tile>> randomValidSets(){
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
        

        ArrayList<ArrayList<Tile>> randomSets = new ArrayList<>();
        randomSets.add(set1);
        randomSets.add(set2);
        randomSets.add(set3);
        randomSets.add(set4);
        randomSets.add(set5);
        randomSets.add(set6);
        randomSets.add(set7);

        return randomSets;
    }

     private static ArrayList<Tile> allTiles(){
        ArrayList<Tile> allTiles = new ArrayList<>();

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

        allTiles.add(joker);

        allTiles.add(rr1);
        allTiles.add(rr2);
        allTiles.add(rr3);
        allTiles.add(rr4);
        allTiles.add(rr5);
        allTiles.add(rr6);
        allTiles.add(rr7);
        allTiles.add(rr8);
        allTiles.add(rr9);
        allTiles.add(rr10);
        allTiles.add(rr11);
        allTiles.add(rr12);
        allTiles.add(rr13);

        allTiles.add(oo1);
        allTiles.add(oo2);
        allTiles.add(oo3);
        allTiles.add(oo4);
        allTiles.add(oo5);
        allTiles.add(oo6);
        allTiles.add(oo7);
        allTiles.add(oo8);
        allTiles.add(oo9);
        allTiles.add(oo10);
        allTiles.add(oo11);
        allTiles.add(oo12);
        allTiles.add(oo13);

        allTiles.add(bu1);
        allTiles.add(bu2);
        allTiles.add(bu3);
        allTiles.add(bu4);
        allTiles.add(bu5);
        allTiles.add(bu6);
        allTiles.add(bu7);
        allTiles.add(bu8);
        allTiles.add(bu9);
        allTiles.add(bu10);
        allTiles.add(bu11);
        allTiles.add(bu12);
        allTiles.add(bu13);

        allTiles.add(bl1);
        allTiles.add(bl2);
        allTiles.add(bl3);
        allTiles.add(bl4);
        allTiles.add(bl5);
        allTiles.add(bl6);
        allTiles.add(bl7);
        allTiles.add(bl8);
        allTiles.add(bl9);
        allTiles.add(bl10);
        allTiles.add(bl11);
        allTiles.add(bl12);
        allTiles.add(bl13);


        return allTiles;
     }


    private static Object[]getTestState(int numTiles){
        ArrayList<ArrayList<Tile>> randomSets = randomValidSets();
        ArrayList<Tile> allTiles = allTiles();

        ArrayList<ArrayList<Tile>> board = new ArrayList<ArrayList<Tile>>();
        ArrayList<Tile> hand = new ArrayList<>();
        int count = 0;
        Random randomSet = new Random();
        Random randomTile = new Random();
        Random randomTest = new Random();
        while(count < numTiles){
            int test = randomTest.nextInt(2);
            if(test == 1){
                int randomSetIndex = randomSet.nextInt(randomSets.size());
                board.add(randomSets.get(randomSetIndex));

                count = count + randomSets.get(randomSetIndex).size();

            }
            else if(test == 0){
            int randomIndex = randomTile.nextInt(allTiles.size());
            hand.add(allTiles.get(randomIndex));
                count = count + 1;
            }
        }
        Object[] state = {hand,board};
        return state;
    }
   
    private static long testMain(int numTiles){
        Object[] testState = getTestState(numTiles);
        Tile[][] hand = (Tile[][]) testState[0];
        Tile[][] board = (Tile[][])testState[1];
        // Record start time
        long startTime = System.currentTimeMillis();

     
        AgentImplementation.makeMove(hand,board);

        // Record end time
        long endTime = System.currentTimeMillis();

        // Calculate elapsed time
        long elapsedTime = endTime - startTime;

        System.out.println("Elapsed time: " + elapsedTime + " milliseconds for " + numTiles + " amount of tiles");
        return elapsedTime;
    }

    private static void runTests(){
        int[] numTilesTest = {20,30,40,50};
        int numOfRuns = 10;

        for(int i = 0; i < numTilesTest.length; i++){
            long average = 0;
            for(int j = 0; j < numOfRuns; j++){
                average += testMain(numTilesTest[i]);
            }
            average = average/numOfRuns;
        
            System.out.println("Average Time For " + numOfRuns + " Run of " + numTilesTest[i] + " Tiles: " + average + " (milliseconds)");
        }


    }
    public static void main(String[] args) {
        runTests();
    }

    }

