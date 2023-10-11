package com.rummikub.gui;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

class GameScreen extends Pane {

    GameScreen() {

        // sets for the grid pane settings

        setBackground(new Background(new BackgroundFill(Color.web("#4A6B3C"), null, null)));
        getStylesheets().add("styling.css");

        GridPane rack = new GridPane();

        GridPane players = new GridPane();

        GridPane gameBoard = new GridPane();


        getChildren().addAll(rack, players, gameBoard);

    }





















}
