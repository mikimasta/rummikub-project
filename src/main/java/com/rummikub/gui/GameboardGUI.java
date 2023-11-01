package com.rummikub.gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class GameboardGUI extends Pane {

    private static GameboardGUI instance;


    private static final int MAX_TILES_PER_ROW = 20;
    private static final int MAX_TILES_PER_COL = 7;

    static final double BOARD_WIDTH = Tile.TILE_WIDTH * MAX_TILES_PER_ROW;
    static final double BOARD_HEIGHT = Tile.TILE_HEIGHT * MAX_TILES_PER_COL;

    static final double BOARD_X = Rummikub.xCenter - BOARD_WIDTH / 2;
    static final double BOARD_Y = 90;

    private Tile[][] state;

    private Tile[][] prevState = new Tile[MAX_TILES_PER_COL][MAX_TILES_PER_ROW];


    private GameboardGUI() {

        state = new Tile[MAX_TILES_PER_COL][MAX_TILES_PER_ROW];
        setPrevState();

        setMinSize(BOARD_WIDTH, BOARD_HEIGHT);
        setMaxSize(BOARD_WIDTH, BOARD_HEIGHT);
        setPrefSize(BOARD_WIDTH, BOARD_HEIGHT);

        setLayoutX(BOARD_X);
        setLayoutY(BOARD_Y);


        for (int i = 0; i < MAX_TILES_PER_COL; ++i) {
            for (int j = 0; j < MAX_TILES_PER_ROW; ++j) {


            Rectangle cell = new Rectangle(Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
            cell.setTranslateX(j * Tile.TILE_WIDTH);
            cell.setTranslateY(i * Tile.TILE_HEIGHT);
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

    public void setPrevState() {
        for (int i = 0; i < state.length; i++) {
            prevState[i] = state[i].clone();
        }

    }
    
    public Tile[][] getState() {
        return state;
    }

    public Tile[][] getPrevState() {
        return prevState;
    }

    public void update(Tile tile) {

        //System.out.println(Arrays.deepToString(state));


        if (state[tile.getRowToSnap()][tile.getColToSnap()] == null) {


            state[tile.getPrevRowBoard()][tile.getPrevColBoard()] = null;
            state[tile.getRowToSnap()][tile.getColToSnap()] = tile;

        } else {
                tile.setXPos(tile.getPrevColBoard() * Tile.TILE_WIDTH);
                tile.setYPos(tile.getPrevRowBoard() * (RackGUI.RACK_HEIGHT / 2));


        }


        tile.setTranslateX(tile.getXPos());
        tile.setTranslateY(tile.getYPos());
        //System.out.println(Arrays.deepToString(state));


    }

    public boolean stateNotChanged() {

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (!(state[i][j] == prevState[i][j])) return false;
            }
        }

        return true;

    }

}
