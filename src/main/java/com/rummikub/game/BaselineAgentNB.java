package com.rummikub.game;


import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;

public class BaselineAgentNB {
    /**
     * Executes the decision-making process for placing tiles on the Rummikub board,
     * considering the given hand of tiles and the board configuration.
     *
     * This method iterates through the board and analyzes various conditions to form sets
     * based on Rummikub rules. It examines different scenarios including complete sets,
     * partial sets, consecutive numbers, and groups of tiles to determine valid moves.
     *
     * The decision-making process includes analyzing the hand of tiles to identify potential
     * matches with the existing board configuration, optimizing sets, and rearranging tiles
     * to form valid Rummikub sets according to the game rules.
     *
     * @param hand The current hand of tiles available for placement.
     * @param board The current board configuration represented as an ArrayList of strings.
     * @return An Object array containing the resulting board configuration, updated hand after
     *         placing tiles, and the count of tiles placed. The returned array format is:
     *         - Index 0: ArrayList of ArrayLists representing the updated board configuration.
     *         - Index 1: Updated hand after placing tiles, represented as an ArrayList of tiles.
     *         - Index 2: Count of tiles placed during this decision-making process.
     */
    public static Object[] baselineAgentNB(ArrayList<Tile> hand, ArrayList<String> board) {
        Object[] result = new Object[3];
        int count = 0;
        ArrayList<ArrayList<Tile>> newboard = new ArrayList<>();
        //FIRST ONLY LOOK AT THE RACK
        int countRackPlays = 1;
        do{
            ArrayList<Tile> resultRack = playTheRack(hand);
            if(resultRack==null){
                countRackPlays=0;
            }else {
                hand.removeAll(resultRack);
                newboard.add(resultRack);
                count += resultRack.size();
            }
        }while(countRackPlays!=0);

        //LOOKING AT BOARD AND THE RACK
        Iterator<String> boardIterator = board.iterator();
        while (boardIterator.hasNext()) { //selects iteratively a set from the board
            int countset = 0;
            String set = boardIterator.next();
            ArrayList<Tile> setTile = Game.orderSetStairs(ValidSets.getSetForKey(set));
            ArrayList<Tile> createNewSet = new ArrayList<>();
            ArrayList<Tile> setNB = ValidSetsNB.getNBForKey(set);
            //REARRANGEMENT TAKING PARTIAL SETS
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
            //SEARCH BASED ON ONE NEIGHBOURS
            if(countset==0) {
                for (Tile NB : setNB) {
                    Iterator<Tile> handIterator = hand.iterator();
                    while (handIterator.hasNext()) {
                        Tile tileHand = handIterator.next();
                        if (NB == null) {break;}
                        if ((NB.getColor() == tileHand.getColor()) && (NB.getNumber() == tileHand.getNumber())) { //there is a Match
                            ArrayList<Tile> tileAdded = new ArrayList<>();
                            tileAdded.add(tileHand);
                            setTile.add(tileHand);
                            hand.remove(tileHand);
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
        return result;
    }

    /**
     * Retrieves a list of pairs of tiles with the same numbers but different colors.
     * @param tiles The list of tiles to analyze.
     * @return An ArrayList of pairs of tiles that have the same number but different colors consecutively(partial sets).
     */
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

    /**
     * Retrieves a list of pairs of tiles with consecutive numbers and the same color.
     * @param tiles The list of tiles to analyze.
     * @return An ArrayList of tiles with consecutive numbers and the same color(partial sets).
     */
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

    /**
     * Finds and creates a group of tiles including the provided tile from the given hand.
     * @param tile The tile to include in the partial group.
     * @param hand The hand containing tiles to search for a partial group.
     * @return An ArrayList containing a partial group that includes the provided tile, or null if no such group is found.
     */
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

    /**
     * Finds and creates a run of tiles including the provided tile from the given hand.
     * @param tile The tile to include in the partial run.
     * @param hand The hand containing tiles to search for a partial run.
     * @return An ArrayList containing a partial group that includes the provided tile, or null if no such run is found.
     */
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

    /**
     * Attempts to create a set from the provided hand considering various criteria.
     * @param hand The hand of tiles to analyze and create a set from.
     * @return An ArrayList containing tiles forming a set, or null if no set is found according to the specified criteria.
     */
    public static ArrayList<Tile> playTheRack(ArrayList<Tile> hand) {
        ArrayList<Tile> set = new ArrayList<>();
        ArrayList<Tile> handNoDuplicates = new ArrayList<>(new HashSet<>(hand));
        handNoDuplicates = Game.orderSetStairs(handNoDuplicates);

        ArrayList<Tile> consecutiveTiles = BaselineAgentNB.getTilesWithConsecutiveNumbers(handNoDuplicates);
        ArrayList<Tile> sameNumberTiles = BaselineAgentNB.getTilesWithSameNumbers(handNoDuplicates);

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

        handNoDuplicates = Game.orderSetGroup(handNoDuplicates);

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

        if (hand.get(hand.size() - 1).getNumber() == -1) {
            if (!consecutiveTiles.isEmpty()) {
                set.add(consecutiveTiles.get(0));
                set.add(consecutiveTiles.get(1));
                set.add(hand.get(hand.size() - 1));
                return set;
            } else if (!sameNumberTiles.isEmpty()) {
                set.add(sameNumberTiles.get(0));
                set.add(sameNumberTiles.get(1));
                set.add(hand.get(hand.size() - 1));
                return set;
            }
        }

        return null;
    }

}
