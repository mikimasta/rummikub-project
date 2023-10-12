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

    TileGUI(byte number, Color color) {

        ImageView tileFace = new ImageView(Images.tileFace);
        tileFace.setPreserveRatio(true);
        tileFace.setFitHeight(150);
        double tileWidth = tileFace.getLayoutBounds().getWidth();
        tileFace.setTranslateX(0);
        tileFace.setTranslateY(0);
        setMinSize(tileWidth, 150);
        setMaxSize(tileWidth, 150);
        setPrefSize(tileWidth, 150);




        Text numText = new Text(String.valueOf(number));
        numText.setBoundsType(TextBoundsType.VISUAL);
        numText.setStyle("-fx-font-size: 60pt");
        numText.setFill(color);
        numText.setTranslateY(-20);
        setAlignment(numText, Pos.CENTER);
        


        getChildren().addAll(tileFace, numText);




    }

    /**
     * used to construct a joker
     */
    TileGUI(Color color) {

        Image imageToSet;
        if (color == Color.RED)
            imageToSet = Images.jokerRed;
        else
            imageToSet = Images.jokerBlack;

        setBackground(new Background(new BackgroundImage(imageToSet, null, null, null, null)));
    }

}
