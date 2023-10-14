package com.rummikub.gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

class RackGUI extends GridPane {

    static final int RACK_WIDTH = 1000;
    static final int RACK_HEIGHT = 188;
    private static final int MAX_TILES_PER_ROW;
    private int tilesInRow = 0;
    private static final int H_GAP = 5;
    static double RACK_Y = 880;
    static double RACK_X = Rummikub.xCenter - RACK_WIDTH / 2;

    static {
        double tilesNoGap = RACK_WIDTH / TileGUI.TILE_WIDTH;
        double pixelGap = tilesNoGap * H_GAP;
        double tileOverLimit = pixelGap / TileGUI.TILE_WIDTH;
        MAX_TILES_PER_ROW = (int) (tilesNoGap - tileOverLimit);
        System.out.println(MAX_TILES_PER_ROW);
    }

    RackGUI() {

        setGridLinesVisible(true);

        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        setLayoutX(RACK_X);
        setLayoutY(RACK_Y);
        setMaxSize(RACK_WIDTH, RACK_HEIGHT);
        setMinSize(RACK_WIDTH, RACK_HEIGHT);
        setPrefSize(RACK_WIDTH, RACK_HEIGHT);
        setHgap(H_GAP);
        //setVgap(20);

        ColumnConstraints cellWidth = new ColumnConstraints(TileGUI.TILE_WIDTH);
        RowConstraints cellHeight = new RowConstraints(TileGUI.TILE_HEIGHT);

        getColumnConstraints().add(cellWidth);
        getRowConstraints().add(cellHeight);


    }
    
    void addToRack(TileGUI tile) {

        if (!(tilesInRow == 2 * MAX_TILES_PER_ROW)) {

            if (tilesInRow <= MAX_TILES_PER_ROW) {
                addRow(0, tile);
                ++tilesInRow;
            } else {
                addRow(2, tile);
                ++tilesInRow;
            }
        } else
            System.out.println("Your rack is full!");

    }

     
}
