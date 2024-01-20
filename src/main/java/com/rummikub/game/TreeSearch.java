package com.rummikub.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Stack;
import javafx.scene.paint.Color;

public class TreeSearch {
    
    private static final int NUMBER_OF_AGENTS = 3;

    ArrayList<Tile> move = new ArrayList<>();
    
    public static ArrayList<Node> findBestMove(Tile[][] board, Tile[][] rack) {
        Node rootNode = new Node(copyBoard(board), copyRack(rack), 100, 0);
        
        if (!rootNode.isLeafNode) {
            for (int i = 0; i < NUMBER_OF_AGENTS; i++) {
                expandTree(rootNode, copyBoard(board), copyRack(rack), i); 
            }
        }
        
        // Node maxTotalValueNode = findNodeWithMaxTotalValue(rootNode);
        // ArrayList<Node> pathToNode = getPathToNode(maxTotalValueNode);
        Node maxTilesUsed = findNodeWithMinRackTiles(rootNode);
        ArrayList<Node> pathToNode = getPathToNode(maxTilesUsed);
        return pathToNode;
    }
 

    private static void expandTree(Node parent, Tile[][] board, Tile[][] rack, int agentIndex) {
        if (!isRackEmpty(rack)) {
            Node newNode = new Node(board, rack, agentIndex, 0);
            parent.addChild(newNode);
    
            ArrayList<ArrayList<Tile>> moves = getMovesForAgent(agentIndex, rack, board);
            ArrayList<Tile> move = new ArrayList<>();
    
            if (moves == null || moves.isEmpty() || moves.size() < 1) {
                newNode.isLeafNode = true;
            } else {
                if (agentIndex == 2) { // splitting agent
                    move = BaselineAgent.nestedArrayListToArrayList(moves);
                } else {
                    // move = getHighestScoringMove(moves, newNode);
                    move = getBiggestMove(moves, newNode);
                }
    
                System.out.println("the move is : " + BaselineAgent.printMove(move));
                newNode.setMove(move);
                
                if (agentIndex == 0) {
                    logicForAgent1(newNode, move, parent.getBoard(), parent.getRack());
                } else if (agentIndex == 1 || agentIndex == 2) {
                    logicForAgent2_3(newNode, move, parent.getBoard(), parent.getRack());
                }
    
                int value = getValueOfMove(parent.getBoard(), newNode.getBoard());
                newNode.setValue(value);
                newNode.setTotalValue(value);
                newNode.setAgentUsed(agentIndex);
    
                for (int i = 0; i < NUMBER_OF_AGENTS; i++) {
                    expandTree(newNode, copyBoard(newNode.getBoard()), copyRack(newNode.getRack()), i);
                }
            }
        }
    }

    public static void logicForAgent1(Node node, ArrayList<Tile> move, Tile[][] board, Tile[][] rack) {
        Tile[][] newBoard = makeAIMove(move, copyBoard(board));
        node.setBoard(newBoard);
        Tile[][] newHand = removeTilesFromRack(move, copyRack(rack));
        node.setRack(newHand);
        node.setTilesInRack(Node.tilesInRack(newHand));
    }
    
    public static void logicForAgent2_3(Node node, ArrayList<Tile> move, Tile[][] board, Tile[][] rack) {
        Tile[][] newBoard = removeTilesFromBoard(move, copyBoard(board));
        newBoard = makeAIMove(move, newBoard);
        node.setBoard(newBoard);
        Tile[][] newHand = removeTilesFromRack(move, copyRack(rack));
        node.setRack(newHand);
        node.setTilesInRack(Node.tilesInRack(newHand));
    }
    
    private static Tile[][] copyBoard(Tile[][] board) {
        Tile[][] copy = new Tile[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, board[0].length);
        }
        return copy;
    }
    
    private static Tile[][] copyRack(Tile[][] rack) {
        Tile[][] copy = new Tile[rack.length][rack[0].length];
        for (int i = 0; i < rack.length; i++) {
            System.arraycopy(rack[i], 0, copy[i], 0, rack[0].length);
        }
        return copy;
    }
    
    private static ArrayList<ArrayList<Tile>> getMovesForAgent(int agentIndex, Tile[][] rack, Tile[][] board) {
        switch (agentIndex) {
            case 0:
                return BaselineAgent.baselineAgent(copyRack(rack));
            case 1:
                return SingleTileAgent.singleTilemove(copyRack(rack), copyBoard(board));
            case 2:
                return SplittingAgent.splittingMoves(copyRack(rack), copyBoard(board));
            default:
                throw new IllegalArgumentException("Invalid agent index");
        }
    }

    /**
     * checks if a rack is empty
     * @param rack rack
     * @return true if the rack is empty 
     */
    static boolean isRackEmpty(Tile [][] rack) {
        int size = rack.length * rack[0].length;
        int count = 0;
        for (int i = 0; i < rack.length; i++) {
            for (int j = 0; j < rack[i].length; j++) {
                if (rack[i][j] == null) {
                    count++;
                } else {
                    return false;
                }
            }
        }
        return count == size;
    }

    private static Node findNodeWithMinRackTiles(Node rootNode) {
        if (rootNode == null) {
            return null;
        }

        int minNumberOfTiles = Integer.MAX_VALUE;
        Node nodeWithMinTiles = null;

        Stack<Node> stack = new Stack<>();
        stack.push(rootNode);

        while (!stack.isEmpty()) {
            Node node = stack.pop();

            if (node.getTilesInRack() < minNumberOfTiles) {
                minNumberOfTiles = node.getTilesInRack();
                nodeWithMinTiles = node;
            }

            for (Node child : node.getChildren()) { // push children onto the stack 
                stack.push(child);
            }
        }

        System.out.println("Node with smallest amount of Tiles in rack : " + nodeWithMinTiles.getTilesInRack());
        return nodeWithMinTiles;
    }

    /**
     * performs a depth first search algorithm to find the node with the biggest total value
     * @param rootNode root node of the tree
     * @return node with the biggest total value
     */
    public static Node findNodeWithMaxTotalValue(Node rootNode) {
        if (rootNode == null) {
            return null;
        }

        int maxTotalValue = Integer.MIN_VALUE;
        Node maxTotalValueNode = null;

        Stack<Node> stack = new Stack<>();
        stack.push(rootNode);

        while (!stack.isEmpty()) {
            Node node = stack.pop();

            if (node.getTotalValue() > maxTotalValue) {
                maxTotalValue = node.getValue();
                maxTotalValueNode = node;
            }

            for (Node child : node.getChildren()) { // push children onto the stack 
                stack.push(child);
            }
        }

        System.out.println("Max value is " + maxTotalValueNode.getTotalValue());
        return maxTotalValueNode;
    }
    
    /**
     * gets the path to the node with the highest total value
     * @param node node with highest total value
     * @return the path to the node with the highest total value
     */
    public static ArrayList<Node> getPathToNode(Node node) {
        ArrayList<Node> path = new ArrayList<>();
        
        while (node != null) {
            path.add(node);
            node = node.getParent();
        }

        Collections.reverse(path);
        return path;
    }

    public static ArrayList<Tile> getBiggestMove(ArrayList<ArrayList<Tile>> moves, Node node) {
        int biggestSizeMove = Integer.MIN_VALUE;
        ArrayList<Tile> biggestMove = new ArrayList<>();

        for (ArrayList<Tile> move : moves) {
            if (move.size() > biggestSizeMove) {
                biggestSizeMove = move.size();
                biggestMove = move;
            }
        }

        return biggestMove;
    }

    public static ArrayList<Tile> getHighestScoringMove(ArrayList<ArrayList<Tile>> moves, Node node) {
        int biggestValue = Integer.MIN_VALUE;
        ArrayList<Tile> biggestMove = new ArrayList<>();

        for (ArrayList<Tile> move : moves) {
            int valueOfMove = getValueOfMove(node.getParent().getBoard(), node.getBoard());
            if (valueOfMove >  biggestValue) {
                biggestValue = valueOfMove;
                biggestMove = move;
            }
        }

        return biggestMove;
    }

    public static int getValueOfMove(Tile[][] parentBoard, Tile[][] newNodeBoard) {
        return getValueOfBoard(newNodeBoard) - getValueOfBoard(parentBoard);
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
     * @param aiMove move of tiles
     * @param board gameboard
     * @return updated gameboard
     */
    public static Tile[][] makeAIMove(ArrayList<Tile> aiMove, Tile[][] board) {
        if (aiMove == null) {
            return board;
        }

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
        

        return board;
    }

    /**
     * removes tiles used in the AI move from its rack 
     * @param aiMove move of tiles
     * @param rack players hand
     * @return updated gameboard
     */
    public static Tile[][] removeTilesFromRack(ArrayList<Tile> aiMove, Tile[][] rack) {
        if (aiMove == null) {
            return rack;
        }

        HashSet<Tile> tilesToRemove = new HashSet<>();
        tilesToRemove.addAll(aiMove);

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
     * @param aiMove move of tiles
     * @param board gameboard
     * @return updated gameboard
     */
    public static Tile[][] removeTilesFromBoard(ArrayList<Tile> aiMove, Tile[][] board) {
        if (aiMove == null) {
            return board;
        }

        HashSet<Tile> tilesToRemove = new HashSet<>();
        tilesToRemove.addAll(aiMove);
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (tilesToRemove.contains(board[i][j])) {
                    board[i][j] = null;
                }
            }
        }

        return board;
    }

    public static void main(String[] args) {
        
        Tile n = null;
        Tile j = new Tile((byte) -1, Color.RED);

        Tile t4B = new Tile((byte) 4, Color.BLACK);
        Tile t5B = new Tile((byte) 5, Color.BLACK);
        Tile t6B = new Tile((byte) 6, Color.BLACK);
        Tile t7B = new Tile((byte) 7, Color.BLACK);
        Tile t8B = new Tile((byte) 8, Color.BLACK);
        Tile t9B = new Tile((byte) 9, Color.BLACK);
        Tile t10B = new Tile((byte) 10, Color.BLACK);
        Tile t11B = new Tile((byte) 11, Color.BLACK);
        Tile t12B = new Tile((byte) 12, Color.BLACK);
        Tile t13B = new Tile((byte) 13, Color.BLACK);


        Tile t9Bbis = new Tile((byte) 9, Color.BLACK);
        
        Tile[][]  board = {
            {n, n, n,n, n, n, n, n, n, n, n, n, n, n, n}
        };
        
        System.out.println(Game.printBoard(board));
        Tile[][]  rack = {{n, n, n, t6B, n, n, t10B, n, n, t8B, n, n, n, t9B, n},
                        {n, t4B, n, t12B, j, t11B, n, n, t5B, n, n, n, t13B, n, n}
                        };

        ArrayList<Node> pathToNode = findBestMove(board, rack);
        
        System.out.println("length of path : " + pathToNode.size());
        System.out.println();
        String s = "";
        for (Node node : pathToNode) {
            s += node.getAgentUsed() + " with value : " + node.getValue() +  " still " + node.getTilesInRack() + " with move : " + BaselineAgent.printMove(node.getMove()) + "\n";
        }
        System.out.println(s);
        /*
        Node n1 = new Node(board, rack, 1, 0);
        Node n2 = new Node(board, rack, 1, 2);
        Node n3 = new Node(board, rack, 1, 3);
        Node n4 = new Node(board, rack, 1, 6);
        Node n5 = new Node(board, rack, 1, 1);
        Node n6 = new Node(board, rack, 1, 10);
        Node n7 = new Node(board, rack, 1, 9);

        n1.addChild(n2);
        n1.addChild(n3);
        n1.addChild(n4);
        n3.addChild(n5);
        n3.addChild(n6);
        n6.addChild(n7);


        System.out.println("n5 total value is " + n5.getTotalValue());
        System.out.println("n7 total value is " + n7.getTotalValue());
        Node node = findNodeWithMaxTotalValue(n1);
        // System.out.println("value is " + node.getValue());
        
        ArrayList<Node> list = getPathToNode(node);
        System.out.println("list size " + list.size());

        String s = "";
        for (Node nn : list) {

            System.out.println(nn.getValue());
            s += nn.getValue() + " ";
        }
        System.out.println(s);
        */
    }
    
}