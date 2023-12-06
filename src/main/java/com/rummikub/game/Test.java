package com.rummikub.game;

import javafx.scene.paint.Color;

public class Test {

    static Tile n = null;
    static Tile j = new Tile((byte) 0, Color.RED);
    static Tile t11O = new Tile((byte) 11, Color.RED);
    static Tile t12O = new Tile((byte) 12, Color.RED);
    static Tile t13O = new Tile((byte) 13, Color.RED);


    public static void main(String[] args) {
        Game game = new Game((byte) 2);
        Test test = new Test();
        Tile[][]  board = {{n, n, n, n, n, n, n, n, n, n, n, t11O, j, t13O, n}};
        System.out.println(game.isValidBoard(board));
    }
}
