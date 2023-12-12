package com.rummikub.game;

import java.util.ArrayList;

public class Agent_NB {

    /**
     * Determines the possible valid moves. Generates valid sets of tiles conforming to Rummikub rules.
     *
     * This method initiates the decision-making process by invoking the `baselineAgentNB` method and
     * continues to explore further potential moves based on the updated board and rack configurations
     * until no further valid moves can be made.
     *
     * @param rack representing the current set of tiles available for placement.
     * @param boardArray current board configuration where each inner ArrayList representing a set of tiles.
     * @return An ArrayList of ArrayLists representing the resulting board configuration after executing
     *         the valid moves.
     *         Returns null if no changes can be made to the board.
     */
    public static ArrayList<ArrayList<Tile>> possibleMoves(ArrayList<Tile> rack, ArrayList<ArrayList<Tile>> boardArray){

        ArrayList<String> board = Game.boardListToSetKey(boardArray);
        Object[] baselineResult = BaselineAgentNB.baselineAgentNB(rack, board);
        ArrayList<ArrayList<Tile>> newBoard = (ArrayList<ArrayList<Tile>>) baselineResult[0];
        ArrayList<Tile> newRack = (ArrayList<Tile>) baselineResult[1];
        int count = (int) baselineResult[2];
        int searchNum = 0;

        do {
            baselineResult = BaselineAgentNB.baselineAgentNB(newRack, Game.boardListToSetKey(newBoard));
            newBoard = (ArrayList<ArrayList<Tile>>) baselineResult[0];
            newRack = (ArrayList<Tile>) baselineResult[1];
            count = (int) baselineResult[2];
            searchNum++;
        } while (count != 0);

        if(searchNum == 1&&(board.size()==Game.boardListToSetKey(newBoard).size())){ //no tiles from the hand are valid
            return null;
        }

        ArrayList<String> finalBoardString = (Game.boardListToSetKey(newBoard));
        ArrayList<ArrayList<Tile >> finalBoard = new ArrayList<>();
        for(String finalSet: finalBoardString){
            finalBoard.add(ValidSets.getSetForKey(finalSet));
        }
        return finalBoard;

    }

}