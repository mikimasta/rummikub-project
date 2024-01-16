package com.rummikub.MCTS.Baseline;

import com.rummikub.game.Game;
import com.rummikub.game.Tile;
import com.rummikub.gui.RackGUI;

import java.util.ArrayList;


public class Move {

    public ArrayList<ArrayList<Tile>> getMove(ArrayList<Tile> initalHand, ArrayList<ArrayList<Tile>> initalBoard){
        
        Tile[][] rack = new Tile[2][15];
         rack = BaselineAgent.arrayListToRack(initalHand,rack);
        ArrayList<ArrayList<Tile>> aimove = BaselineAgent.baselineAgent(rack);
        if (aimove != null && !aimove.isEmpty() && aimove.size() > 0) {
            System.out.println("move with baseline agent");
            System.out.println(BaselineAgent.printMoves(aimove));
            makeAIMoves(aimove, GameboardGUI.getInstance().getState());
            GameboardGUI.getInstance().renderAIMove();
            Tile[][] newHand = removeTilesFromRack(aimove, Game.getInstance().currentPlayer.getHand());
            RackGUI.getInstance().handToRack(newHand);
            finishAIMove();
            System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));
        } 
        aimove = SingleTileAgent.singleTilemove(Game.getInstance().currentPlayer.getHand(), GameboardGUI.getInstance().getState());
        if (aimove != null && !aimove.isEmpty()) { // && aimove.size() > 0
            System.out.println("Move with single tiles");
            System.out.println(BaselineAgent.printMoves(aimove));
            processAIMove(aimove);
        }
        aimove = SplittingAgent.splittingMoves(Game.getInstance().currentPlayer.getHand(), GameboardGUI.getInstance().getState());
        if (aimove != null && !aimove.isEmpty()) { // && aimove.size() > 0
            System.out.println("Move with splitting method");
            System.out.println(BaselineAgent.printMoves(aimove));
            processAIMove(aimove);
        }
    }
    
}

