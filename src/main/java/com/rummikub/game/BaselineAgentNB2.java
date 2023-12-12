//package com.rummikub.game;
//
//import javafx.scene.paint.Color;
//
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.ArrayList;
//
//public class BaselineAgentNB2 {
//
//    public static Object[] baselineAgentNB2(ArrayList<Tile> hand, ArrayList<String> board) {
//        Object[] result = new Object[3];
//        int count = 0;
//        ArrayList<ArrayList<Tile>> newboard = new ArrayList<>();
//
//        //FIRST ONLY LOOK AT THE RACK
//        int countRackPlays = 1;
//        do{
//            ArrayList<Tile> resultRack = playTheRack(hand);
//            if(resultRack==null){
//                countRackPlays=0;
//            }else {
//                hand.removeAll(resultRack);
//                newboard.add(resultRack);
//                count += resultRack.size();
//            }
//        }while(countRackPlays!=0);
//        /*
//        int countRackPlays = 1;
//        do{
//            ArrayList<Tile> resultRack = BaselineAgent.BaselineAgent(hand.toArray(new Tile[0][0]));;
//            if(resultRack==null){
//                countRackPlays=0;
//            }else {
//                hand.removeAll(resultRack);
//                newboard.add(resultRack);
//                count += resultRack.size();
//            }
//        }while(countRackPlays!=0);
//        */
//
//        //LOOKING AT BOARD AND THE RACK
//        Iterator<String> boardIterator = board.iterator();
//        while (boardIterator.hasNext()) {
//            int countset = 0;
//            String set = boardIterator.next();
//            ArrayList<Tile> setTile = Game.orderSetStairs(ValidSets.getSetForKey(set));
//            ArrayList<Tile> createNewSet = new ArrayList<>();
//            ArrayList<Tile> setNB = ValidSetsNB.getNBForKey(set);
//            //REARRANGEMENT BASED ON PARTIAL SETS ON THE RACK
//            if (setTile.size()>3) {
//                if(Game.checkIfStairs(setTile)){
//                    Tile first = setTile.get(0);
//                    Tile last = setTile.get(setTile.size()-1);
//                    if(addPartialRun(first, hand)!= null){
//                        createNewSet = addPartialRun(first, hand);
//                        newboard.add(createNewSet);
//                        hand.remove(createNewSet.get(0));
//                        hand.remove(createNewSet.get(1));
//                        setTile.remove(first);
//                        newboard.add(setTile);
//                        count += 2;
//                        countset++;
//                    }else if(addPartialRun(last, hand)!=null){
//                        createNewSet = addPartialRun(last, hand);
//                        newboard.add(createNewSet);
//                        hand.remove(createNewSet.get(0));
//                        hand.remove(createNewSet.get(1));
//                        setTile.remove(last);
//                        newboard.add(setTile);
//                        count += 2;
//                        countset++;
//
//                    }else if(addPartialGroup(first, hand) != null){
//                        createNewSet = addPartialGroup(first, hand);
//                        newboard.add(createNewSet);
//                        hand.remove(createNewSet.get(0));
//                        hand.remove(createNewSet.get(1));
//                        setTile.remove(first);
//                        newboard.add(setTile);
//                        count += 2;
//                        countset++;
//
//                    }else if(addPartialGroup(last, hand) != null){
//                        createNewSet = addPartialGroup(last, hand);
//                        newboard.add(createNewSet);
//                        hand.remove(createNewSet.get(0));
//                        hand.remove(createNewSet.get(1));
//                        setTile.remove(last);
//                        newboard.add(setTile);
//                        count += 2;
//                        countset++;
//                    }
//                }else if(Game.checkIfGroup(setTile)){
//                    for(Tile selected: setTile){
//                        //System.out.println(selected.getNumber()+" "+ Game.getStringFromColor(selected));
//                        if(addPartialRun(selected, hand)!= null){
//                            createNewSet = addPartialRun(selected, hand);
//                            newboard.add(createNewSet);
//                            hand.remove(createNewSet.get(0));
//                            hand.remove(createNewSet.get(1));
//                            setTile.remove(selected);
//                            newboard.add(setTile);
//                            count += 2;
//                            countset++;
//                            break;
//
//                        }else if(addPartialGroup(selected, hand) != null) {
//                            createNewSet = addPartialGroup(selected, hand);
//                            newboard.add(createNewSet);
//                            hand.remove(createNewSet.get(0));
//                            hand.remove(createNewSet.get(1));
//                            setTile.remove(selected);
//                            newboard.add(setTile);
//                            count += 2;
//                            countset++;
//                            break;
//                        }
//                    }
//                }
//            }
//            //IF THE SET STARTS WITH JOCKER
//            if (set.startsWith("-1None") &&(setTile.size()>3)) {
//                ArrayList<Tile> setOrder = Game.orderSetStairs(setTile);
//                Tile joker = setOrder.get(setTile.size()-1);
//                if((setOrder.get(setOrder.size()-2).getNumber()== setOrder.get(setOrder.size()-3).getNumber())&&(setOrder.get(setOrder.size()-3).getNumber() == setOrder.get(setOrder.size()-4).getNumber())){
//                    ArrayList<Tile> consecutive = getTilesWithConsecutiveNumbers(hand);
//                    ArrayList<Tile> sameNum = getTilesWithSameNumbers(hand);
//                    if(!consecutive.isEmpty()){
//                        createNewSet.add(consecutive.get(0));
//                        createNewSet.add(consecutive.get(1));
//                        createNewSet.add(joker);
//                        hand.remove(createNewSet.get(0));
//                        hand.remove(createNewSet.get(1));
//                        setTile.remove(joker);
//                        newboard.add(createNewSet);
//                        newboard.add(setTile);
//                        count += 2;
//                        countset++;
//                    }else if(!sameNum.isEmpty()){
//                        createNewSet.add(sameNum.get(0));
//                        createNewSet.add(sameNum.get(1));
//                        createNewSet.add(joker);
//                        hand.remove(createNewSet.get(0));
//                        hand.remove(createNewSet.get(1));
//                        setTile.remove(joker);
//                        newboard.add(createNewSet);
//                        newboard.add(setTile);
//                        count += 2;
//                        countset++;
//                    }else{
//                        newboard.add(setTile);
//                    }
//                }
//            }
//            //SEARCH BASED ON VALID SETS NB
//            if(countset==0) {
//                for (Tile NB : setNB) {
//                    Iterator<Tile> handIterator = hand.iterator();
//                    while (handIterator.hasNext()) {
//                        Tile tileHand = handIterator.next();
//                        if (NB == null) {break;}
//                        if ((NB.getColor() == tileHand.getColor()) && (NB.getNumber() == tileHand.getNumber())) { //there is a Match
//                            ArrayList<Tile> tileAdded = new ArrayList<>();
//                            tileAdded.add(tileHand);
//                            setTile.add(tileHand);
//                            hand.remove(tileHand);
//                            count++;
//                            setTile = Game.orderSetStairs(setTile);
//                            break;
//                        }
//                    }
//                }
//                newboard.add(setTile);
//            }
//        }
//
//        //REARRANGEMENT WITH PARTIAL SETS FROM THE BOARD
//        ArrayList<Tile> potentialTile = new ArrayList<>();
//        Iterator<String> newboardIterator = Game.boardListToSetKey(newboard).iterator();
//        ArrayList<Tile> createNewSet = new ArrayList<>();
//        while (newboardIterator.hasNext()) {
//            String set = newboardIterator.next();
//            ArrayList<Tile> setTile = Game.orderSetStairs(ValidSets.getSetForKey(set));
//            if(setTile.size()==5){
//                potentialTile.add(setTile.get(0));
//                potentialTile.add(setTile.get(setTile.size()-1));
//                setTile.remove(setTile.get(0));
//                setTile.remove(setTile.get(setTile.size()-1));
//                newboard.add(setTile);
//            }else if (setTile.size()==4){
//                int randomNumber = ((int) Math.round(Math.random()))*3;
//                potentialTile.add(setTile.get(randomNumber));
//                setTile.remove(setTile.get(randomNumber));
//                newboard.add(setTile);
//            } else {
//                newboard.add(setTile);
//            }
//        }
//        if(potentialTile.size()>1){
//            ArrayList<ArrayList<Tile>> ps = partialSetBoard(hand,potentialTile,newboard);
//            hand = ps.get(ps.size());
//            ps.remove(hand);
//            newboard = ps;
//        }
//
//        result[0] = newboard;
//        result[1] = hand;
//        result[2] = count;
//        return result;
//    }
//
//    public static ArrayList<ArrayList<Tile >> partialSetBoard(ArrayList<Tile> hand, ArrayList<Tile> ps, ArrayList<ArrayList<Tile>> board){
//        ArrayList<ArrayList<Tile>> newboard = new ArrayList<>();
//        ArrayList<Tile> createNewSet = new ArrayList<>();
//        if(!getTilesWithConsecutiveNumbers(ps).isEmpty()){
//            Iterator<Tile> handIterator = hand.iterator();
//            while (handIterator.hasNext()) {
//                Tile tileHand = handIterator.next();
//                if(addPartialRun(tileHand, ps)!=null){
//                    createNewSet = addPartialRun(tileHand,ps);
//                    board.add(createNewSet);
//                    ps.remove(createNewSet.get(0));
//                    ps.remove(createNewSet.get(1));
//                    hand.remove(tileHand);
//                    createNewSet.clear();
//                }
//            }
//        }
//        //System.out.println(ps);
//        if(!getTilesWithSameNumbers(ps).isEmpty()){
//            Iterator<Tile> handIterator = hand.iterator();
//            while (handIterator.hasNext()) {
//                Tile tileHand = handIterator.next();
//                if(addPartialGroup(tileHand, ps)!=null){
//                    createNewSet = addPartialGroup(tileHand,ps);
//                    newboard.add(createNewSet);
//                    System.out.println(Game.boardListToSetKey(newboard));
//                    hand.remove(createNewSet.get(2));
//                    ps.remove(createNewSet.get(0));
//                    ps.remove(createNewSet.get(1));
//                    createNewSet.clear();
//                }
//            }
//        }
//        System.out.println("ljcjbsd"+Game.boardListToSetKey(newboard));
//        if(!ps.isEmpty()) {
//            Iterator<String> boardIterator = Game.boardListToSetKey(board).iterator();
//            while (boardIterator.hasNext()) {
//                String set = boardIterator.next();
//                ArrayList<Tile> setTile = Game.orderSetStairs(ValidSets.getSetForKey(set));
//                ArrayList<Tile> setNB = ValidSetsNB.getNBForKey(set);
//
//                for (Tile NB : setNB) {
//                    Iterator<Tile> handIterator = hand.iterator();
//                    while (handIterator.hasNext()) {
//                        Tile tileHand = handIterator.next();
//                        if (NB == null) {
//                            break;
//                        }
//                        if ((NB.getColor() == tileHand.getColor()) && (NB.getNumber() == tileHand.getNumber())) { //there is a Match
//                            ArrayList<Tile> tileAdded = new ArrayList<>();
//                            tileAdded.add(tileHand);
//                            setTile.add(tileHand);
//                            hand.remove(tileHand);
//                            setTile = Game.orderSetStairs(setTile);
//                            break;
//                        }
//                    }
//                }
//                board.add(setTile);
//            }
//        }
//        System.out.println(Game.boardListToSetKey(newboard));
//        newboard.addAll(board);
//        newboard.add(hand);
//        return newboard;
//    }
//
//    public static ArrayList<Tile> getTilesWithSameNumbers(ArrayList<Tile> tiles) {
//        tiles = Game.orderSetGroup(tiles);
//        ArrayList<Tile> resultTiles = new ArrayList<>();
//        for (int i = 0; i < tiles.size() - 1; i++) {
//            int currentNumber = tiles.get(i).getNumber();
//            int nextNumber = tiles.get(i + 1).getNumber();
//            if ((currentNumber == nextNumber)&&(tiles.get(i).getColor()!=tiles.get(i+1).getColor())) {
//                resultTiles.add(tiles.get(i));
//                resultTiles.add(tiles.get(i + 1));
//            }
//        }
//        return resultTiles;
//    }
//
//    public static ArrayList<Tile> getTilesWithConsecutiveNumbers(ArrayList<Tile> tiles) {
//        tiles = Game.orderSetStairs(tiles);
//        //System.out.println(tiles);
//        ArrayList<Tile> resultTiles = new ArrayList<>();
//        for (int i = 0; i < tiles.size() - 1; i++) {
//            int currentNumber = tiles.get(i).getNumber();
//            int nextNumber = tiles.get(i + 1).getNumber();
//            if ((currentNumber == nextNumber-1)&&(tiles.get(i).getColor()==tiles.get(i+1).getColor())) {
//                resultTiles.add(tiles.get(i));
//                resultTiles.add(tiles.get(i + 1));
//            }
//        }
//        return resultTiles;
//    }
//
//    public static ArrayList<Tile> addPartialGroup(Tile tile, ArrayList<Tile> hand) {
//        int num = tile.getNumber();
//        ArrayList<Tile> sameNum = getTilesWithSameNumbers(hand);
//        ArrayList<Tile> newSet = new ArrayList<>();
//        if (!sameNum.isEmpty()) {
//            for (int i = 0; i < sameNum.size(); i += 2) {
//                boolean condition1 = (sameNum.get(i).getNumber() == num);
//                boolean condition2 = (((sameNum.get(i).getColor()) != tile.getColor()) && ((sameNum.get(i + 1).getColor()) != tile.getColor())&& (sameNum.get(i).getColor()!=sameNum.get(i+1).getColor()));
//                if (condition1 && condition2) {
//                    newSet.add(sameNum.get(i));
//                    newSet.add(sameNum.get(i + 1));
//                    newSet.add(tile);
//                    return newSet;
//                }
//            }
//        }
//        return null;
//    }
//    public static ArrayList<Tile> addPartialRun(Tile tile, ArrayList<Tile> hand) {
//        ArrayList<Tile> consecutive = getTilesWithConsecutiveNumbers(hand);
//        ArrayList<Tile> newSet = new ArrayList<>();
//        if (!consecutive.isEmpty()) {
//            for (int i = 0; i < consecutive.size(); i += 2) {
//                boolean condition1 = ((consecutive.get(i).getNumber()) == tile.getNumber()+1) && (consecutive.get(i).getColor() == tile.getColor());
//                boolean condition2 = ((consecutive.get(i).getNumber() + 2) == tile.getNumber()) && (consecutive.get(i).getColor() == tile.getColor());
//                if (condition1 || condition2) {
//                    newSet.add(consecutive.get(i));
//                    newSet.add(consecutive.get(i + 1));
//                    newSet.add(tile);
//                    return newSet;
//                }
//            }
//        }
//        return null;
//    }
//    public static ArrayList<Tile> playTheRack(ArrayList<Tile> hand) {
//        ArrayList<Tile> set = new ArrayList<>();
//        ArrayList<Tile> handNoDuplicates = new ArrayList<>(new HashSet<>(hand));
//        handNoDuplicates = Game.orderSetStairs(handNoDuplicates);
//
//        ArrayList<Tile> consecutiveTiles = BaselineAgentNB2.getTilesWithConsecutiveNumbers(handNoDuplicates);
//        ArrayList<Tile> sameNumberTiles = BaselineAgentNB2.getTilesWithSameNumbers(handNoDuplicates);
//
//        for (int i = 0; i < handNoDuplicates.size() - 2; i++) {
//            Tile t1 = handNoDuplicates.get(i);
//            Tile t2 = handNoDuplicates.get(i + 1);
//            Tile t3 = handNoDuplicates.get(i + 2);
//            if ((t1.getNumber() == t3.getNumber() - 2) && t1.getColor().equals(t2.getColor()) && t1.getColor().equals(t3.getColor())) {
//                set.add(t1);
//                set.add(t2);
//                set.add(t3);
//                return set;
//            }
//        }
//
//        handNoDuplicates = Game.orderSetGroup(handNoDuplicates);
//
//        for (int i = 0; i < handNoDuplicates.size() - 2; i++) {
//            Tile t1 = handNoDuplicates.get(i);
//            Tile t2 = handNoDuplicates.get(i + 1);
//            Tile t3 = handNoDuplicates.get(i + 2);
//
//            if ((t1.getNumber() == t3.getNumber()) && (!t1.getColor().equals(t2.getColor())) && (!t1.getColor().equals(t3.getColor())) && !t2.getColor().equals(t3.getColor())) {
//                set.add(t1);
//                set.add(t2);
//                set.add(t3);
//                return set;
//            }
//        }
//
//        if (hand.get(hand.size() - 1).getNumber() == -1) {
//            if (!consecutiveTiles.isEmpty()) {
//                set.add(consecutiveTiles.get(0));
//                set.add(consecutiveTiles.get(1));
//                set.add(hand.get(hand.size() - 1));
//                return set;
//            } else if (!sameNumberTiles.isEmpty()) {
//                set.add(sameNumberTiles.get(0));
//                set.add(sameNumberTiles.get(1));
//                set.add(hand.get(hand.size() - 1));
//                return set;
//            }
//        }
//
//        return null;
//    }
//
//    public static void main(String[] args) {
//
//        Tile joker = new Tile((byte) -1, Color.BROWN);
//
//        Tile rr1 = new Tile((byte) 1, Color.RED);
//        Tile rr2 = new Tile((byte) 2,Color.RED);
//        Tile rr3 = new Tile((byte) 3, Color.RED);
//        Tile rr4 = new Tile((byte) 4,Color.RED);
//        Tile rr5 = new Tile((byte) 5, Color.RED);
//        Tile rr6 = new Tile((byte) 6,Color.RED);
//        Tile rr7 = new Tile((byte) 7, Color.RED);
//        Tile rr8 = new Tile((byte) 8,Color.RED);
//        Tile rr9 = new Tile((byte) 9, Color.RED);
//        Tile rr10 = new Tile((byte) 10,Color.RED);
//        Tile rr11 = new Tile((byte) 11, Color.RED);
//        Tile rr12 = new Tile((byte) 12,Color.RED);
//        Tile rr13 = new Tile((byte) 13, Color.RED);
//
//        Tile oo1 = new Tile((byte) 1, Color.ORANGE);
//        Tile oo2 = new Tile((byte) 2,Color.ORANGE);
//        Tile oo3 = new Tile((byte) 3, Color.ORANGE);
//        Tile oo4 = new Tile((byte) 4,Color.ORANGE);
//        Tile oo5 = new Tile((byte) 5, Color.ORANGE);
//        Tile oo6 = new Tile((byte) 6,Color.ORANGE);
//        Tile oo7 = new Tile((byte) 7, Color.ORANGE);
//        Tile oo8 = new Tile((byte) 8,Color.ORANGE);
//        Tile oo9 = new Tile((byte) 9, Color.ORANGE);
//        Tile oo10 = new Tile((byte) 10,Color.ORANGE);
//        Tile oo11 = new Tile((byte) 11, Color.ORANGE);
//        Tile oo12 = new Tile((byte) 12,Color.ORANGE);
//        Tile oo13 = new Tile((byte) 13, Color.ORANGE);
//
//        Tile bu1 = new Tile((byte) 1, Color.BLUE);
//        Tile bu2 = new Tile((byte) 2,Color.BLUE);
//        Tile bu3 = new Tile((byte) 3, Color.BLUE);
//        Tile bu4 = new Tile((byte) 4,Color.BLUE);
//        Tile bu5 = new Tile((byte) 5, Color.BLUE);
//        Tile bu6 = new Tile((byte) 6,Color.BLUE);
//        Tile bu7 = new Tile((byte) 7, Color.BLUE);
//        Tile bu8 = new Tile((byte) 8,Color.BLUE);
//        Tile bu9 = new Tile((byte) 9, Color.BLUE);
//        Tile bu10 = new Tile((byte) 10,Color.BLUE);
//        Tile bu11 = new Tile((byte) 11, Color.BLUE);
//        Tile bu12 = new Tile((byte) 12,Color.BLUE);
//        Tile bu13 = new Tile((byte) 13, Color.BLUE);
//
//        Tile bl1 = new Tile((byte) 1, Color.BLACK);
//        Tile bl2 = new Tile((byte) 2,Color.BLACK);
//        Tile bl3 = new Tile((byte) 3, Color.BLACK);
//        Tile bl4 = new Tile((byte) 4,Color.BLACK);
//        Tile bl5 = new Tile((byte) 5, Color.BLACK);
//        Tile bl6 = new Tile((byte) 6,Color.BLACK);
//        Tile bl7 = new Tile((byte) 7, Color.BLACK);
//        Tile bl8 = new Tile((byte) 8,Color.BLACK);
//        Tile bl9 = new Tile((byte) 9, Color.BLACK);
//        Tile bl10 = new Tile((byte) 10,Color.BLACK);
//        Tile bl11 = new Tile((byte) 11, Color.BLACK);
//        Tile bl12 = new Tile((byte) 12,Color.BLACK);
//        Tile bl13 = new Tile((byte) 13, Color.BLACK);
//
//        ArrayList<Tile> set1 = new ArrayList<>();
//        set1.add(bl5);
//        set1.add(bl6);
//        set1.add(joker);
//        set1.add(bl8);
//        set1.add(bl9);
//
//        ArrayList<Tile> set2 = new ArrayList<>();
//        set2.add(bu5);
//        set2.add(bu6);
//        set2.add(bu7);
//        set2.add(bu4);
//
//        ArrayList<Tile> set3 = new ArrayList<>();
//        set3.add(rr1);
//        set3.add(rr2);
//        set3.add(rr3);
//        set3.add(joker);
//
//        ArrayList<Tile> set4 = new ArrayList<>();
//        set4.add(oo10);
//        set4.add(oo11);
//        set4.add(oo12);
//        set4.add(oo13);
//
//        ArrayList<Tile> set5 = new ArrayList<>();
//        set5.add(oo4);
//        set5.add(oo3);
//        set5.add(oo5);
//        set5.add(oo6);
//
//        ArrayList<Tile> set6 = new ArrayList<>();
//        set6.add(rr3);
//        set6.add(rr4);
//        set6.add(rr5);
//
//        ArrayList<ArrayList<Tile>> fullBoard = new ArrayList<>();
//
//        fullBoard.add(set5);
//        fullBoard.add(set6);
//
//        ArrayList<Tile> ps = new ArrayList<>();
//        ps.add(oo2);
//        ps.add(rr2);
//        ArrayList<Tile> hand = new ArrayList<>();
//        //hand.add(rr4);
//        hand.add(bl2);
//        //hand.add(rr3);
//        //hand.add(rr5);
//        //hand.add(oo5);
//        //hand.add(bl11);
//        //hand.add(rr11);
//        //hand.add(bu11);
//
//        System.out.println("Inicia loop");
//        //System.out.println(getTilesWithSameNumbers(ps));
//        //System.out.println(addPartialGroup(bl2,ps));
//
//        System.out.println(Game.boardListToSetKey(partialSetBoard(hand,ps,fullBoard)));
//
//
//
//    }
//}
