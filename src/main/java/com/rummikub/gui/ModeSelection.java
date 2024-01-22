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
    private HoverButton AIvsAI;
    private HoverButton back;
    private HoverButton start;
    private HoverButton startAI;
    private HoverButton startAIvsAI;

    ModeSelection() {
       
        setBackground(new Background(new BackgroundFill(Color.web("#4A6B3C"), null, null)));
        getStylesheets().add("styling.css");

        start = new HoverButton("Start");
        start.setLayoutX(Rummikub.xCenter + 300);
        start.setLayoutY(Rummikub.yCenter - 50);
        start.setOnAction(e -> {

            Rummikub.gameWindow.getScene().setRoot(new GameScreen());
            Game.getInstance();
        });

        startAI = new HoverButton("AI start");
        startAI.setLayoutX(Rummikub.xCenter + 500);
        startAI.setLayoutY(Rummikub.yCenter + 50);
        startAI.setOnAction(e -> {

            Rummikub.gameWindow.getScene().setRoot(new AIGameScreen());
            Game.getInstance();
        });

        startAIvsAI = new HoverButton("AIvsAI start");
        startAIvsAI.setLayoutX(Rummikub.xCenter + 600);
        startAIvsAI.setLayoutY(Rummikub.yCenter + 150);
        startAIvsAI.setOnAction(e -> {

            Rummikub.gameWindow.getScene().setRoot(new AIGameScreen());
            Game.getInstance();
        });

        vsHuman = new HoverButton("Player vs. Player");
        vsHuman.setLayoutX(Rummikub.xCenter - 400);
        vsHuman.setLayoutY(Rummikub.yCenter - 50);
        vsHuman.setOnAction(e -> {

            vsHuman.lock();

            ComboBox<Integer> comboBox = new ComboBox<>(FXCollections.observableArrayList(2, 3, 4));
            comboBox.setValue(0);
            comboBox.setLayoutX(Rummikub.xCenter + 180);
            comboBox.setLayoutY(Rummikub.yCenter - 50);
            getChildren().add(comboBox);

            comboBox.setOnAction(ev -> {
                if (comboBox.getSelectionModel().getSelectedItem() != 0) {
                    Game.NUM_OF_PLAYERS = (byte) (int) comboBox.getValue();
                    Game.GAME_MODE = 1;
                    if (!getChildren().contains(start)) 
                        getChildren().add(start);
                }
            });

        });

        vsAI = new HoverButton("Player vs. AI");
        vsAI.setLayoutX(Rummikub.xCenter - 400);
        vsAI.setLayoutY(Rummikub.yCenter + 50);
        vsAI.setOnAction(e -> {

            vsAI.lock();

            ComboBox<Integer> AIcomboBox = new ComboBox<>(FXCollections.observableArrayList(2, 3, 4));
            AIcomboBox.setValue(0);
            AIcomboBox.setLayoutX(Rummikub.xCenter + 180);
            AIcomboBox.setLayoutY(Rummikub.yCenter + 70);
            getChildren().add(AIcomboBox);

            ComboBox<String> AIcomboBoxType = new ComboBox<>(FXCollections.observableArrayList("Greedy", "MCTS+NN", "Tree Search"));
            AIcomboBoxType.setValue("Greedy");
            AIcomboBoxType.setLayoutX(Rummikub.xCenter + 300);
            AIcomboBoxType.setLayoutY(Rummikub.yCenter + 70);
            getChildren().add(AIcomboBoxType);

            AIcomboBoxType.setOnAction(ev -> {
                if (AIcomboBoxType.getSelectionModel().getSelectedItem() == "Greedy") {
                    Game.NUM_OF_PLAYERS = (byte) (int) AIcomboBox.getValue();
                    Game.AI1type = 0;
                    //Game.AI2type = -1;
                    Game.GAME_MODE = 2;
                    if (!getChildren().contains(startAI))
                        getChildren().add(startAI);
                }
                if (AIcomboBoxType.getSelectionModel().getSelectedItem() == "MCTS+NN") {
                    Game.NUM_OF_PLAYERS = (byte) (int) AIcomboBox.getValue();
                    Game.AI1type = 1;
                    Game.GAME_MODE = 2;
                    if (!getChildren().contains(startAI))
                        getChildren().add(startAI);
                }if (AIcomboBoxType.getSelectionModel().getSelectedItem() == "Tree Search") {
                    Game.NUM_OF_PLAYERS = (byte) (int) AIcomboBox.getValue();
                    Game.AI1type = 2;
                    Game.GAME_MODE = 2;
                    if (!getChildren().contains(startAI))
                        getChildren().add(startAI);
                }
            });
        });

        AIvsAI = new HoverButton("AI vs. AI");
        AIvsAI.setLayoutX(Rummikub.xCenter - 400);
        AIvsAI.setLayoutY(Rummikub.yCenter + 150);
        AIvsAI.setOnAction(e -> {

            AIvsAI.lock();
            ComboBox<String> AIcomboBoxType1 = new ComboBox<>(FXCollections.observableArrayList("Greedy vs. Greedy", "MCTS+NN vs. Greedy", "Tree Search vs. Greedy", "MCTS+NN vs. MCTS+NN", "MCTS+NN vs. Tree Search", "Tree Search vs. Tree Search"));
            AIcomboBoxType1.setValue("Greedy vs. Greedy");
            AIcomboBoxType1.setLayoutX(Rummikub.xCenter + 180);
            AIcomboBoxType1.setLayoutY(Rummikub.yCenter + 180);
            getChildren().add(AIcomboBoxType1);

            AIcomboBoxType1.setOnAction(ev -> {
                if (AIcomboBoxType1.getSelectionModel().getSelectedItem() == "Greedy vs. Greedy") {
                    Game.NUM_OF_PLAYERS = 2;
                    Game.AI1type = 0;
                    Game.AI2type = 0;
                    Game.GAME_MODE = 3;
                    if (!getChildren().contains(startAIvsAI))
                        getChildren().add(startAIvsAI);
                }
                if (AIcomboBoxType1.getSelectionModel().getSelectedItem() == "MCTS+NN vs. Greedy") {
                    Game.NUM_OF_PLAYERS = 2;
                    Game.AI1type = 1;
                    Game.AI2type = 0;
                    Game.GAME_MODE = 3;
                    if (!getChildren().contains(startAIvsAI))
                        getChildren().add(startAIvsAI);
                }if (AIcomboBoxType1.getSelectionModel().getSelectedItem() == "Tree Search vs. Greedy") {
                    Game.NUM_OF_PLAYERS = 2;
                    Game.AI1type = 2;
                    Game.AI2type = 0;
                    Game.GAME_MODE = 3;
                    if (!getChildren().contains(startAIvsAI))
                        getChildren().add(startAIvsAI);
                }
                if (AIcomboBoxType1.getSelectionModel().getSelectedItem() == "MCTS+NN vs. MCTS+NN") {
                    Game.NUM_OF_PLAYERS = 2;
                    Game.AI1type = 1;
                    Game.AI2type = 1;
                    Game.GAME_MODE = 3;
                    if (!getChildren().contains(startAIvsAI))
                        getChildren().add(startAIvsAI);
                }
                if (AIcomboBoxType1.getSelectionModel().getSelectedItem() == "MCTS+NN vs. Tree Search") {
                    Game.NUM_OF_PLAYERS = 2;
                    Game.AI1type = 1;
                    Game.AI2type = 2;
                    Game.GAME_MODE = 3;
                    if (!getChildren().contains(startAIvsAI))
                        getChildren().add(startAIvsAI);
                }if (AIcomboBoxType1.getSelectionModel().getSelectedItem() == "Tree Search vs. Tree Search") {
                    Game.NUM_OF_PLAYERS = 2;
                    Game.AI1type = 2;
                    Game.AI2type = 2;
                    Game.GAME_MODE = 3;
                    if (!getChildren().contains(startAIvsAI))
                        getChildren().add(startAIvsAI);
                }
            });
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

        getChildren().addAll(rummikubLogoIV,vsHuman, vsAI, AIvsAI, back);
    }

}
