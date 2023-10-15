package com.rummikub.gui;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Arrays;


class RackGUI extends Pane {

    static final int RACK_WIDTH = 1000;
    static final int RACK_HEIGHT = 188;
    private static final int MAX_TILES_PER_ROW;
    private int tileCount = 0;
    static final int H_GAP = 0;
    static final int X_OFFSET = 10;
    static double RACK_Y = 880;
    static double RACK_X = Rummikub.xCenter - RACK_WIDTH / 2;

    private static RackGUI instance;


    TileGUI[][] tiles;

    static {
        double tilesNoGap = RACK_WIDTH / TileGUI.TILE_WIDTH;
        double pixelGap = tilesNoGap * H_GAP;
        double tileOverLimit = pixelGap / TileGUI.TILE_WIDTH;
        MAX_TILES_PER_ROW = (int) (tilesNoGap - tileOverLimit);
        System.out.println(MAX_TILES_PER_ROW);
    }

    public static RackGUI getInstance() {
        if (instance == null)
            instance = new RackGUI();
        return instance;
    }

    private RackGUI() {

        tiles = new TileGUI[2][MAX_TILES_PER_ROW];
        //System.out.println(Arrays.deepToString(tiles));

        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        setLayoutX(RACK_X);
        setLayoutY(RACK_Y);
        setMaxSize(RACK_WIDTH, RACK_HEIGHT);
        setMinSize(RACK_WIDTH, RACK_HEIGHT);
        setPrefSize(RACK_WIDTH, RACK_HEIGHT);

        double xRect = 0;
        double yRect = 0;
        int tileCount = 0;

      
        /*
        for (int i = 0; i < MAX_TILES_PER_ROW * 2; ++i) {
            //System.out.println(xRect);

            Rectangle cell = new Rectangle(TileGUI.TILE_WIDTH, TileGUI.TILE_HEIGHT);
            cell.setTranslateX(xRect + 10);
            cell.setTranslateY(yRect);
            cell.setFill(Color.TRANSPARENT);
            cell.setStroke(Color.BLACK);



            getChildren().add(cell);
            tileCount++;
            xRect += TileGUI.TILE_WIDTH + 5;
            if (tileCount == MAX_TILES_PER_ROW) {

                xRect = 0;
                yRect += TileGUI.TILE_HEIGHT;

            }
        }
        */
        

    }

    
    void addToRack(TileGUI tile) {

        assert tile != null;
        boolean tileAdded = false;

        for (int i = 0; i < tiles[0].length; ++i) {
            for (int j = 0; j < tiles[1].length; ++j) {

                if (tiles[i][j] == null) {

                    tiles[i][j] = tile;
                    tile.setXPos(j * (TileGUI.TILE_WIDTH + H_GAP) + X_OFFSET);
                    tile.setYPos(i * RACK_HEIGHT / 2);
                    tile.setTranslateX(tile.getXPos());
                    tile.setTranslateY(tile.getYPos());
                    getChildren().add(tile);
                    tile.toFront();
                    tileAdded = true;
                    break;
                }

            }

            if (tileAdded) break;

        }

    }

    void updateRack(TileGUI tile) {

        System.out.println(Arrays.deepToString(tiles));


                if (tile != null) {
                    tile.setTranslateX(tile.getXPos());
                    tile.setTranslateY(tile.getYPos());

                    tiles[tile.getPrevRow()][tile.getPrevCol()] = null;
                    tiles[tile.getRowToSnap()][tile.getColToSnap()] = tile;
                }

        System.out.println(Arrays.deepToString(tiles));

    }

     
}
