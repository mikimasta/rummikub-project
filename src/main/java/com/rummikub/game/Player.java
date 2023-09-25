package com.rummikub.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Tile> hand;
    private boolean firstMoveMade;


    // Constructor method to create a Player object with a given name
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.firstMoveMade = false;
    }
    // Getter method to retrieve the player's name
    public String getName(){
        return name;
    }
    // Getter method to retrieve the player's hand as a list of Tile objects
    public List<Tile> getHand(){
        return hand;
    }
    
    //Getter method to retrieve boolean indicated if the first move has been made
    public boolean getFirstMoveMade(){
        return firstMoveMade;
    }

    // Setter method to update the player's name
    public void setName(String name){
        this.name = name;
    }

    // Method to change boolean to indicate the player has made the first move
    public void firstMoveMade(){
        this.firstMoveMade = true;
    }

    // Method to add a Tile object to the player's hand when the player draws
    public void drawTile(Tile tile) {
        hand.add(tile);
    }

    // Method to remove a specific Tile object from the player's hand when it is played
    public void playTile(Tile tile) {
        hand.remove(tile);
    }
}
