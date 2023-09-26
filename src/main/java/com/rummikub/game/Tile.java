package com.rummikub.game;

/**
 * This class represents the tile, given its number and color. 
 */
class Tile {

    private String color;
    private byte  number;

    /**
     * constructs a tile with a given color and number
     * @param color  color passed as a hex String
     * @param number the number that the tile has
     */
    Tile(String color, byte number) {
        this.color = color;
        this.number = number;
    }

    public String getColor(){
        return color;
    }

    public byte getNumber(){
        return number;
    }
}
