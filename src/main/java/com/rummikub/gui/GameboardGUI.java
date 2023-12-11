package com.rummikub.gui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import com.rummikub.game.Tile;

class GameboardGUI extends Pane {

    private static GameboardGUI instance;


    private static final int MAX_TILES_PER_ROW = 20;
    private static final int MAX_TILES_PER_COL = 7;

    static final double BOARD_WIDTH = TileGUI.TILE_WIDTH * MAX_TILES_PER_ROW;
    static final double BOARD_HEIGHT = TileGUI.TILE_HEIGHT * MAX_TILES_PER_COL;

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

        ImageView logo = new ImageView(Images.rummikubLogoOnBoard);
        logo.setOpacity(0.3);
        logo.setPreserveRatio(true);
        logo.setFitWidth(400);
        logo.setLayoutX(BOARD_WIDTH / 2 - logo.getFitWidth() / 2);
        logo.setLayoutY(BOARD_HEIGHT / 2 - logo.getFitHeight());

        Rectangle border = new Rectangle(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        border.setStroke(Color.GREY);
        border.setFill(Color.TRANSPARENT);

        getChildren().addAll(logo, border);

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

    public void update(TileGUI tile) {

        //System.out.println(Arrays.deepToString(state));


        if (state[tile.getRowToSnap()][tile.getColToSnap()] == null) {

            state[tile.getRowToSnap()][tile.getColToSnap()] = tile.getTile();

        } else {
                tile.setXPos(tile.getPrevColBoard() * TileGUI.TILE_WIDTH);
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

    public void lockTiles() {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[1].length; j++) {
                if (state[i][j] != null) state[i][j].lock();
            }
        }
    }


    public void renderAIMove() {

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                
                Tile t = state[i][j];

                if (t != null && !t.isLocked()) {

                    TileGUI tGui = new TileGUI(t); 
                    tGui.setPrevBoardCoords(j, i);
                    tGui.addToBoard();
                    tGui.setTranslateX(j * TileGUI.TILE_WIDTH);
                    tGui.setTranslateY(i * TileGUI.TILE_HEIGHT);

                    getChildren().add(tGui);
                }

            }
        }

    }

}
