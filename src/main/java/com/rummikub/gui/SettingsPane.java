package com.rummikub.gui;

import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.lang.reflect.Method;

public class SettingsPane extends Pane {

    public SettingsPane() {

        setBackground(new Background(new BackgroundFill(Color.web("#4A6B3C"), null, null)));
        getStylesheets().add("styling.css");

        Slider brightnessSlider = new Slider();
        brightnessSlider.setPrefSize(800,3);
        brightnessSlider.setMin(0);
        brightnessSlider.setMax(100);
        brightnessSlider.setValue(50);
        brightnessSlider.setLayoutX(Rummikub.WIDTH /2 - 400);
        brightnessSlider.setLayoutY(Rummikub.HEIGHT / 2 -100);

        ImageView sun = new ImageView(Images.sun);
        sun.setX(Rummikub.WIDTH /2 - 550);
        sun.setY(Rummikub.HEIGHT / 2 - 170);
        sun.setPreserveRatio(true);
        sun.setFitWidth(200);


        Slider volumeSlider = new Slider();
        volumeSlider.setPrefSize(800,3);
        volumeSlider.setMin(0);
        volumeSlider.setMax(100);
        volumeSlider.setValue(50);
        volumeSlider.setLayoutX(Rummikub.WIDTH / 2 - 400);
        volumeSlider.setLayoutY(Rummikub.HEIGHT / 2 + 100);
        ImageView volumen = new ImageView(Images.volumen);
        volumen.setX(Rummikub.WIDTH /2 - 550);
        volumen.setY(Rummikub.HEIGHT / 2 + 45);
        volumen.setPreserveRatio(true);
        volumen.setFitWidth(200);

        brightnessSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double newBrightness = newValue.doubleValue() / 100.0;
        });
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double newVolume = newValue.doubleValue() / 100.0;
        });

        ImageView rummikubLogoIV = new ImageView(Images.rummyCenter);
        rummikubLogoIV.setX(660);
        rummikubLogoIV.setY(100);
        rummikubLogoIV.setPreserveRatio(true);
        rummikubLogoIV.setFitWidth(600);

        HoverButton back = new HoverButton("Back");
        back.setLayoutX(100);
        back.setLayoutY(1000);
        back.setOnAction(e -> {
            Rummikub.gameWindow.getScene().setRoot(new MainMenu());
        });
        getChildren().addAll(rummikubLogoIV,brightnessSlider, volumeSlider, back, sun, volumen);
    }
}