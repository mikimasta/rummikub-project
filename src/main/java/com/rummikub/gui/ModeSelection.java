package com.rummikub.gui;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.control.ComboBox;

import com.rummikub.game.Game;

import javafx.collections.FXCollections;

import javafx.scene.image.ImageView;

class ModeSelection extends Pane {

    private HoverButton vsHuman;
    private HoverButton vsAI;
    private HoverButton back;
    private HoverButton start;
    private HoverButton startAI;

    ModeSelection() {
       
        setBackground(new Background(new BackgroundFill(Color.web("#4A6B3C"), null, null)));
        getStylesheets().add("styling.css");

        start = new HoverButton("Start");
        start.setLayoutX(1450);
        start.setLayoutY(500);
        start.setOnAction(e -> {

            Rummikub.gameWindow.getScene().setRoot(new GameScreen());
            Game.getInstance();
        });

        startAI = new HoverButton("AIStart");
        startAI.setLayoutX(1450);
        start.setLayoutY(300);
        startAI.setOnAction(e -> {

            Rummikub.gameWindow.getScene().setRoot(new AIGameScreen());
            Game.getInstance();
        });

        vsHuman = new HoverButton("Player vs. Player");
        vsHuman.setLayoutX(Rummikub.xCenter - 200);
        vsHuman.setLayoutY(Rummikub.yCenter - 50);
        vsHuman.setOnAction(e -> {

            vsHuman.lock();

            ComboBox<Integer> comboBox = new ComboBox<>(FXCollections.observableArrayList(2, 3, 4));
            comboBox.setValue(0);
            comboBox.setLayoutX(1350);
            comboBox.setLayoutY(510);
            getChildren().add(comboBox);

            comboBox.setOnAction(ev -> {
                if (comboBox.getSelectionModel().getSelectedItem() != 0) {
                    Game.NUM_OF_PLAYERS = (byte) (int) comboBox.getValue();

                    if (!getChildren().contains(start)) 
                        getChildren().add(start);
                }
            });

        });

        vsAI = new HoverButton("Player vs. AI");
        vsAI.setLayoutX(Rummikub.xCenter - 200);
        vsAI.setLayoutY(Rummikub.yCenter + 50);

        vsAI.setOnAction(e -> {

            vsHuman.lock();

            ComboBox<Integer> AIcomboBox = new ComboBox<>(FXCollections.observableArrayList(2, 3, 4));
            AIcomboBox.setValue(0);
            AIcomboBox.setLayoutX(1350);
            AIcomboBox.setLayoutY(610);
            getChildren().add(AIcomboBox);

            AIcomboBox.setOnAction(ev -> {
                if (AIcomboBox.getSelectionModel().getSelectedItem() != 0) {
                    Game.NUM_OF_PLAYERS = (byte) (int) AIcomboBox.getValue();
                    if (!getChildren().contains(startAI)) 
                        getChildren().add(startAI);
                }
            });
            //Game.NUM_OF_PLAYERS = (byte)2;
        });

        ImageView rummikubLogoIV = new ImageView(Images.rummikubLogo);
        rummikubLogoIV.setX(400);
        rummikubLogoIV.setY(100);
        rummikubLogoIV.setPreserveRatio(true);
        rummikubLogoIV.setFitWidth(600);

        back = new HoverButton("Back");
        back.setLayoutX(100);
        back.setLayoutY(1000);
        back.setOnAction(e -> {
            Rummikub.gameWindow.getScene().setRoot(new MainMenu());
        });

        getChildren().addAll(rummikubLogoIV,vsHuman, vsAI, back);
    }

}
