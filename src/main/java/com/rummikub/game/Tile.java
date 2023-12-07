package com.rummikub.game;

import javafx.scene.paint.Color;

/**
 * This class is a memory representation of a tile in the game
 */
public class Tile {

    private byte number;
    private Color color;

    private boolean locked = false;

    public Tile(byte number, Color color) {
        this.number = number;
        this.color = color;
    }


    public byte getNumber() {
        return number;
    }

    public Color getColor() {
        return color;
    }

    public void lock() {
        this.locked = true;
    }

    public boolean isLocked() {
        return locked;
    }

}
