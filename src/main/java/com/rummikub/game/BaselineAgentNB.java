package com.rummikub.game;


import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;

public class BaselineAgentNB {

    public static Object[] baselineAgentNB(ArrayList<Tile> hand, ArrayList<String> board) {
        Object[] result = new Object[4];
        int count = 0;
        ArrayList<ArrayList<Tile>> newboard = new ArrayList<>();
        ArrayList<Move> moves = new ArrayList<>();

        Iterator<String> boardIterator = board.iterator();
        while (boardIterator.hasNext()) {
            int countset = 0;
            String set = boardIterator.next();
            ArrayList<Tile> setTile = Game.orderSetStairs(ValidSets.getSetForKey(set));
            ArrayList<Tile> createNewSet = new ArrayList<>();
            ArrayList<Tile> setNB = ValidSetsNB.getNBForKey(set);
            //REARRANGEMENT
            if (setTile.size()>3) {
                if(Game.checkIfStairs(setTile)){
                    Tile first = setTile.get(0);
                    Tile last = setTile.get(setTile.size()-1);
                    if(addPartialRun(first, hand)!= null){
                        createNewSet = addPartialRun(first, hand);
                        newboard.add(createNewSet);
                        hand.remove(createNewSet.get(0));
                        hand.remove(createNewSet.get(1));
                        setTile.remove(first);
                        newboard.add(setTile);
                        count += 2;
                        countset++;
                    }else if(addPartialRun(last, hand)!=null){
                        createNewSet = addPartialRun(last, hand);
                        newboard.add(createNewSet);
                        hand.remove(createNewSet.get(0));
                        hand.remove(createNewSet.get(1));
                        setTile.remove(last);
                        newboard.add(setTile);
                        count += 2;
                        countset++;

                    }else if(addPartialGroup(first, hand) != null){
                        createNewSet = addPartialGroup(first, hand);
                        newboard.add(createNewSet);
                        hand.remove(createNewSet.get(0));
                        hand.remove(createNewSet.get(1));
                        setTile.remove(first);
                        newboard.add(setTile);
                        count += 2;
                        countset++;

                    }else if(addPartialGroup(last, hand) != null){
                        createNewSet = addPartialGroup(last, hand);
                        newboard.add(createNewSet);
                        hand.remove(createNewSet.get(0));
                        hand.remove(createNewSet.get(1));
                        setTile.remove(last);
                        newboard.add(setTile);
                        count += 2;
                        countset++;
                    }
                }else if(Game.checkIfGroup(setTile)){
                    for(Tile selected: setTile){
                        //System.out.println(selected.getNumber()+" "+ Game.getStringFromColor(selected));
                        if(addPartialRun(selected, hand)!= null){
                            createNewSet = addPartialRun(selected, hand);
                            newboard.add(createNewSet);
                            hand.remove(createNewSet.get(0));
                            hand.remove(createNewSet.get(1));
                            setTile.remove(selected);
                            newboard.add(setTile);
                            count += 2;
                            countset++;
                            break;

                        }else if(addPartialGroup(selected, hand) != null) {
                            createNewSet = addPartialGroup(selected, hand);
                            newboard.add(createNewSet);
                            hand.remove(createNewSet.get(0));
                            hand.remove(createNewSet.get(1));
                            setTile.remove(selected);
                            newboard.add(setTile);
                            count += 2;
                            countset++;
                            break;
                        }
                    }
                }
            }
            //IF THE SET STARTS WITH JOCKER
            if (set.startsWith("-1None") &&(setTile.size()>3)) {
                ArrayList<Tile> setOrder = Game.orderSetStairs(setTile);
                Tile joker = setOrder.get(setTile.size()-1);
                if((setOrder.get(setOrder.size()-2).getNumber()== setOrder.get(setOrder.size()-3).getNumber())&&(setOrder.get(setOrder.size()-3).getNumber() == setOrder.get(setOrder.size()-4).getNumber())){
                    ArrayList<Tile> consecutive = getTilesWithConsecutiveNumbers(hand);
                    ArrayList<Tile> sameNum = getTilesWithSameNumbers(hand);
                    if(!consecutive.isEmpty()){
                        createNewSet.add(consecutive.get(0));
                        createNewSet.add(consecutive.get(1));
                        createNewSet.add(joker);
                        hand.remove(createNewSet.get(0));
                        hand.remove(createNewSet.get(1));
                        setTile.remove(joker);
                        newboard.add(createNewSet);
                        newboard.add(setTile);
                        count += 2;
                        countset++;
                    }else if(!sameNum.isEmpty()){
                        createNewSet.add(sameNum.get(0));
                        createNewSet.add(sameNum.get(1));
                        createNewSet.add(joker);
                        hand.remove(createNewSet.get(0));
                        hand.remove(createNewSet.get(1));
                        setTile.remove(joker);
                        newboard.add(createNewSet);
                        newboard.add(setTile);
                        count += 2;
                        countset++;
                    }else{
                        newboard.add(setTile);
                    }
                }
            }
            //SEARCH BASED ON ONE NB
            if(countset==0) {
                for (Tile NB : setNB) {
                    Iterator<Tile> handIterator = hand.iterator();
                    while (handIterator.hasNext()) {
                        Tile tileHand = handIterator.next();
                        if (NB == null) {break;}
                        if ((NB.getColor() == tileHand.getColor()) && (NB.getNumber() == tileHand.getNumber())) { //there is a Match
                            ArrayList<Tile> tileAdded = new ArrayList<>();
                            tileAdded.add(tileHand);
                            Move move = new Move(setTile, tileAdded);
                            setTile.add(tileHand);
                            hand.remove(tileHand);
                            moves.add(move);
                            count++;
                            setTile = Game.orderSetStairs(setTile);
                            break;
                        }
                    }
                }
                newboard.add(setTile);
            }
        }
        result[0] = newboard;
        result[1] = hand;
        result[2] = count;
        result[3] = moves;
        return result;
    }


    public static ArrayList<Tile> getTilesWithSameNumbers(ArrayList<Tile> tiles) {
        tiles = Game.orderSetGroup(tiles);
        ArrayList<Tile> resultTiles = new ArrayList<>();
        for (int i = 0; i < tiles.size() - 1; i++) {
            int currentNumber = tiles.get(i).getNumber();
            int nextNumber = tiles.get(i + 1).getNumber();
            if ((currentNumber == nextNumber)&&(tiles.get(i).getColor()!=tiles.get(i+1).getColor())) {
                resultTiles.add(tiles.get(i));
                resultTiles.add(tiles.get(i + 1));
            }
        }
        return resultTiles;
    }

    public static ArrayList<Tile> getTilesWithConsecutiveNumbers(ArrayList<Tile> tiles) {
        tiles = Game.orderSetStairs(tiles);
        //System.out.println(tiles);
        ArrayList<Tile> resultTiles = new ArrayList<>();
        for (int i = 0; i < tiles.size() - 1; i++) {
            int currentNumber = tiles.get(i).getNumber();
            int nextNumber = tiles.get(i + 1).getNumber();
            if ((currentNumber == nextNumber-1)&&(tiles.get(i).getColor()==tiles.get(i+1).getColor())) {
                resultTiles.add(tiles.get(i));
                resultTiles.add(tiles.get(i + 1));
            }
        }
        return resultTiles;
    }

    public static ArrayList<Tile> addPartialGroup(Tile tile, ArrayList<Tile> hand) {
        int num = tile.getNumber();
        ArrayList<Tile> sameNum = getTilesWithSameNumbers(hand);
        ArrayList<Tile> newSet = new ArrayList<>();
        if (!sameNum.isEmpty()) {
            for (int i = 0; i < sameNum.size(); i += 2) {
                boolean condition1 = (sameNum.get(i).getNumber() == num);
                boolean condition2 = (((sameNum.get(i).getColor()) == tile.getColor()) && ((sameNum.get(i + 1).getColor()) == tile.getColor()));
                if (condition1 && condition2) {
                    newSet.add(sameNum.get(i));
                    newSet.add(sameNum.get(i + 1));
                    newSet.add(tile);
                    return newSet;
                }
            }
        }
        return null;
    }
    public static ArrayList<Tile> addPartialRun(Tile tile, ArrayList<Tile> hand) {
        ArrayList<Tile> consecutive = getTilesWithConsecutiveNumbers(hand);
        ArrayList<Tile> newSet = new ArrayList<>();
        if (!consecutive.isEmpty()) {
            for (int i = 0; i < consecutive.size(); i += 2) {
                boolean condition1 = ((consecutive.get(i).getNumber()) == tile.getNumber()+1) && (consecutive.get(i).getColor() == tile.getColor());
                boolean condition2 = ((consecutive.get(i).getNumber() + 2) == tile.getNumber()) && (consecutive.get(i).getColor() == tile.getColor());
                if (condition1 || condition2) {
                    newSet.add(consecutive.get(i));
                    newSet.add(consecutive.get(i + 1));
                    newSet.add(tile);
                    return newSet;
                }
            }
        }
        return null;
    }

}
