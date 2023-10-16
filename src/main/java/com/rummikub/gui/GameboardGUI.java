package com.rummikub.gui;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

class GameboardGUI extends Pane {

    private static GameboardGUI instance;


    private static final int MAX_TILES_PER_ROW = 20;
    private static final int MAX_TILES_PER_COL = 7;

    static final double BOARD_WIDTH = TileGUI.TILE_WIDTH * MAX_TILES_PER_ROW;
    static final double BOARD_HEIGHT = TileGUI.TILE_HEIGHT * MAX_TILES_PER_COL;

    static final double BOARD_X = Rummikub.xCenter - BOARD_WIDTH / 2;
    static final double BOARD_Y = 90;



    private GameboardGUI() {

        setMinSize(BOARD_WIDTH, BOARD_HEIGHT);
        setMaxSize(BOARD_WIDTH, BOARD_HEIGHT);
        setPrefSize(BOARD_WIDTH, BOARD_HEIGHT);

        setLayoutX(BOARD_X);
        setLayoutY(BOARD_Y);


        for (int i = 0; i < MAX_TILES_PER_COL; ++i) {
            for (int j = 0; j < MAX_TILES_PER_ROW; ++j) {


            Rectangle cell = new Rectangle(TileGUI.TILE_WIDTH, TileGUI.TILE_HEIGHT);
            cell.setTranslateX(j * TileGUI.TILE_WIDTH);
            cell.setTranslateY(i * TileGUI.TILE_HEIGHT);
            cell.setFill(Color.TRANSPARENT);
            cell.setStroke(Color.BLACK);



            getChildren().add(cell);
        }
       
        }

    }



    public static GameboardGUI getInstance() {
        if (instance == null) 
            instance = new GameboardGUI();
        return instance;

    }

}
