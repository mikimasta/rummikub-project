package com.rummikub.game;

import java.util.ArrayList;

public class Node {

    private Tile[][] board;
    private Tile[][] rack;
    private int value;
    private ArrayList<Node> children;
    private ArrayList<Tile> move;
    private ArrayList<ArrayList<Tile>> moves;
    private Node parent;

    public Node(Tile[][] board, Tile[][] rack, int value) {
        this.board = board;
        this.rack = rack;
        this.value = value;
        this.children = new ArrayList<>();
        this.move = new ArrayList<>();
        this.parent = null;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Tile[][] getRack() {
        return rack;
    }

    public ArrayList<Tile> getMove() {
        return move;
    }

    public ArrayList<ArrayList<Tile>> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<ArrayList<Tile>> moves) {
        this.moves = moves;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public int getValue() {
        return value;
    }

    public Node getParent() {
        return parent;
    }

    public void addChild(Node child) {
        children.add(child);
        child.parent = this;
    }
}
