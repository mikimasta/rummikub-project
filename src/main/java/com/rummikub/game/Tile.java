package com.rummikub.game;

import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * This class is a memory representation of a tile in the game
 */
public class Tile {

    private byte number;
    private Color color;
    private String colorString;

    private boolean locked = false;

    public Tile(byte number, Color color) {
        this.number = number;
        this.color = color;


        if (color.equals(Color.ORANGE)) {
            this.colorString = "orange";
        } else if (color.equals(Color.RED)) {
            this.colorString = "red";
        } else if (color.equals(Color.BLACK)) {
            this.colorString = "black";
        } else if (color.equals(Color.BLUE)) {
            this.colorString = "blue";
        }else if(color.equals(Color.BROWN)){
            this.colorString = "null";
        }
    }
    public void printTile(){
        System.out.print(getNumber()+""+getColor());
    }
    public byte getNumber() {
        return number;
    }

    public Color getColor() {
        return color;
    }
    
    public String getColorString() {
        return colorString;
    }

    public void lock() {
        this.locked = true;
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return number == tile.number && Objects.equals(color, tile.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, color);
    }


}
