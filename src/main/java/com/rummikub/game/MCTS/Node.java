package com.rummikub.game.MCTS;

import java.util.ArrayList;
import java.util.List;
import com.rummikub.game.Tile;

public class Node {

    private Node parent;
    private List<Node> children;
    private int visits;
    private double reward;
    private ArrayList<ArrayList<Tile>> stateBoard;
    private ArrayList<Tile> stateHand;

    public Node(ArrayList<ArrayList<Tile>> stateBoard, ArrayList<Tile> stateHand) {
        this.stateBoard = stateBoard;
        this.stateHand = stateHand;
        this.children = new ArrayList<>();
        this.visits = 0;
        this.reward = 0.0;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public int getVisits() {
        return visits;
    }

    public double getReward() {
        return reward;
    }

    public ArrayList<ArrayList<Tile>>  getStateBoard() {
        return stateBoard;
    }

    public ArrayList<Tile>  getStateHand() {
        return stateHand;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public void updateScore(double score) {
        visits++;
        reward += score;
    }

}
