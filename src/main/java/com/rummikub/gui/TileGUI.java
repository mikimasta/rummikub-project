package com.rummikub.gui;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import javax.lang.model.util.ElementScanner6;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;



class TileGUI extends StackPane {

    static final double TILE_WIDTH;
    static final double TILE_HEIGHT = RackGUI.RACK_HEIGHT / 2;
    static final ImageView TILE_FACE;

    private double mouseX;
    private double mouseY;

    private double xPos;
    private double yPos;

    private int colToSnap;
    private int rowToSnap;

    static {

        TILE_FACE = new ImageView(Images.tileFace);
        TILE_FACE.setPreserveRatio(true);
        TILE_FACE.setFitHeight(TILE_HEIGHT);
        TILE_WIDTH = TILE_FACE.getBoundsInLocal().getWidth();

    }

    private TileGUI() {

        setMinSize(TILE_WIDTH, RackGUI.RACK_HEIGHT / 2);
        setMaxSize(TILE_WIDTH, RackGUI.RACK_HEIGHT / 2);
        setPrefSize(TILE_WIDTH, RackGUI.RACK_HEIGHT / 2);


        setOnMousePressed(e -> {
            mouseX = e.getSceneX() - getTranslateX();
            mouseY = e.getSceneY() - getTranslateY();
            toFront();

        });

        setOnMouseDragged(ev -> {
            setTranslateX(ev.getSceneX() - mouseX);
            setTranslateY(ev.getSceneY() - mouseY);

        });

        setOnMouseReleased(e -> {

            mouseX = e.getSceneX();
            mouseY = e.getSceneY();

            if (isInRackBounds(mouseX, mouseY)) {
                colToSnap = (int) (Math.abs(RackGUI.RACK_X - mouseX + RackGUI.X_OFFSET) / TILE_WIDTH);
                System.out.println(Math.abs(RackGUI.RACK_X - mouseX + RackGUI.X_OFFSET));
                rowToSnap = (int) ((mouseY > (RackGUI.RACK_Y + RackGUI.RACK_HEIGHT / 2)) ? 1 : 0);
                setXPos(colToSnap * TILE_WIDTH + (colToSnap * RackGUI.H_GAP) + RackGUI.X_OFFSET);
                //System.out.println(getXPos() + RackGUI.X_OFFSET);
                setYPos(rowToSnap * (RackGUI.RACK_HEIGHT / 2));
            }

            /*
            System.out.println(rowToSnap);
            System.out.println(colToSnap);
            System.out.println(TILE_WIDTH);
            */


            RackGUI.getInstance().update(); 

        });

    }


    TileGUI(byte number, Color color) {

        this(); 

        ImageView tileFace = new ImageView(Images.tileFace);
        tileFace.setPreserveRatio(true);
        tileFace.setFitHeight(TILE_HEIGHT);
        tileFace.setTranslateX(0);
        tileFace.setTranslateY(0);


        Text numText = new Text(String.valueOf(number));
        numText.setBoundsType(TextBoundsType.VISUAL);
        numText.setStyle("-fx-font-size: 50pt");
        numText.setFill(color);
        numText.setTranslateY(-15);
        setAlignment(numText, Pos.CENTER);

        getChildren().addAll(tileFace, numText);

    }

    /**
     * used to construct a joker
     */
    TileGUI(Color color) {

        this();

        ImageView imageToSet = new ImageView();
        imageToSet.setPreserveRatio(true);
        imageToSet.setFitHeight(TILE_HEIGHT);
        if (color == Color.RED)
            imageToSet.setImage(Images.jokerRed);
        else if (color == Color.BLACK)
            imageToSet.setImage(Images.jokerBlack);
        else 
            System.out.println("A Joker with a given colors does not exist");

        getChildren().add(imageToSet);
    }

    private boolean isInRackBounds(double x, double y) {

        if (x > (RackGUI.RACK_X) && x < (RackGUI.RACK_X + RackGUI.RACK_WIDTH)) {
            if (y > (RackGUI.RACK_Y) && y < (RackGUI.RACK_Y + RackGUI.RACK_HEIGHT)) {
                return true;
            }
        }

        return false;

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

}
