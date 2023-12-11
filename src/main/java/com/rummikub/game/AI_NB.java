package com.rummikub.game;

import java.util.ArrayList;

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
            System.out.println("new board");
            System.out.println(Game.boardListToSetKey(newBoard));

        } while (count != 0);

        if(moves.size() == 0){ //no tiles from the hand are valid
            System.out.println("no tiles from the hand can be added to the board");
            //newRack.add(Player.draw); //draw a tile from the pool
        }
        System.out.println("Final board");
        System.out.println(Game.boardListToSetKey(newBoard));

    }


}