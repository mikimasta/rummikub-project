package com.rummikub.gui;


import com.rummikub.game.Game;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.List;
import com.rummikub.game.Player;

class PlayersGUI extends GridPane {


    private static final double V_GAP = 200;

    PlayersGUI(List<Player> players) {

        setLayoutY(100);
        setLayoutX(50);

        int id = 0;

        for (Player p : players) {


            Text pText = new Text(p.getName());
            pText.setStyle("-fx-font-size: 50pt");
            pText.setTranslateX(0);
            pText.setTranslateY(id * V_GAP);
            
            id++;

            getChildren().add(pText);
        }

    }


    


}
