package com.rummikub.game;

import com.rummikub.gui.RackGUI;
import com.rummikub.gui.Tile;

/**
 * This class represents a player instance and provides all the move methods needed <br>
 * for a player to make a move
 */
public class Player {

    private String name;
    private Tile[][] hand;

    public void setHand(Tile[][] hand) {

        this.hand = hand;
    }

    private boolean firstMoveMade;
    private boolean moveFinished;


    /**
     * constructs a Player instance with a given name, that has a hand.
     */
    Player(String name) {
        this.name = name;
        this.hand = new Tile[2][RackGUI.MAX_TILES_PER_ROW];
        this.firstMoveMade = false;
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
