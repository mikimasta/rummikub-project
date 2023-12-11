package com.rummikub.game;

import java.util.ArrayList;
import java.util.Random;

public class MonteCarlo {
    
     private static ArrayList<Tile> hand;
     private static ArrayList<ArrayList<Tile>> intialBoard;
    public MonteCarlo(ArrayList<Tile> initialHand, ArrayList<ArrayList<Tile>> intialBoard){
        this.hand = initialHand;
        this.intialBoard = intialBoard;

    }
//     static void mcts(int segmentSize, int iterations) {
//         MonteCarloNode root = new MonteCarloNode(hand, intialBoard);  // Create a node for the initial state
    
//         for (int i = 0; i < iterations; i++) {
//             MonteCarloNode selectedNode = select(root);
//             MonteCarloNode expandedNode = expand(selectedNode, segmentSize);
//             double simulationResult = simulate(expandedNode, segmentSize);
//             backpropagate(expandedNode, simulationResult);
//         }
    
//         // Select the best move based on the MCTS results
//         // Update the 'solutions' variable accordingly
//     }
      /**
     * Monte Carlo Search algorithm to explore possible moves.
     *
     * @return The solutions generated by Monte Carlo Search.
     */

    ArrayList<ArrayList<Tile>> monteCarloSearch() {
        //Random random = new Random();
        int simulations = 5;  // Number of Monte Carlo simulations

        ArrayList<ArrayList<Tile>> bestSolution = null;
        double bestScore = Double.NEGATIVE_INFINITY;
        MonteCarloGameTree tree = new MonteCarloGameTree(hand, intialBoard);
        for (int i = 0; i < simulations; i++) {
            ArrayList<ArrayList<Tile>> currentSolution = tree.getRandomSolution();
            double currentScore = evaluateSolution(currentSolution);

            if (currentScore > bestScore) {
                bestSolution = currentSolution;
                bestScore = currentScore;
            }
        }

        return bestSolution;
    }



   


    private  double evaluateSolution(ArrayList<ArrayList<Tile>> solution) {
        // You need to define a metric to evaluate the quality of a solution.
        // This could be based on factors such as the number of tiles used,
        // the diversity of groups/runs formed, adherence to game rules, etc.
        // Adjust this based on your specific evaluation criteria.
        // For simplicity, I'm returning the total number of tiles used as a score.
        int totalSets = intialBoard.size() - solution.size();

        int totalTilesUsed = getTotalTiles(solution)-getTotalTiles(intialBoard);

        int score = totalSets + totalTilesUsed;
        return totalTilesUsed;
    }

    private static int getTotalTiles(ArrayList<ArrayList<Tile>> list) {
        int totalTiles = 0;

        for (ArrayList<Tile> innerList : list) {
            totalTiles += innerList.size();
        }

        return totalTiles;
    }

}
