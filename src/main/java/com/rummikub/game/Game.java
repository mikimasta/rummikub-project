package com.rummikub.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    //im unsure about how to do the board, like an array of tile arrays to show each run  or what?
    private List<Player> players;
    private List<Tile> board;
    private List<Tile> pile;

    public Game(int numPlayers) {

        players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player("Player " + i));
        }

        board = new ArrayList<>();
        pile = new ArrayList<>();

        initializeTiles();
        dealInitialTiles();
    }

    /*  this method initlizaes all 104 tiles, two runs of each  
     *  two runs up from 1-13 in each color (red, orange black or blue)
     * in addition to two jokers.
    */

    private void initializeTiles() {
        String[] colors = {"Red", "Orange", "Black", "Blue"};
        for (String color : colors) {
         for(int i = 0; i < 2; i++){
            for (int number = 1; number <= 13; number++) {
                pile.add(new Tile(color, number));
            }
        }
        }
        //the interger 0 will be used in regard to a joker
        //two jokers are added into our game
        pile.add(new Tile("Joker", 0));
        pile.add(new Tile("Joker", 0));
    }

    /* this method first shuffles the pile and then deal each player 14 tiles */
    private void dealInitialTiles() {
        Collections.shuffle(pile);
        for (Player player : players) {
            for (int i = 0; i < 14; i++) {
                Tile drawnTile = pile.remove(0);
                player.drawTile(drawnTile);
            }
        }
    }

    public boolean isValidMove(List<Tile> move) {
        //add board stuff here
        return false;
    }

    public void makeMove(Player player, List<Tile> move) {
        if (isValidMove(move)) {
            // add move stuff here
        }
    }

    public boolean isGameOver() {
         for (Player player : players) {
            if(player.getHand() == null){
                return true;
            }
        }
        return false;
    }

    public void playGame() {
        while (!isGameOver()) {
           //make moves here
        }
    }
}
