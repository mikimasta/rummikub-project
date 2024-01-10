package com.rummikub.model;

import com.rummikub.game.Game;
import com.rummikub.game.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import javafx.scene.paint.Color;

public class CaseBased {

    public static Object[] baselineAgentNB(ArrayList<Tile> hand, ArrayList<String> board) {
        Object[] result = new Object[3];
        int count = 0;
        ArrayList<ArrayList<Tile>> newboard = new ArrayList<>();

        //FIRST ONLY LOOK AT THE RACK
        if(hand.size()>2) {
            //System.out.println("check possible moves from the rack");
            do {
                ArrayList<Tile> resultRack = playTheRack(hand);
                //System.out.println(resultRack);
                if (resultRack == null) {
                    //System.out.println("no possible moves from the rack ");
                    break;
                } else {
                    System.out.println(" moves from the rack ");
                    for(Tile tilerack: resultRack){
                        if (hand.contains(tilerack)) {
                            hand.remove(hand.indexOf(tilerack));
                        }
                    }
                    //hand.removeAll(resultRack);
                    newboard.add(resultRack);
                    count += resultRack.size();
                }
            } while (true);
        }

        //LOOKING AT BOARD AND THE RACK
        Iterator<String> boardIterator = board.iterator();
        while (boardIterator.hasNext()) {
            int countset = 0;
            String set = boardIterator.next();
            ArrayList<Tile> setTile = Game.orderSetStairs(ValidSets.getSetForKey(set));
            ArrayList<Tile> createNewSet = new ArrayList<>();
            ArrayList<Tile> setNB = ValidSetsNB.getNBForKey(set);
            //System.out.println(set);
            //System.out.println(setTile.size());
            //REARRANGEMENT
            if (setTile!=null && setTile.size()>3) {
                //Set stairs
                if(Game.checkIfStairs(setTile)){
                    Tile first = setTile.get(0);
                    Tile last = setTile.get(setTile.size()-1);
                    if(addPartialRun(first, hand)!= null){
                        System.out.println("rearragement ");
                        createNewSet = addPartialRun(first, hand);
                        newboard.add(createNewSet);
                        hand.remove(hand.indexOf(createNewSet.get(0)));
                        hand.remove(hand.indexOf(createNewSet.get(1)));
                        setTile.remove(first);
                        newboard.add(setTile);
                        count += 2;
                        countset++;
                    }else if(addPartialRun(last, hand)!=null){
                        System.out.println("rearragement ");
                        createNewSet = addPartialRun(last, hand);
                        newboard.add(createNewSet);
                        hand.remove(hand.indexOf(createNewSet.get(0)));
                        hand.remove(hand.indexOf(createNewSet.get(1)));
                        setTile.remove(last);
                        newboard.add(setTile);
                        count += 2;
                        countset++;
                    }else if(addPartialGroup(first, hand) != null){
                        System.out.println("rearragement ");
                        createNewSet = addPartialGroup(first, hand);
                        newboard.add(createNewSet);
                        hand.remove(hand.indexOf(createNewSet.get(0)));
                        hand.remove(hand.indexOf(createNewSet.get(1)));
                        setTile.remove(first);
                        newboard.add(setTile);
                        count += 2;
                        countset++;

                    }else if(addPartialGroup(last, hand) != null){
                        System.out.println("rearragement ");
                        createNewSet = addPartialGroup(last, hand);
                        newboard.add(createNewSet);
                        hand.remove(hand.indexOf(createNewSet.get(0)));
                        hand.remove(hand.indexOf(createNewSet.get(1)));
                        setTile.remove(last);
                        newboard.add(setTile);
                        count += 2;
                        countset++;
                    }

                    // set group
                }else if(Game.checkIfGroup(setTile)){
                    for(Tile selected: setTile){
                        if(addPartialRun(selected, hand)!= null){
                            System.out.println("rearragement ");
                            createNewSet = addPartialRun(selected, hand);
                            newboard.add(createNewSet);
                            hand.remove(hand.indexOf(createNewSet.get(0)));
                            hand.remove(hand.indexOf(createNewSet.get(1)));
                            setTile.remove(selected);
                            newboard.add(setTile);
                            count += 2;
                            countset++;
                            break;

                        }else if(addPartialGroup(selected, hand) != null) {
                            System.out.println("rearragement ");
                            createNewSet = addPartialGroup(selected, hand);
                            newboard.add(createNewSet);
                            hand.remove(hand.indexOf(createNewSet.get(0)));
                            hand.remove(hand.indexOf(createNewSet.get(1)));
                            setTile.remove(selected);
                            newboard.add(setTile);
                            count += 2;
                            countset++;
                            break;
                        }
                    }
                }
                //IF THE SET STARTS WITH JOCKER
            }
            /*
            if (set.startsWith("-1") &&(setTile.size()>3)) {
                System.out.println("condition joker");
                ArrayList<Tile> setOrder = Game.orderSetStairs(setTile);
                Tile joker = setOrder.get(setTile.size()-1);
                //SET GROUP
                setTile.remove(joker);
                ArrayList<Tile> consecutive = getTilesWithConsecutiveNumbers(hand);
                ArrayList<Tile> sameNum = getTilesWithSameNumbers(hand);
                if(Game.checkIfGroup(setTile)&&((sameNum!=null)||(consecutive!=null))){
                    if(consecutive!=null){
                        System.out.println("joker in Group + consecutive ");
                        createNewSet.add(consecutive.get(0));
                        createNewSet.add(consecutive.get(1));
                        createNewSet.add(joker);
                        hand.remove(createNewSet.get(0));
                        hand.remove(createNewSet.get(1));
                        newboard.add(createNewSet);
                        //newboard.add(setTile);
                        count += 2;
                    }else{
                        System.out.println("joker in Group + sameNum ");
                        createNewSet.add(sameNum.get(0));
                        createNewSet.add(sameNum.get(1));
                        createNewSet.add(joker);
                        hand.remove(createNewSet.get(0));
                        hand.remove(createNewSet.get(1));
                        newboard.add(createNewSet);
                        //newboard.add(setTile);
                        count += 2;
                    }
                }else if(Game.checkIfStairs(setTile)&&((sameNum!=null)||(consecutive!=null))){
                    if(consecutive!=null){
                        System.out.println("joker in Run + consecutive ");
                        createNewSet.add(consecutive.get(0));
                        createNewSet.add(consecutive.get(1));
                        createNewSet.add(joker);
                        hand.remove(createNewSet.get(0));
                        hand.remove(createNewSet.get(1));
                        newboard.add(createNewSet);
                        count += 2;
                    }else{
                        System.out.println("joker in Group + sameNum ");
                        createNewSet.add(sameNum.get(0));
                        createNewSet.add(sameNum.get(1));
                        createNewSet.add(joker);
                        hand.remove(createNewSet.get(0));
                        hand.remove(createNewSet.get(1));
                        newboard.add(createNewSet);
                        count += 2;
                    }
                }else{
                    setTile.add(joker);
                }
            }*/

            if(setTile!=null&&setTile.size()==5){
                Iterator<Tile> handIterator = hand.iterator();
                while (handIterator.hasNext()) {
                    Tile tileHand = handIterator.next();
                    if (tileHand==setTile.get(2)) { //there is a Match
                        System.out.println("rearregement run size 5");
                        ArrayList<Tile> tileAdded = new ArrayList<>();
                        tileAdded.add(tileHand);
                        tileAdded.add(setTile.get(0));
                        tileAdded.add(setTile.get(1));
                        newboard.add(tileAdded);
                        setTile.remove(setTile.get(0));
                        setTile.remove(setTile.get(1));
                        count++;
                        break;
                    }
                }
            }
            //SEARCH BASED ON ONE NB
            if(setTile!=null&&countset==0) {
                for (Tile NB : setNB) {
                    for(Tile tileHand: hand){
                        if (NB == null) {break;}
                        if ((NB.getColor() == tileHand.getColor()) && (NB.getNumber() == tileHand.getNumber())) { //there is a Match
                            System.out.println("There's a valid NB");
                            ArrayList<Tile> tileAdded = new ArrayList<>();
                            tileAdded.add(tileHand);
                            setTile.add(tileHand);
                            hand.remove(hand.indexOf(tileHand));
                            count++;
                            setTile = Game.orderSetStairs(setTile);
                            /*
                            if(setTile.get(setTile.size()-1).getNumber()==-1){
                                ArrayList<ArrayList<Tile>> boardSet = new ArrayList<>();
                                boardSet.add(setTile);
                                setTile = ValidSets.getSetForKey(Game.boardListToSetKey(boardSet).get(0));
                                System.out.println("re order joker"+setTile);

                            }
                            */

                            System.out.println("hand " + hand);
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
        //System.out.println(newboard);
        //System.out.println(hand);

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
        ArrayList<Tile> resultTiles = new ArrayList<>();
        for (int i = 0; i < tiles.size() - 1; i++) {
            int currentNumber = tiles.get(i).getNumber();
            int nextNumber = tiles.get(i + 1).getNumber();
            if ((currentNumber == nextNumber-1)&&(tiles.get(i).getColor()==tiles.get(i+1).getColor())) {
                resultTiles.add(tiles.get(i));
                resultTiles.add(tiles.get(i + 1));
            }else if((currentNumber == nextNumber-2)&&(tiles.get(i).getColor()==tiles.get(i+1).getColor())) {
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
                boolean condition2 = (((sameNum.get(i).getColor()) != tile.getColor()) && ((sameNum.get(i + 1).getColor()) != tile.getColor()));
                boolean condition3 = (num==-1);
                if ((condition1 && condition2)|| condition3) {
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
                boolean condition3 = (tile.getNumber()==-1);
                if (condition1 ||condition2||condition3) {
                    newSet.add(consecutive.get(i));
                    newSet.add(consecutive.get(i + 1));
                    newSet.add(tile);
                    return newSet;
                }
            }
        }
        return null;
    }

    public static ArrayList<Tile> playTheRack(ArrayList<Tile> hand) {
        //System.out.println(hand);
        ArrayList<Tile> set = new ArrayList<>();
        ArrayList<Tile> handNoDuplicates = new ArrayList<>(new HashSet<>(hand));
        //System.out.println(handNoDuplicates);


        //SET STAIRS
        handNoDuplicates = Game.orderSetStairs(handNoDuplicates);
        //System.out.println(handNoDuplicates);
        for (int i = 0; i < handNoDuplicates.size() - 2; i++) {
            Tile t1 = handNoDuplicates.get(i);
            Tile t2 = handNoDuplicates.get(i + 1);
            Tile t3 = handNoDuplicates.get(i + 2);
            if ((t1.getNumber() == t3.getNumber() - 2) && t1.getColor().equals(t2.getColor()) && t1.getColor().equals(t3.getColor())) {
                set.add(t1);
                set.add(t2);
                set.add(t3);
                return set;
            }
        }

        //SET GROUP
        handNoDuplicates = Game.orderSetGroup(handNoDuplicates);
        //System.out.println(handNoDuplicates);
        for (int i = 0; i < handNoDuplicates.size() - 2; i++) {
            Tile t1 = handNoDuplicates.get(i);
            Tile t2 = handNoDuplicates.get(i + 1);
            Tile t3 = handNoDuplicates.get(i + 2);

            if ((t1.getNumber() == t3.getNumber()) && (!t1.getColor().equals(t2.getColor())) && (!t1.getColor().equals(t3.getColor())) && !t2.getColor().equals(t3.getColor())) {
                set.add(t1);
                set.add(t2);
                set.add(t3);
                return set;
            }
        }

        //JOKER
        /*
        if (handNoDuplicates.get(0).getNumber() == -1) {
            ArrayList<Tile> consecutiveTiles = AgentNBHelper.getTilesWithConsecutiveNumbers(hand);
            //System.out.println(consecutiveTiles);
            ArrayList<Tile> sameNumberTiles = AgentNBHelper.getTilesWithSameNumbers(hand);
            //System.out.println("hand has a joker");
            if (!consecutiveTiles.isEmpty()) {
                set.add(consecutiveTiles.get(0));
                set.add(consecutiveTiles.get(1));
                set.add(handNoDuplicates.get(0));
                return set;
            } else if (!sameNumberTiles.isEmpty()) {
                set.add(sameNumberTiles.get(0));
                set.add(sameNumberTiles.get(1));
                set.add(handNoDuplicates.get(0));
                return set;
            }
        }*/

        return null;
    }

}

