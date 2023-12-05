package com.rummikub.gui;

import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class TimerGUI extends GridPane {

    Text timerTxt;
    
    public TimerGUI(){

        setLayoutY(20);
        setLayoutX(400);
        getStylesheets().add("styling.css");

        timerTxt = new Text("00:00");
        timerTxt.setStyle("-fx-font-size: 50;");
        getChildren().add(timerTxt);

        Timer myTimer = new Timer();
        myTimer.scheduleAtFixedRate(new TimerTask(){

            @Override
            public void run() {
              increaseBySecond();
            }
          }, 1000, 1000);

    }

    public void resetTimer(){
        timerTxt.setText("00:00");
    }

    private void increaseBySecond(){
        String s = timerTxt.getText();
        String[] ms = s.split(":");

        if ( ms[1].equals("59") ) {
            ms[0] = String.format("%02d", Integer.parseInt(ms[0])+1);
            ms[1] = "00"; 
        } else {
            ms[1] = String.format("%02d", Integer.parseInt(ms[1])+1);
        }

        String done = ms[0] + ":" + ms[1];

        timerTxt.setText(done);
    }

}
