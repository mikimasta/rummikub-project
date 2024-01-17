package com.rummikub.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class MCTS {
    
    private static final int SIMULATION_COUNT = 1000;
    private static final int NUMBER_OF_AGENTS = 3;

    ArrayList<Tile> move = new ArrayList<>();
    ArrayList<ArrayList<Node>> moves = new ArrayList<ArrayList<Node>>();
    
    public static ArrayList<Node> findBestMove(Tile[][] board, Tile[][] rack) {
        Node rootNode = new Node(board, rack, -1, 0);

        for (Node node : rootNode.getChildren()) {
            for (int i = 0; i < NUMBER_OF_AGENTS; i++) {
                Node selectedNode = select(rootNode);
                double simulationResult = simulate(selectedNode);
                backpropagate(selectedNode, simulationResult);
    
                // Expand the tree by adding nodes based on different agents if the node is not a leaf
                if (!node.isLeafNode) {
                    expandTree(selectedNode, board, rack, i);
                }

            }
        }

        // Choose the best move based on the tree exploration
        Node maxTotalValueNode = findNodeWithMaxTotalValue(rootNode);
        ArrayList<Node> pathToNode = getPathToNode(maxTotalValueNode);
        return pathToNode;
    }

    private static void expandTree(Node parent, Tile[][] board, Tile[][] rack, int agentIndex) {
        Node newNode = new Node(board, rack, agentIndex, 0);
    
        // Based on the agent index, determine the move using the corresponding agent
        ArrayList<ArrayList<Tile>> moves = getMovesForAgent(agentIndex, rack, board);
    
        if (moves == null || moves.isEmpty() || moves.stream().allMatch(ArrayList::isEmpty)) {
            newNode.isLeafNode = true;
        } else {
            ArrayList<Tile> move = getHighestScoringMove(moves, newNode);
            newNode.setMove(move);
            parent.addChild(newNode); // add the new node as a child to the parent
            int value = getValueOfMove(parent, newNode);
            newNode.setValue(value);
            newNode.setTotalValue(value);
            ArrayList<ArrayList<Tile>> m = new ArrayList<ArrayList<Tile>>(); 
            m.add(move);
            
            Tile [][] newBoard = makeAIMoves(m, board);
            newNode.setBoard(newBoard);
            
            Tile [][] newHand = removeTilesFromRack(moves, rack);
            newNode.setRack(newHand);

            for (int i = 0; i < NUMBER_OF_AGENTS; i++) {
                expandTree(newNode, newNode.getBoard(), newNode.getRack(), i);
            }
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

    private static Node select(Node node) {
        // Implement the selection logic
        // Use a suitable selection strategy, e.g., UCT (Upper Confidence Bound for Trees)
        // You might need to balance exploration and exploitation
        // Return the selected child node
        return getBestChild(node);
    }

    public static Node findNodeWithMaxTotalValue(Node rootNode) {
        if (rootNode == null) {
            return null;
        }

        double maxTotalValue = Double.MIN_VALUE;
        Node maxTotalValueNode = null;

        // Perform depth-first traversal
        Stack<Node> stack = new Stack<>();
        stack.push(rootNode);

        while (!stack.isEmpty()) {
            Node node = stack.pop();

            // Check if the current node has a higher total value
            if (node.getValue() > maxTotalValue) {
                maxTotalValue = node.getValue();
                maxTotalValueNode = node;
            }

            // Push children onto the stack for further exploration
            for (Node child : node.getChildren()) {
                stack.push(child);
            }
        }

        System.out.println(maxTotalValue);
        return maxTotalValueNode;
    }
    
    public static ArrayList<Node> getPathToNode(Node node) {
        ArrayList<Node> path = new ArrayList<>();
        
        while (node != null) {
            path.add(node);
            node = node.getParent();
        }

        Collections.reverse(path);
        return path;
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

    public static ArrayList<Tile> getHighestScoringMove(ArrayList<ArrayList<Tile>> moves, Node node) {
        int biggestValue = 0;
        ArrayList<Tile> biggestMove = new ArrayList<>();

        for (ArrayList<Tile> move : moves) {
            int valueOfMove = getValueOfMove(node.getParent(), node);
            if (valueOfMove >  biggestValue) {
                biggestValue = valueOfMove;
                node.setMove(move);
                node.setValue(biggestValue);
                biggestMove = move;
            }
        }
        return biggestMove;
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

    /**
     * updates the gameboard in memory by finding spaces on the board to place the AI moves
     * @param aiMoves list of moves of tiles
     * @param board gameboard
     * @return updated gameboard
     */
    public static Tile[][] makeAIMoves(ArrayList<ArrayList<Tile>> aiMoves, Tile[][] board) {
        if (aiMoves == null) {
            return board;
        }

        for (ArrayList<Tile> aiMove : aiMoves) {
            int space = 0;
            boolean done = false;
            int leaveSpace = 0;

            for (int i = 0; i < board.length; i++) {
                for (int y = 0; y < board[i].length; y++) {
                    if (board[i][y] == null) {
                        if (leaveSpace < 1) leaveSpace++;
                        else space++;
                    } else {
                        leaveSpace = 0;
                        space = 0;
                    }

                    if (!done && space > aiMove.size()) { // enough space for the move
                        for (int z = y - space + 1, aiIndex = 0; aiIndex < aiMove.size(); z++, aiIndex++) {
                            board[i][z] = aiMove.get(aiIndex);
                        }
                        done = true;
                        break;
                    }
                }

                if (done) break;
                space = 0; leaveSpace = 0;
            }
        }

        return board;
    }

    /**
     * removes tiles used in the AI move from its rack 
     * @param aiMoves list of moves of tiles
     * @param board gameboard
     * @return updated gameboard
     */
    public static Tile[][] removeTilesFromRack(ArrayList<ArrayList<Tile>> aiMoves, Tile[][] rack) {
        if (aiMoves == null) {
            return rack;
        }

        HashSet<Tile> tilesToRemove = new HashSet<>();
        for (ArrayList<Tile> aiMove : aiMoves) {
            tilesToRemove.addAll(aiMove);
        }

        for (int i = 0; i < rack.length; i++) {
            for (int j = 0; j < rack[i].length; j++) {
                if (rack[i][j] != null && tilesToRemove.contains(rack[i][j])) {
                    rack[i][j] = null;
                }
            }
        }

        return rack;
    }

    /**
     * removes tiles from the gameboard in memory.
     * @param aiMoves list of moves of tiles
     * @param board gameboard
     * @return updated gameboard
     */
    public static Tile[][] removeTilesFromBoard(ArrayList<ArrayList<Tile>> aiMoves, Tile[][] board) {
        if (aiMoves == null) {
            return board;
        }

        HashSet<Tile> tilesToRemove = new HashSet<>();
        for (ArrayList<Tile> aiMove : aiMoves) {
            tilesToRemove.addAll(aiMove);
        }
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (tilesToRemove.contains(board[i][j])) {
                    board[i][j] = null;
                }
            }
        }

        return board;
    }
    
}