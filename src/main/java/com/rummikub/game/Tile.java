package com.rummikub.game;

import javafx.scene.paint.Color;

import java.util.Comparator;

/**
 * This class is a memory representation of a tile in the game
 */
public class Tile implements Comparable, Cloneable {

    private final byte number;
    private final Color color;
    private String colorString;

    private boolean locked = false;

    public Tile(byte number, Color color) {
        this.number = number;
        this.color = color;

        if (number == (byte) -1){
            this.colorString = "z";
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
        return number + colorString;
        //return " " + number;
    }


    @Override
    public int compareTo(Object o) {
        Tile other = (Tile) o;
        int numberComparison = Integer.compare(this.getNumber(), other.getNumber());

        if (numberComparison != 0) {
            return numberComparison;
        }

        int colorComparison = this.getColorString().compareTo(other.getColorString());

        if (colorComparison != 0) {
            return colorComparison;
        }

        // Compare object identity to ensure distinct objects are not considered equal
        return System.identityHashCode(this) - System.identityHashCode(other);
    }
    @Override
    public Tile clone() {
        try {
            // No need to perform a deep copy for javafx.scene.paint.Color (immutable)
            return (Tile) super.clone();
        } catch (CloneNotSupportedException e) {
            // Handle the exception or throw it further
            return null;
        }
    }
}
