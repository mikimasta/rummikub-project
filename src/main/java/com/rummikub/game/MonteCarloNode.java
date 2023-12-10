package com.rummikub.game;

import java.util.ArrayList;

public class MonteCarloNode {

        private ArrayList<Tile> hand;
        private ArrayList<ArrayList<Tile>> board;
        private int visits;
        private double value;
    
        // Constructor, getters, and setters
        public MonteCarloNode(ArrayList<Tile> hand, ArrayList<ArrayList<Tile>> board){
            this.hand = hand;
            this.board = board;
        }

        public int getVisits() {
            return visits;
        }

        public void setVisits(int visits) {
            this.visits = visits;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public ArrayList<Tile> getHand() {
            return hand;
        }

        public ArrayList<ArrayList<Tile>> getBoard() {
            return board;
        }
}

