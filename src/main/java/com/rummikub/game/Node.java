package com.rummikub.game;

import java.util.ArrayList;

public class Node {

    private Tile[][] board;
    private Tile[][] rack;
    private int valueOfNode;
    private int totalValue;
    private int agentUsed;
    private int tilesInRack;
    private ArrayList<Node> children;
    private ArrayList<Tile> move;
    private Node parent;
    protected boolean isLeafNode;

    public Node(Tile[][] board, Tile[][] rack, int agentUsed, int valueOfNode) {
        this.board = board;
        this.rack = rack;
        this.children = new ArrayList<>();
        this.move = new ArrayList<>();
        this.parent = null;
        this.agentUsed = agentUsed;
        this.valueOfNode = valueOfNode;
        this.isLeafNode = false;
        this.tilesInRack = tilesInRack(rack);
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
    }

    public int getTilesInRack() {
        return tilesInRack;
    }

    public void setTilesInRack(int tilesInRack) {
        this.tilesInRack = tilesInRack;
    }

    public Node getParent() {
        return parent;
    }

    public void addChild(Node child) {
        children.add(child);
        child.parent = this;
        child.setTotalValue(child.getValue());
    }

    
    public static int tilesInRack(Tile[][] rack) {
        int count = 0;

        for (int i = 0; i < rack.length; i++) {
            for (int y = 0; y < rack[i].length; y++) {
                if (rack[i][y] != null) {
                    count++;
                }
            }
        }
        return count;
    }
}
