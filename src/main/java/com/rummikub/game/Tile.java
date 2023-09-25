package com.rummikub.game;

public class Tile {
    private String color;
    private int number;

    // Constructor method to initialize a Tile with a given color and number
    public Tile(String color, int number) {
        this.color = color;
        this.number = number;
    }

    // Getter method to retrieve the color of the tile
    public String getColor(){
        return color;
    }

    // Getter method to retrieve the number of the tile
    public int getNumber(){
        return number;
    }
}
