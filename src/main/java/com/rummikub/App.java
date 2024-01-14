package com.rummikub;

import com.rummikub.gui.Rummikub;
import com.rummikub.game.Game;
import com.rummikub.movegen.MoveGenerator;

/**
 * Acts as an entry point to the Application. <br>
 * This way we avoid potential .jar issues when building artifacts.
 */
public class App {

    public static void main(String[] args) {

        //Rummikub.main(args);
        MoveGenerator.main(args);

    }

}
