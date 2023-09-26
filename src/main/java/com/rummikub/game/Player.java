package com.rummikub.game;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a player instance and provides all the move methods needed <br>
 * for a player to make a move
 */
class Player {

    private String name;
    private List<Tile> hand;
    private boolean firstMoveMade;


    /**
     * constructs a Player instance with a given name, that has a hand.
     */
    Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.firstMoveMade = false;
    }

    @Override
    public String toString() {
        return name;
    }

    String getName() {
        return name;
    }

    List<Tile> getHand() {
        return hand;
    }
    
    boolean getFirstMoveMade() {
        return firstMoveMade;
    }

    /**
     * determines whether a first move has been made by a player
     */
    void firstMoveMade() {
        this.firstMoveMade = true;
    }

    /**
     * this method draws a tile, and adds it to the player's hand
     * @param tile  tile drawn from the pool
     */
    void drawTile(Tile tile) {
        hand.add(tile);
    }

    /**
     * method that removes a tile from a player's hand
     * @param tile  tile to be removed/played
     */
    void playTile(Tile tile) {
        hand.remove(tile);
    }
}
