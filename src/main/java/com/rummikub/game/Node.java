package com.rummikub.game;

import java.util.ArrayList;

public class Node {

    private Tile[][] board;
    private Tile[][] rack;
    private int valueOfNode;
    private int totalValue;
    private int agentUsed;
    private int visits;
    private ArrayList<Node> children;
    private ArrayList<Tile> move;
    // private ArrayList<ArrayList<Tile>> moves;
    private Node parent;
    boolean isLeafNode;

    public Node(Tile[][] board, Tile[][] rack, int agentUsed, int valueOfNode) {
        this.board = board;
        this.rack = rack;
        this.children = new ArrayList<>();
        this.move = new ArrayList<>();
        //this.moves = new ArrayList<ArrayList<Tile>>();
        this.parent = null;
        this.agentUsed = agentUsed;
        this.valueOfNode = valueOfNode;
        this.isLeafNode = false;

        
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void setBoard(Tile [][] board) {
        this.board = board;
    }

    public Tile[][] getRack() {
        return rack;
    }

    public void setRack(Tile [][] rack) {
        this.rack = rack;
    } 

    public ArrayList<Tile> getMove() {
        return move;
    }

    public void setMove(ArrayList<Tile> move) {
        this.move = move;
    }

    public int getAgentUsed() {
        return agentUsed;
    }

    public void setAgentUsed(int agentUsed) {
        this.agentUsed = agentUsed;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public int getValue() {
        return valueOfNode;
    }

    public void setValue(int value) {
        this.valueOfNode = value;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int value) {
        if (parent != null) {
            this.totalValue = value + parent.getTotalValue();
        } else {
            this.totalValue = value;
        } 
        // could have written : this.totalValue = value + (parent != null ? parent.getTotalValue() : 0);
    }

    public Node getParent() {
        return parent;
    }

    public void addChild(Node child) {
        children.add(child);
        child.parent = this;
        child.setTotalValue(child.getValue());
    }
    

    public int getVisits() {
        return visits;
    }

/*
 *  public boolean getIsLeafNode() {
        return isLeafNode;
    }
    public ArrayList<Integer> getAgentsUsed(Node node) {
        ArrayList<Integer> agentsUsed = new ArrayList<>();
        
        for (Node n : node.getChildren()) {
            agentsUsed.add(n.getAgentUsed());
        }

        return agentsUsed;
    }
    
    public ArrayList<ArrayList<Tile>> getMoves() {
        return moves;
    }

    public void addToMoves(ArrayList<Tile> move) {
        this.moves.add(move);
    }
 */

}
