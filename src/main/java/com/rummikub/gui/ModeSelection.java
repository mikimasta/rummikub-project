package com.rummikub.gui;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

class ModeSelection extends Pane {

    private HoverButton vsHuman;
    private HoverButton vsAI;
    private HoverButton back;

    ModeSelection() {
       
        setBackground(new Background(new BackgroundFill(Color.web("#4A6B3C"), null, null)));
        getStylesheets().add("styling.css");

        vsHuman = new HoverButton("Player vs. Player");
        vsHuman.setLayoutX(300);
        vsHuman.setLayoutY(400);
        vsHuman.setOnAction(e -> {

        });

        vsAI = new HoverButton("Player vs. AI");

        back = new HoverButton("Back");
        back.setLayoutX(100);
        back.setLayoutY(600);
        back.setOnAction(e -> {
            Rummikub.gameWindow.getScene().setRoot(new MainMenu());
        });


        getChildren().addAll(vsHuman, vsAI, back);
    }

}
