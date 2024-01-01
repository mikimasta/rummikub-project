package com.rummikub.game;

import javafx.scene.paint.Color;

import java.util.Objects;

/**
 * This class is a memory representation of a tile in the game
 */
public class Tile {

    private final byte number;
    private final Color color;
    private String colorString;

    private boolean locked = false;

    public Tile(byte number, Color color) {
        this.number = number;
        this.color = color;

        if (color.equals(Color.BROWN)){
            this.colorString = "Brown";
        }else{
            if (color.equals(Color.ORANGE)) {
            this.colorString = "orange";
        } else if (color.equals(Color.RED)) {
            this.colorString = "red";
        } else if (color.equals(Color.BLACK)) {
            this.colorString = "black";
        } else if (color.equals(Color.BLUE)) {
            this.colorString = "blue";
        }
        }
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
    public String toString() {
        return "Tile" + number + colorString;
        //return " " + number;
    }


    public int compareTo(Tile otherTile) {
        int colorComparison = this.colorString.compareTo(otherTile.colorString);
        if (colorComparison != 0) {
            return colorComparison;
        } else {
            return Byte.compare(this.number, otherTile.number);
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Tile)) {
            return false;
        }
        Tile otherTile = (Tile) obj;
        return this.number == otherTile.number && this.color.equals(otherTile.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, color);
    }


}
