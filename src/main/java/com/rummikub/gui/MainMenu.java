package com.rummikub.gui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.File;
import java.io.IOException;

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
        ImageView startImage = new ImageView(Images.start);
        startImage.setFitWidth(300);
        startImage.setFitHeight(150);
        HoverButton start = new HoverButton("");
        start.setGraphic(startImage);
        start.setLayoutX(Rummikub.xCenter - 350);
        start.setLayoutY(Rummikub.yCenter);
        start.setOnAction(e -> {

            Rummikub.gameWindow.getScene().setRoot(new ModeSelection());

        });
        
        // settings button
        ImageView settingsImage = new ImageView(Images.settings);
        settingsImage.setFitWidth(300);
        settingsImage.setFitHeight(150);
        HoverButton settings = new HoverButton("");
        settings.setGraphic(settingsImage);
        settings.setLayoutX(Rummikub.xCenter - 750);
        settings.setLayoutY(Rummikub.yCenter);
        settings.setOnAction(e -> {

            Rummikub.gameWindow.getScene().setRoot(new SettingsPane());

        });
        
        // rules button
        ImageView rulesImage = new ImageView(Images.rules);
        rulesImage.setFitWidth(300);
        rulesImage.setFitHeight(150);
        HoverButton rules = new HoverButton("");
        rules.setGraphic(rulesImage);
        rules.setLayoutX(Rummikub.xCenter + 50);
        rules.setLayoutY(Rummikub.yCenter);
        rules.setOnAction(e -> {
            try {
                File pdfFile = new File("src/main/resources/RulesRummy.pdf");

                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.out.println("Couldn't find the path");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // exit button
        ImageView quitImage = new ImageView(Images.quit);
        quitImage.setFitWidth(300);
        quitImage.setFitHeight(150);
        HoverButton exit = new HoverButton("");
        exit.setGraphic(quitImage);
        exit.setLayoutX(Rummikub.xCenter + 450);
        exit.setLayoutY(Rummikub.yCenter );
        exit.setOnAction(e -> {
            System.exit(0);
        });

        //Color.web("#4C516D" - blue shade)
        setBackground(new Background(new BackgroundFill(Color.web("#4A6B3C"), null, null)));
        getStylesheets().add("styling.css");

        ImageView rummikubLogoIV = new ImageView(Images.rummyCenter);
        rummikubLogoIV.setX(460);
        rummikubLogoIV.setY(100);
        rummikubLogoIV.setPreserveRatio(true);
        rummikubLogoIV.setFitWidth(1000);

        getChildren().addAll(rummikubLogoIV, start, settings, exit, rules);

    }

}
