package com.rummikub.gui;

import javafx.scene.control.Button;

class HoverButton extends Button {

    /**
     * determines whether the cursor has entered a button area, button styling
     */
    private boolean mouseEntered = false;
    private String defaultButtonStyle = "-fx-font-size: 60; -fx-padding: 0;";
    private String hoveredStyle = "-fx-font-size: 70; -fx-padding: 0; -fx-text-fill: gray;";
    HoverButton(String text) {

        setText(text);
        setStyle(defaultButtonStyle);
        setBackground(null);
        

        setOnMouseEntered(ev -> {
            System.out.println("as");
            mouseEntered = true;
            buttonHoverEffect(); 

        });
        setOnMouseExited(ev -> {

            mouseEntered = false;
            buttonHoverEffect();

        });

    }


    private void buttonHoverEffect() {
        
        if (mouseEntered) {
            
            setStyle(hoveredStyle);

        } else setStyle(defaultButtonStyle);

    }

}
