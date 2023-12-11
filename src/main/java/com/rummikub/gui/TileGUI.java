package com.rummikub.gui;

import com.rummikub.game.Game;
import com.rummikub.game.Tile;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

class TileGUI extends StackPane {

    static final double TILE_WIDTH;
    static final double TILE_HEIGHT = RackGUI.RACK_HEIGHT / 2;
    static final ImageView TILE_FACE;

    private Tile tile;

    private boolean addedToBoard = false;
    private boolean locked = false;

    private double mouseX;
    private double mouseY;

    private double xPos;
    private double yPos;

    private int colToSnap;
    private int rowToSnap;

    private int prevColBoard;
    private int prevRowBoard;

    private int prevColRack;
    private int prevRowRack;


    static {

        TILE_FACE = new ImageView(Images.tileFace);
        TILE_FACE.setPreserveRatio(true);
        TILE_FACE.setFitHeight(TILE_HEIGHT);
        TILE_WIDTH = TILE_FACE.getBoundsInLocal().getWidth();

    }

    public Tile getTile() {
        return tile;
    }


    private TileGUI() {

        setMinSize(TILE_WIDTH, RackGUI.RACK_HEIGHT / 2);
        setMaxSize(TILE_WIDTH, RackGUI.RACK_HEIGHT / 2);
        setPrefSize(TILE_WIDTH, RackGUI.RACK_HEIGHT / 2);


        setOnMousePressed(e -> {
            toFront();
            mouseX = e.getSceneX() - getTranslateX();
            mouseY = e.getSceneY() - getTranslateY();

            double mouseRelToSceneX = e.getSceneX();
            double mouseRelToSceneY = e.getSceneY();

            if (isInRackBounds(mouseX, mouseY)) {
                prevColRack = (int) (Math.abs(RackGUI.RACK_X - mouseRelToSceneX + RackGUI.X_OFFSET) / TILE_WIDTH);
                prevRowRack = (int) ((mouseRelToSceneY > (RackGUI.RACK_Y + RackGUI.RACK_HEIGHT / 2)) ? 1 : 0);
                //System.out.println("Rack coords: " + prevRowRack + " " + prevColRack);

            }

            if (isInBoardBounds(mouseX, mouseY)) {
                prevColBoard = (int) (Math.abs(GameboardGUI.BOARD_X - mouseRelToSceneX) / TILE_WIDTH);
                prevRowBoard = (int) (Math.abs(GameboardGUI.BOARD_Y - mouseRelToSceneY) / TILE_HEIGHT);
                //System.out.println("Board coords: " + prevRowBoard + " " + prevColBoard);

            }


        });

        setOnMouseDragged(ev -> {
            this.toFront();
            setTranslateX(ev.getSceneX() - mouseX);
            setTranslateY(ev.getSceneY() - mouseY);

        });

        setOnMouseReleased(e -> {


            mouseX = e.getSceneX();
            mouseY = e.getSceneY();

            if (isInRackBounds(mouseX, mouseY)) {

                colToSnap = (int) (Math.abs(RackGUI.RACK_X - mouseX + RackGUI.X_OFFSET) / TILE_WIDTH);
                //System.out.println(colToSnap);
                rowToSnap = (int) ((mouseY > (RackGUI.RACK_Y + RackGUI.RACK_HEIGHT / 2)) ? 1 : 0);
                //System.out.println(rowToSnap);

                if (Game.getInstance().currentPlayer.getHand()[rowToSnap][colToSnap] == null) {

                    if (!tile.isLocked()) {

                        Game.getInstance().currentPlayer.getHand()[getPrevRowRack()][getPrevColRack()] = null;
                        Game.getInstance().currentPlayer.getHand()[rowToSnap][colToSnap] = this.getTile();

                        setXPos(colToSnap * TILE_WIDTH + (colToSnap * RackGUI.H_GAP) + RackGUI.X_OFFSET);
                        setYPos(rowToSnap * (RackGUI.RACK_HEIGHT / 2));


                    if (addedToBoard) {
                        GameboardGUI.getInstance().getChildren().remove(this);
                        RackGUI.getInstance().getChildren().add(this);
                        this.toFront();
                        GameboardGUI.getInstance().getState()[getPrevRowBoard()][getPrevColBoard()] = null;
                        addedToBoard = false;
                        }
                    }

                    RackGUI.getInstance().update(this);
                }

            } else if (isInBoardBounds(mouseX, mouseY)) {

                colToSnap = (int) (Math.abs(GameboardGUI.BOARD_X - mouseX) / TILE_WIDTH);
                rowToSnap = (int) (Math.abs(GameboardGUI.BOARD_Y - mouseY) / TILE_HEIGHT);

                if (GameboardGUI.getInstance().getState()[rowToSnap][colToSnap] == null) {

                    //System.out.println("Board snap at " + rowToSnap + " " + colToSnap);

                    setXPos(colToSnap * TILE_WIDTH);
                    setYPos(rowToSnap * TILE_HEIGHT);


                    if (!addedToBoard) {

                        RackGUI.getInstance().getChildren().remove(this);
                        GameboardGUI.getInstance().getChildren().add(this);
                        Game.getInstance().currentPlayer.getHand()[getPrevRowRack()][getPrevColRack()] = null;
                        addedToBoard = true;

                    } else {
                        GameboardGUI.getInstance().getState()[getPrevRowBoard()][getPrevColBoard()] = null;
                    }

                    GameboardGUI.getInstance().update(this);
                }
            }


            /*
            System.out.println(rowToSnap);
            System.out.println(colToSnap);
            System.out.println(TILE_WIDTH);
            */

            setTranslateX(getXPos());
            setTranslateY(getYPos());


            // System.out.println(Game.printBoard(GameboardGUI.getInstance().getState()));

        });

    }


    public TileGUI(Tile tile) {

        this();

        this.tile = tile;

        if (tile.getNumber() != -1) {


        ImageView tileFace = new ImageView(Images.tileFace);
        tileFace.setPreserveRatio(true);
        tileFace.setFitHeight(TILE_HEIGHT);
        tileFace.setTranslateX(0);
        tileFace.setTranslateY(0);


        Text numText = new Text(String.valueOf(tile.getNumber()));
        numText.setBoundsType(TextBoundsType.VISUAL);
        numText.setStyle("-fx-font-size: 50pt");
        numText.setFill(tile.getColor());
        numText.setTranslateY(-15);
        setAlignment(numText, Pos.CENTER);
        getChildren().addAll(tileFace, numText);

        } else {

            ImageView imageToSet = new ImageView();
            imageToSet.setPreserveRatio(true);
            imageToSet.setFitHeight(TILE_HEIGHT);
            if (tile.getColor() == Color.RED)
                imageToSet.setImage(Images.jokerRed);
            else if (tile.getColor() == Color.BLACK)
                imageToSet.setImage(Images.jokerBlack);
            else 
                System.out.println("A Joker with a given colors does not exist");

        getChildren().add(imageToSet);


        }


    }

    private boolean isInRackBounds(double x, double y) {

        if (x > (RackGUI.RACK_X) && x < (RackGUI.RACK_X + RackGUI.RACK_WIDTH)) {
            if (y > (RackGUI.RACK_Y) && y < (RackGUI.RACK_Y + RackGUI.RACK_HEIGHT)) {
                return true;
            }
        }

        return false;

    }

    private boolean isInBoardBounds(double x, double y) {

        if (x > (GameboardGUI.BOARD_X) && x < (GameboardGUI.BOARD_X + GameboardGUI.BOARD_WIDTH)) {
            if (y > (GameboardGUI.BOARD_Y) && y < (GameboardGUI.BOARD_Y + GameboardGUI.BOARD_HEIGHT)) {
                return true;
            }
        }

        return false;
    }

    public void addToBoard() {
        this.addedToBoard = true;
    }

    double getCurrentMouseX() {
        return mouseX;
    }

    double getCurrentMouseY() {
        return mouseY;
    }

    double getXPos() {
        return xPos;
    }

    double getYPos() {
        return yPos;
    }

    void setXPos(double xPos) {
        this.xPos = xPos;
    }

    void setYPos(double yPos){
        this.yPos = yPos;
    }

    int getColToSnap() {
        return colToSnap;
    }

    int getRowToSnap() {
        return rowToSnap;
    }

    public int getPrevColBoard() {
        return prevColBoard;
    }

    public int getPrevRowBoard() {
        return prevRowBoard;
    }

    public int getPrevColRack() {
        return prevColRack;
    }

    public int getPrevRowRack() {
        return prevRowRack;
    }
    public boolean isAddedToBoard() {
        return addedToBoard;
    }
    public void setPrevColBoard(int col) {
        this.prevColBoard = col;
    } 
    public void setPrevRowBoard(int row) {
        this.prevRowBoard = row;
    }

    public void setPrevBoardCoords(int col, int row) {
        setPrevColBoard(col);
        setPrevRowBoard(row);
    }


}

