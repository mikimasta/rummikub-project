package com.rummikub.gui;

import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class EndGameScreen extends Pane {
    private String hoveredStyle = "-fx-font-size: 70; -fx-padding: 0; -fx-fill: #4A6B3C;";

    public EndGameScreen(String player) {

        setBackground(new Background(new BackgroundFill(Color.web("#83A475"), null, null)));
        getStylesheets().add("styling.css");

        Text winner = new Text(player + " has won the game!!!");
        winner.setStyle(hoveredStyle);
        winner.setLayoutX(Rummikub.xCenter - 400);
        winner.setLayoutY(Rummikub.yCenter + 200);


        ImageView rummikubLogoIV = new ImageView(Images.endScreen);
        rummikubLogoIV.setX(Rummikub.WIDTH/2 - 600);
        rummikubLogoIV.setY(0);
        rummikubLogoIV.setPreserveRatio(true);
        rummikubLogoIV.setFitWidth(1200);

        ImageView quitImage = new ImageView(Images.quit);
        quitImage.setFitWidth(300);
        quitImage.setFitHeight(150);
        HoverButton exit = new HoverButton("");
        exit.setGraphic(quitImage);
        exit.setLayoutX(Rummikub.xCenter - 150);
        exit.setLayoutY(Rummikub.yCenter + 300);
        exit.setOnAction(e -> {
            System.exit(0);
        });
        getChildren().addAll(rummikubLogoIV, exit, winner);
    }
}
