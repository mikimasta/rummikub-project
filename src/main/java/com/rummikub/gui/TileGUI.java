package com.rummikub.gui;

import javafx.scene.layout.BackgroundImage;
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

    static {

        TILE_FACE = new ImageView(Images.tileFace);
        TILE_FACE.setPreserveRatio(true);
        TILE_FACE.setFitHeight(TILE_HEIGHT);
        TILE_WIDTH = TILE_FACE.getBoundsInLocal().getWidth();

    }


    TileGUI(byte number, Color color) {

        setMinSize(TILE_WIDTH, RackGUI.RACK_HEIGHT / 2);
        setMaxSize(TILE_WIDTH, RackGUI.RACK_HEIGHT / 2);
        setPrefSize(TILE_WIDTH, RackGUI.RACK_HEIGHT / 2);

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

        setMinSize(TILE_WIDTH, RackGUI.RACK_HEIGHT / 2);
        setMaxSize(TILE_WIDTH, RackGUI.RACK_HEIGHT / 2);
        setPrefSize(TILE_WIDTH, RackGUI.RACK_HEIGHT / 2);

        ImageView imageToSet = new ImageView();
        imageToSet.setPreserveRatio(true);
        imageToSet.setFitHeight(TILE_HEIGHT);
        if (color == Color.RED)
            imageToSet.setImage(Images.jokerRed);
        else
            imageToSet.setImage(Images.jokerBlack);

        getChildren().add(imageToSet);
    }

    void add(TileGUI tile) {
        
    }



}
