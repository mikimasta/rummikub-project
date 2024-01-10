package com.rummikub.game;

import java.util.ArrayList;

public class SplittingAgent {
    

    // step 1 : check moves bigger than 5, maybe 4 even
    // step 2 :  for each move bigger than 5 split the move depending on the size -> will return list of moves
    // step 3 :  loop over these moves -> if checkIfStairs(move) then good count++
    // and if not good call the runMoves over it then checkIfStairs -> if true -> count++
    // if count== these move.size(), break return that split
    public static ArrayList<ArrayList<Tile>> splittingMovesGroup(Tile[][] dupRack, Tile[][] board){
        ArrayList<ArrayList<Tile>> runMoves = SingleTileAgent.listOfMovesRuns(board); // returns list of moves which are runs
        ArrayList<ArrayList<Tile>> splittedMoves = new ArrayList<ArrayList<Tile>>();
        ArrayList<ArrayList<Tile>> expendMove = new ArrayList<ArrayList<Tile>>();
        ArrayList<ArrayList<Tile>> finalMove =  new ArrayList<ArrayList<Tile>>();

        ArrayList<Tile> rack = new ArrayList<>();

        for (ArrayList<Tile> move : runMoves) {
            if (move.size() < 5) { // maybe 4 even
                runMoves.remove(move);
            }
        }

        boolean isPossible = true;
        int count = 0;
        for (ArrayList<Tile> move : runMoves) {
            splittedMoves = splitMoves(move);
            for (ArrayList<Tile> splittedMove : splittedMoves) {
                if (Game.checkIfStairs(splittedMove)) { // its a good move
                    count++;
                } else { //  the move is not good
                    expendMove.add(splittedMove);
                    expendMove = SingleTileAgent.runMoves(dupRack, expendMove); // expend move with tile(s) from rack
                    if (Game.checkIfStairs(expendMove.get(0))) { // expended move is correct, remove tile from rack used to expend move
                        rack.removeAll(expendMove.get(0));
                        dupRack = BaselineAgent.arrayListToRack(rack, dupRack);
                        count++;
                        expendMove = null;
                    } else {
                        isPossible = false;
                        break;
                    }
                }
                if (!isPossible) {
                    System.out.println("the split is not possible try another split");
                    break;
                }
                if (count == splittedMoves.size()) { // that split works
                    System.out.println("the split is valid");
                    finalMove.add(splittedMove);
                }
            }
        }

        return splittedMoves;
    }

    public static ArrayList<ArrayList<Tile>> splitMoves(ArrayList<Tile> runMove) {
        ArrayList<ArrayList<Tile>> splittedMove = new ArrayList<ArrayList<Tile>>();

        return splittedMove;

    }
    
}
