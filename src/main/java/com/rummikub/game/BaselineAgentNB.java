package com.rummikub.game;


import javafx.scene.paint.Color;

import java.util.Iterator;
import java.util.ArrayList;

public class BaselineAgentNB {

    public static Object[] baselineAgentNB(ArrayList<Tile> hand, ArrayList<String> board){
        Object[] result = new Object[4];
        int count = 0;
        ArrayList<ArrayList<Tile>> newboard = new ArrayList<>();
        ArrayList<Move> moves = new ArrayList<>();

        Iterator<String> boardIterator = board.iterator();
        while (boardIterator.hasNext()) {
            String set = boardIterator.next();
            ArrayList<Tile> setTile = Game.orderSetStairs(ValidSets.getSetForKey(set));
            ArrayList<Tile> setNB = ValidSetsNB.getNBForKey(set);
            ArrayList<Tile> createNewSet = new ArrayList<>();
            if(setTile.size()>3){ //Intentar coger dos tiles de rack creando un nuevo set con una tile de board
                if(set.startsWith("-1None")){
                    Tile joker = Game.orderSetStairs(setTile).get(0);
                    if(!getTilesWithConsecutiveNumbers(hand).isEmpty()) {
                        Tile t1 = getTilesWithConsecutiveNumbers(hand).get(0);
                        Tile t2 = getTilesWithConsecutiveNumbers(hand).get(1);
                        createNewSet.add(t1);hand.remove(t1);
                        createNewSet.add(t2);hand.remove(t2);
                        createNewSet.add(joker);setTile.remove(joker);
                        newboard.add(setTile);newboard.add(createNewSet);
                        break;
                    } else if(!getTilesWithSameNumbers(hand).isEmpty()){
                        Tile t1 = getTilesWithSameNumbers(hand).get(0);
                        Tile t2 = getTilesWithSameNumbers(hand).get(1);
                        createNewSet.add(t1); hand.remove(t1);
                        createNewSet.add(t2); hand.remove(t2);
                        createNewSet.add(joker); setTile.remove(joker);
                        newboard.add(setTile); newboard.add(createNewSet);
                        break;
                    }
                }else if(Game.checkIfGroup(setTile)){
                    ArrayList<Tile> consecutive = getTilesWithConsecutiveNumbers(hand);
                    if(!consecutive.isEmpty()) {
                        for (int i = 0; i < consecutive.size() ; i += 2) {
                            if(consecutive.get(i).getNumber()==setTile.get(0).getNumber()){
                                Tile t1 = getTilesWithConsecutiveNumbers(hand).get(i);
                                Tile t2 = getTilesWithConsecutiveNumbers(hand).get(i+1);
                                for (int j = 0; j < 4; j++) {
                                    if((setTile.get(i).getColor()!=t1.getColor())&&(setTile.get(i).getColor()!=t2.getColor())) {
                                        createNewSet.add(t1);
                                        hand.remove(t1);
                                        createNewSet.add(t2);
                                        hand.remove(t2);
                                        createNewSet.add(setTile.get(i));
                                        setTile.remove(setTile.get(i));
                                        newboard.add(setTile);
                                        newboard.add(createNewSet);
                                        break;
                                    }
                                }
                            }
                        }

                        newboard.add(setTile);
                        newboard.add(createNewSet);
                        break;
                    }
                } else if(Game.checkIfStairs(setTile)) {

                }


            }
                for(Tile NB : setNB){
                Iterator<Tile> handIterator = hand.iterator();
                while (handIterator.hasNext()) {
                    Tile tileHand = handIterator.next();
                    if(NB==null){
                        break;
                    }
                    if ((NB.getColor()==tileHand.getColor())&&(NB.getNumber()== tileHand.getNumber())) { //there is a Match
                        ArrayList<Tile> tileAdded = new ArrayList<>();
                        tileAdded.add(tileHand);
                        Move move = new Move(setTile, tileAdded);
                        setTile.add(tileHand);
                        hand.remove(tileHand);
                        moves.add(move);
                        count++;
                        break;
                    }
                }
            }
            setTile = Game.orderSetStairs(setTile);
            newboard.add(setTile);
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
        System.out.println(tiles);
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

}
