package com.rummikub.gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

class RackGUI extends GridPane {

    RackGUI() {


        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
        setLayoutX(640);
        setLayoutY(920);
        setMaxWidth(800);
        setHgap(5);
        setVgap(20);


    }

     
}
