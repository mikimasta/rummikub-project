package com.rummikub.gui;

import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;



class TileGUI extends StackPane {

    static final double TILE_WIDTH;
    static final double TILE_HEIGHT = RackGUI.RACK_HEIGHT / 2;
    static final ImageView TILE_FACE;
    
    private double currentMouseX;
    private double currentMouseY;

    private double mouseX;
    private double mouseY;

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

        });

        setOnMouseDragged(ev -> {
            setTranslateX(ev.getSceneX() - mouseX);
            setTranslateY(ev.getSceneY() - mouseY);

        });

        setOnMouseReleased(e -> {

            currentMouseX = e.getSceneX();
            currentMouseY = e.getSceneY();

            int colToSnap = (int) (Math.abs((RackGUI.RACK_X - currentMouseX))  / TILE_WIDTH);
            int rowToSnap = (int) ((currentMouseY > (RackGUI.RACK_Y + RackGUI.RACK_HEIGHT / 2)) ? 1 : 0);

            System.out.println(rowToSnap);
            System.out.println(colToSnap);
            System.out.println(TILE_WIDTH);

            GridPane grid = (GridPane) getParent();
            

            grid.add(this, colToSnap, rowToSnap);

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
        else
            imageToSet.setImage(Images.jokerBlack);

        getChildren().add(imageToSet);
    }

    double getCurrentMouseX() {
        return currentMouseX;
    }

    double getCurrentMouseY() {
        return currentMouseY;
    }

}
