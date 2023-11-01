package com.rummikub.gui;


import com.rummikub.game.Game;
import com.rummikub.game.Player;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.List;

class PlayersGUI extends GridPane {


    private static final double V_GAP = 200;

    private String highlight = "-fx-fill: #D5FBC5; -fx-font-size: 60;";
    private String normal = "-fx-fill: black; -fx-font-size: 50";

    PlayersGUI(List<Player> players) {

        setLayoutY(100);
        setLayoutX(50);
        getStylesheets().add("styling.css");

        int id = 0;

        for (Player p : players) {


            Text pText = new Text(p.getName());
            pText.setStyle("-fx-font-size: 50;");
            pText.setTranslateX(0);
            pText.setTranslateY(id * V_GAP);
            
            id++;

            getChildren().add(pText);
        }

        update();
    }

    public void update() {

        for (int i = 0; i < getChildren().size(); ++i) {
            if (Game.getInstance().getPlayers().indexOf(Game.getInstance().currentPlayer) == i) {
                getChildren().get(i).setStyle(highlight);

            } else  {
                getChildren().get(i).setStyle(normal);
            }

        }

    }

}
