package com.rummikub.game;

import com.rummikub.model.Type;

/**
 * This class represents a player instance and provides all the move methods needed <br>
 * for a player to make a move
 */
public class Player {

    private String name;
    private Tile[][] hand;
    private boolean isAI;
    private Type aiType;

    public void setHand(Tile[][] hand) {

        this.hand = hand;
    }

    private boolean firstMoveMade;
    private boolean moveFinished;


    /**
     * constructs a Player instance with a given name, that has a hand.
     */
    Player(String name, boolean isAI) {
        this.name = name;
        this.hand = new Tile[2][15];
        this.firstMoveMade = false;
        this.isAI = isAI;
        this.aiType = Type.MAIN;
    }

    public boolean isAI() {
        return isAI;
    }

    public Type getAiType() {
        return aiType;
    }
    public void draw(Tile tile) {

        boolean tileAdded = false;

        for (int i = 0; i < hand[0].length; i++) {
            for (int j = 0; j < hand[1].length; j++) {

                if (hand[i][j] == null) {
                    hand[i][j] = tile;
                    tileAdded = true;
                    break;
                }

            }
            if (tileAdded) break;
        }

        //System.out.println(Game.printBoard(hand));

    }

    public boolean isMoveFinished() {
        return moveFinished;
    }

    public void startMove() {
        moveFinished = false;
    }

    public void finishMove() {
        moveFinished = true;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public Tile[][] getHand() {
        return hand;
    }
    
    public boolean getFirstMoveMade() {
        return firstMoveMade;
    }

    /**
     * determines whether a first move has been made by a player
     */
    public void firstMoveMade() {
        this.firstMoveMade = true;
    }

}
