package com.rummikub.game;

import java.util.ArrayList;

public class MCTS {
    
    private static final int SIMULATION_COUNT = 1000;
    private static final int NUMBER_OF_AGENTS = 3;

    public static ArrayList<ArrayList<Tile>> findBestMove(Tile[][] board, Tile[][] rack) {
        Node rootNode = new Node(board, rack, 0);
        
        for (Node node : rootNode.getChildren()) {
            for (int i = 0; i < NUMBER_OF_AGENTS; i++) {
                Node selectedNode = select(rootNode);
                double simulationResult = simulate(selectedNode);
                backpropagate(selectedNode, simulationResult);
    
                // Expand the tree by adding nodes based on different agents
                expandTree(selectedNode, board, rack, i);
            }
        }

        // Choose the best move based on the tree exploration
        Node bestChild = getBestChild(rootNode);
        return bestChild.getMoves();
    }

    private static void expandTree(Node parent, Tile[][] board, Tile[][] rack, int agentIndex) {
        Node newNode = new Node(board, rack, agentIndex);

        // Based on the agent index, determine the move using the corresponding agent
        ArrayList<ArrayList<Tile>> moves = getMovesForAgent(agentIndex, rack, board);
        if (moves != null || !moves.isEmpty()) {
            // Add moves to the new node
            newNode.setMoves(moves);

            // Add the new node as a child to the parent
            parent.addChild(newNode);
        } 
        
    }

    private static ArrayList<ArrayList<Tile>> getMovesForAgent(int agentIndex, Tile[][] rack, Tile[][] board) {
        switch (agentIndex) {
            case 0:
                return BaselineAgent.baselineAgent(rack);
            case 1:
                return SingleTileAgent.singleTilemove(rack, board);
            case 2:
                return SplittingAgent.splittingMoves(rack, board);
            default:
                throw new IllegalArgumentException("Invalid agent index");
        }
    }

    private static Node select(Node node) {
        // Implement the selection logic
        // Use a suitable selection strategy, e.g., UCT (Upper Confidence Bound for Trees)
        // You might need to balance exploration and exploitation
        // Return the selected child node
        return getBestChild(node);
    }

    private static double simulate(Node node) {
        // Implement the simulation logic
        // Simulate a random play-out from the given node
        // Return a value indicating the result of the simulation (e.g., the sum of tile numbers)
        return 0.0;
    }

    private static void backpropagate(Node node, double result) {
        // Implement the backpropagation logic
        // Update statistics of the nodes along the path from the selected node to the root
    }

    private static Node getBestChild(Node node) {
        int bestValue = Integer.MIN_VALUE;
        Node bestValueNode = null;

        for (Node child : node.getChildren()) {
            int valueOfNode = getValueOfMove(node, child);
            if (valueOfNode > bestValue) {
                bestValue = valueOfNode;
                bestValueNode = child;
            }
        }

        return bestValueNode;
    }

    public static int getValueOfMove (Node previousNode, Node actualNode) {
        return getValueOfBoard(actualNode.getBoard()) - getValueOfBoard(previousNode.getBoard());
    }

    public static int getValueOfBoard(Tile[][] board) {
        int total = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Tile t = board[i][j];

                if (t != null && !t.isLocked()) {
                    if (t.getNumber() == -1) {
                        if (board[i][j + 1] != null) {
                            if (board[i][j - 1] != null) {
                                if (board[i][j + 1].getNumber() == board[i][j - 1].getNumber()) {
                                    total += board[i][j + 1].getNumber();
                                } else {
                                    total += board[i][j - 1].getNumber() + 1;
                                }
                            } else {
                                if (board[i][j + 1].getNumber() == board[i][j + 2].getNumber()) {
                                    total += board[i][j + 1].getNumber();
                                } else {
                                    total += board[i][j + 1].getNumber() - 1;
                                }
                            }
                        } else {
                            if (board[i][j - 1].getNumber() == board[i][j - 2].getNumber()) {
                                total += board[i][j - 1].getNumber();
                            } else {
                                total += board[i][j - 1].getNumber() + 1;
                            }
                        }
                    } else {
                        total += t.getNumber();
                    }
                } 
            }
        }
        return total;
    }
    
}