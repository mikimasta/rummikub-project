package com.rummikub.game.MCTS;

import java.util.ArrayList;
import java.util.List;

import com.rummikub.Utils;
import com.rummikub.game.Tile;
import com.rummikub.movegen.MoveGenerator;
import javafx.scene.paint.Color;


public class MCTS {
    private static int NUM_SIMULATIONS = 100;
    private static Node root;

    public static ArrayList<ArrayList<Tile>> mcts(ArrayList<ArrayList<Tile>> stateBoard, ArrayList<Tile> stateHand, ArrayList<Tile> opponentHand, ArrayList<Tile> pile){
        root = new Node(stateBoard, stateHand);
        root.updateScore(0);
        for(int i = 1; i <= NUM_SIMULATIONS; i++){
            System.out.println("Simulation number : " + i);
            Node node = select(root);
            List<Node>  children = expand(node).getChildren();
            for(Node child : children){
                double reward = simulateNormal(child.getStateBoard(), child.getStateHand(), opponentHand, pile);
                child.updateScore(reward);
                backpropagate(child, reward);

            }
        }
        return selectBestMove(root).getStateBoard();
        //return selectBestMove(root).getStateBoard;
    }

    private static Node select(Node node) {
       
    if (!node.getChildren().isEmpty()){
            return bestUCT(node);
    }
        return node;

    }

    private static Node bestUCT(Node node) {
        double maxUCT = Double.MIN_VALUE;
        Node bestNode = null;

        for (Node child : node.getChildren()) {

            double uct = UCT(child);

            if (uct > maxUCT) {
                maxUCT = uct;
                bestNode = child;
            }
        }
        return bestNode;
    }
    
    private static double UCT(Node child) {

        double explotation= (double) child.getReward() / child.getVisits();
        double constant = 1.0;
        double exploration = constant *   Math.sqrt(Math.log(child.getParent().getVisits()) / child.getVisits());      
        double uct = explotation + exploration;
        
        return uct;
    }
    
    private static Node expand(Node node){
        // Get the current game state (board and hand)
        ArrayList<ArrayList<Tile>> stateBoard = node.getStateBoard();
        ArrayList<Tile> stateHand = node.getStateHand();

        ArrayList<ArrayList<Tile>> stateHandForMoves = new ArrayList<>();
        stateHandForMoves.add(stateHand);

        // Generate all possible child nodes
        ArrayList<ArrayList<ArrayList<Tile>>> validMoves = MoveGenerator.getInstance().getSolutions(Utils.listTo2dArray(stateBoard), Utils.listTo2dArray(stateHandForMoves));
        for(ArrayList<ArrayList<Tile>> moveBoard : validMoves){
            ArrayList<Tile> moveHand = getNewHand(moveBoard, stateBoard, stateHand);
            Node child = new Node(moveBoard, moveHand);
            child.setParent(node);
            node.addChild(child);
        }
        
        return node;
    }


    private static ArrayList<Tile> getNewHand(ArrayList<ArrayList<Tile>> solutionBoard, ArrayList<ArrayList<Tile>> intialBoard, ArrayList<Tile> hand){
        ArrayList<Tile> usedTiles = new ArrayList<>();

        //ArrayList<Tile> deepCopy = deepCopyHand(hand);
   
        // Iterate through list1
        for (ArrayList<Tile> sublist1 : solutionBoard) {
            for (Tile tile : sublist1) {
                // Check if the tile is not present in list2
                if (!isTilePresent(tile, intialBoard)) {
                    usedTiles.add(tile);
                }
            }
        }        
        ArrayList<Tile> finalHand = removeUsedTile(hand, usedTiles);
        return finalHand;
    }

    private static ArrayList<Tile> removeUsedTile(ArrayList<Tile> hand, ArrayList<Tile> used){
        ArrayList<Tile> result = new ArrayList<>();
        for(int i = 0; i < hand.size(); i++){
                Tile currentFromHand = hand.get(i);
                boolean tileUsed = false;
            for(int j = 0; j < used.size(); j++){
                Tile currentFromused = used.get(j);
                if((currentFromHand.getColorString().equals(currentFromused.getColorString()))){
                    if(currentFromHand.getNumber() == currentFromused.getNumber()){
                        tileUsed = true;
                    }   
                }
            }
            if(!tileUsed){
                result.add(currentFromHand);
            }
        }
        return result;
    }

    private static boolean isTilePresent(Tile tile, ArrayList<ArrayList<Tile>> list) {
        // Check if the tile is present in any sublist of the list
        for (ArrayList<Tile> sublist : list) {
            if (sublist.contains(tile)) {
                return true;
            }
        }
        return false;
    }

    private static double simulateNormal(ArrayList<ArrayList<Tile>> stateBoard, ArrayList<Tile> stateHand, ArrayList<Tile> opponentHand, ArrayList<Tile> pile){
        int player = 0;
        ArrayList<ArrayList<Tile>> prevBoard = new ArrayList<ArrayList<Tile>>();

        ArrayList<ArrayList<Tile>> board = new ArrayList<ArrayList<Tile>>();
    
        board = stateBoard;

        int noMoveA = 0;
        int noMoveB = 0;
        // System.out.println("move being simulated:");
        // System.out.println(stateBoard);
        // System.out.println("hand being simulated:");
        // System.out.println(stateHand);

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        while(elapsedTime < 1){
        if(!((stateHand.isEmpty() && opponentHand.isEmpty()) && !pile.isEmpty())){
            if(player == 0){
                ArrayList<ArrayList<Tile>> move = Move.getMove(stateHand, stateBoard);
                if(move != null){
                prevBoard = board;
                board = move;
                stateHand = getNewHand(board, prevBoard, stateHand);
                noMoveA = 0;

                }
                else{
                    if(pile.isEmpty()){
                    stateHand.add(pile.remove(0));}
                    noMoveA++;
                }
                player = 1;
            }
            else if(player == 1){
                ArrayList<ArrayList<Tile>> move = Move.getMove(opponentHand, stateBoard);
                if(move != null){
                    prevBoard = board;
                    board = move;
                    opponentHand = getNewHand(board, prevBoard, opponentHand);
                    noMoveB = 0;
                }
                else{
                    if(pile.isEmpty()){
                    opponentHand.add(pile.remove(0));}
                    noMoveB++;
                }
                player = 0;
            }
        }
        long endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
    }
        //System.out.println("Elapsed Time: " + elapsedTime + " milliseconds");

       double handtally = tallyHand(stateHand);
       return (100 - handtally)/100; 

    }

    // private static double simulateNormal(ArrayList<ArrayList<Tile>> stateBoard, ArrayList<Tile> stateHand, ArrayList<Tile> opponentHand, ArrayList<Tile> pile){
    //     int player = 0;
    //     ArrayList<ArrayList<Tile>> initalBoard = new ArrayList<ArrayList<Tile>>();
    //     initalBoard = stateBoard;
    //     while((stateHand != null && opponentHand != null) && !pile.isEmpty() ){
    //         if(player == 0){
    //             ArrayList<ArrayList<Tile>> move = Move.getMove(stateHand, stateBoard);
    //             if(move != null){
    //             stateBoard = move;
    //             stateHand = getNewHand(initalBoard, stateBoard, stateHand);
    //             }
    //             else{
    //                 stateHand.add(pile.remove(0));
    //             }
    //             player = 1;
    //         }
    //         else if(player == 1){
    //             ArrayList<ArrayList<Tile>> move = Move.getMove(opponentHand, stateBoard);
    //             if(move != null){
    //             stateBoard = move;
    //             opponentHand = getNewHand(initalBoard, stateBoard, opponentHand);
    //             }
    //             else{
    //                 opponentHand.add(pile.remove(0));
    //             }
    //             player = 0;
    //         }
    //     }

    //      double handtally = tallyHand(stateHand);
    //     return (100 - handtally)/100; 

    // }

    private static int tallyHand(ArrayList<Tile> stateHand){
        int count = 0;
        for(Tile tile : stateHand){
            count = count + tile.getNumber();
        }
        return count;
    }

    private static void lockBoard(ArrayList<ArrayList<Tile>> board){
        for(ArrayList<Tile> set : board){
            for(Tile tile: set){
                tile.lock();
            }
        }
    }

    private double simulateNN(ArrayList<ArrayList<Tile>> stateBoard, ArrayList<Tile> stateHand, ArrayList<Tile> opponentHand, ArrayList<Tile> pile){

        return 0;
    }


    private static void backpropagate(Node node, double reward){
        while (node.getParent() != null) {
            
            node.updateScore(reward);
            node = node.getParent();
        }
    }

    private static Node selectBestMove(Node root){
        List<Node> children = root.getChildren();
        double bestReward = 0;
        Node bestNode = null;
        for(Node child : children){
            if(child.getReward() > bestReward){
                bestReward = child.getReward();
                bestNode = child;
            }
        }
        return bestNode;
    }

    public static void main(String[] args) {

        Tile t1 = new Tile((byte) 1, Color.RED);
        Tile t2 = new Tile((byte) 2, Color.RED);
        Tile t3 = new Tile((byte) 3, Color.RED);
        Tile t4 = new Tile((byte) 4, Color.RED);
        Tile t5 = new Tile((byte) 1, Color.ORANGE);
        Tile t6 = new Tile((byte) 2, Color.ORANGE);
        Tile t7 = new Tile((byte) 3, Color.ORANGE);
        Tile t8 = new Tile((byte) 4, Color.ORANGE);
        Tile t9 = new Tile((byte) 1, Color.BLACK);
        Tile t10 = new Tile((byte) 2, Color.BLACK);
        Tile t11 = new Tile((byte) 3, Color.BLACK);
        Tile t12 = new Tile((byte) 4, Color.BLACK);

        t1.lock();
        t2.lock();
        t3.lock();
        t4.lock();
        t5.lock();
        t6.lock();
        t7.lock();
        t8.lock();
        t9.lock();
        t10.lock();
        t11.lock();
        t12.lock();


        Tile t13 = new Tile((byte) 1, Color.BLUE);
        Tile t14 = new Tile((byte) 3, Color.BLACK);
        Tile t15 = new Tile((byte) 5, Color.BLACK);

        Tile[][] testBoard = {{t1, t2, t3, t4}, {t5, t6, t7, t8}, {t9, t10, t11, t12}};
        Tile[][] testHand = {{t13, t14, t15}};

        Tile o1 = new Tile((byte) 2, Color.RED);
        Tile[][] opHand = {{o1}};

        ArrayList<Tile> pile = new ArrayList<>();

        Tile p1 = new Tile((byte) 11, Color.BLACK);
        Tile p2 = new Tile((byte) 12, Color.BLACK);
        Tile p3 = new Tile((byte) 13, Color.BLACK);

        pile.add(p1);
        pile.add(p2);
        pile.add(p3);

        System.out.println(MCTS.mcts(Utils.convertToArrayList(testBoard), Utils.convertToArrayList(testHand).get(0), Utils.convertToArrayList(opHand).get(0), pile));
    }

}
