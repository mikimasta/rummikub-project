package com.rummikub.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import com.rummikub.game.Game;
import com.rummikub.game.Tile;

import javafx.scene.paint.Color;

public class AgentNB {

    public static Object[] possibleMoves(ArrayList<Tile> rack, ArrayList<ArrayList<Tile>> boardArray){

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
            }
        } while (true);
        if(searchNum < 2){
            return new Object[]{boardArray, rack, false};
        }
        return new Object[]{boardArray, rack, true};

    }
}
