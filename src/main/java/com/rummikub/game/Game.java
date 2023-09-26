package com.rummikub.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is a memory representation of the game state. It executes and validates player moves.<br>
 * Is responsible for the flow of the game.
 */
class Game {

    //im unsure about how to do the board, like an array of tile arrays to show each run  or what?
    private List<Player> players;
    private List<Tile> board;
    private List<Tile> pool;

    /**
     * constructs a Game instance with a given number of player. <br>
     * creates the board, the pool and initializes and deals tiles.
     * @param numPlayers  number of players for the game
     */
     Game(int numPlayers) {

        players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player("Player " + i));
        }

        board = new ArrayList<>();
        pool = new ArrayList<>();

        initializeTiles();
        dealInitialTiles();
    }

    /**  
     *  this method initlizaes all 104 tiles, two runs of each  
     *  two runs up from 1-13 in each color (red, orange black or blue)
     *  in addition to two jokers, and adds them to the pool.
    */
    private void initializeTiles() {
        String[] colors = {"Red", "Orange", "Black", "Blue"};
        for (String color : colors) {
         for (byte i = 0; i < 2; i++) {
            for (byte number = 1; number <= 13; number++) {
                pool.add(new Tile(color, number));
            }
        }
        }
        //the interger 0 will be used in regard to a joker
        //two jokers are added into our game
        pool.add(new Tile("Joker", (byte) 0));
        pool.add(new Tile("Joker", (byte) 0));
    }

    /**
     * this method first shuffles the pool and then deals each player 14 tile
    */
    private void dealInitialTiles() {
        Collections.shuffle(pool);
        for (Player player : players) {
            for (int i = 0; i < 14; i++) {
                Tile drawnTile = pool.remove(0);
                player.drawTile(drawnTile);
            }
        }
    }

    /**
     * this method checks the state of the entire board after a move has been made.
     * @return  true if the board state complies with the rules, false otherwise.
     */
    private boolean isValidBoard() {
        // to do
        return true;
    }


    private void makeMove(Player player, List<Tile> move) {
    }

    private boolean isGameOver() {
         for (Player player : players) {
            if (player.getHand() == null) {
                return true;
            }
        }
        return false;
    }

    private void playGame() {
        while (!isGameOver()) {
           //make moves here
        }
    }
}
