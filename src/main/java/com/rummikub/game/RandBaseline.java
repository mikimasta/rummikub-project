package com.rummikub.game;

import java.util.ArrayList;

import com.rummikub.Utils;
import com.rummikub.movegen.MoveGenerator;


public class RandBaseline {
    
    public static ArrayList<ArrayList<Tile>> getRandMove(ArrayList<ArrayList<Tile>> stateBoard, ArrayList<Tile> stateHand){
        ArrayList<ArrayList<Tile>> stateHand2 = new ArrayList<>();
        stateHand2.add(stateHand);
        ArrayList<ArrayList<ArrayList<Tile>>> solutions = MoveGenerator.getInstance().getSolutions(Utils.listTo2dArray(stateBoard), Utils.listTo2dArray(stateHand2));
        
        int randomNumber = (int) (Math.random() * (solutions.size()-1));
        return solutions.get(randomNumber);


    }
}
