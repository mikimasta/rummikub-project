package com.rummikub.gui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * This class represents the main menu screen of the game.<br>
 * Extends the {@link Pane} class, allowing for use as a root node.
 */
public class MainMenu extends Pane {

    /**
     * Creates a main menu instance, setting up all the buttons and environment
     */
    MainMenu() {

        // start button
        //
        HoverButton start = new HoverButton("Start");
        start.setLayoutX(Rummikub.xCenter - 200);
        start.setLayoutY(Rummikub.yCenter - 50);
        start.setOnAction(e -> {

            Rummikub.gameWindow.getScene().setRoot(new ModeSelection());

        });
        
        // settings button
        HoverButton settings = new HoverButton("Settings");
        settings.setLayoutX(Rummikub.xCenter - 200);
        settings.setLayoutY(Rummikub.yCenter + 25);
        
        // rules button
        HoverButton rules = new HoverButton("Rules");
        rules.setLayoutX(Rummikub.xCenter - 200);
        rules.setLayoutY(Rummikub.yCenter + 100);

        // exit button
        HoverButton exit = new HoverButton("Quit");
        exit.setLayoutX(Rummikub.xCenter - 200);
        exit.setLayoutY(Rummikub.yCenter + 175);
        exit.setOnAction(e -> {
            System.exit(0);
        });

        //Color.web("#4C516D" - blue shade)
        setBackground(new Background(new BackgroundFill(Color.web("#4A6B3C"), null, null)));
        getStylesheets().add("styling.css");

        ImageView rummikubLogoIV = new ImageView(Images.rummikubLogo);
        rummikubLogoIV.setX(400);
        rummikubLogoIV.setY(100);
        rummikubLogoIV.setPreserveRatio(true);
        rummikubLogoIV.setFitWidth(600);

        getChildren().addAll(rummikubLogoIV, start, settings, exit, rules);

    }

}
