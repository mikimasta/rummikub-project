package com.rummikub.gui;

import com.rummikub.game.Tile;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class RackGUI extends Pane {

    static final int RACK_WIDTH = 1000;
    static final int RACK_HEIGHT = 188;
    public static final int MAX_TILES_PER_ROW;
    static final int H_GAP = 0;
    static final int X_OFFSET = 10;
    static double RACK_Y = 880;
    static double RACK_X = Rummikub.xCenter - RACK_WIDTH / 2;
    private static RackGUI instance;


    Tile[][] tiles;

    static {
        double tilesNoGap = RACK_WIDTH / TileGUI.TILE_WIDTH;
        double pixelGap = tilesNoGap * H_GAP;
        double tileOverLimit = pixelGap / TileGUI.TILE_WIDTH;
        MAX_TILES_PER_ROW = (int) (tilesNoGap - tileOverLimit);
        //System.out.println(MAX_TILES_PER_ROW);
    }

    public static RackGUI getInstance() {
        if (instance == null)
            instance = new RackGUI();
        return instance;
    }

    private RackGUI() {

        tiles = new Tile[2][MAX_TILES_PER_ROW];
        //System.out.println(Arrays.deepToString(tiles));

        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        setLayoutX(RACK_X);
        setLayoutY(RACK_Y);
        setMaxSize(RACK_WIDTH, RACK_HEIGHT);
        setMinSize(RACK_WIDTH, RACK_HEIGHT);
        setPrefSize(RACK_WIDTH, RACK_HEIGHT);


    }


    void update(TileGUI tile) {

        //System.out.println(Game.printBoard(tiles));





                    tile.setTranslateX(tile.getXPos());
                    tile.setTranslateY(tile.getYPos());
        //System.out.println(Game.printBoard(tiles));

    }


    public void handToRack(Tile[][] hand) {

        getChildren().clear();

        for (int i = 0; i < hand.length; i++) {
            for (int j = 0; j < hand[0].length; j++) {

                if (!(hand[i][j] == null)) {
                    //Game.getInstance().currentPlayer.getHand()[i][j] = hand[i][j];
                    TileGUI tile = new TileGUI(hand[i][j]);
                    //tiles[i][j] = tile.getTile();

                    tile.setXPos(j * (TileGUI.TILE_WIDTH + H_GAP) + X_OFFSET);
                    tile.setYPos(i * RACK_HEIGHT / 2);
                    tile.setTranslateX(tile.getXPos());
                    tile.setTranslateY(tile.getYPos());
                    getChildren().add(tile);
                    tile.toFront();
                }
            }
        }


    }
}
