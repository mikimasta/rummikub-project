package com.rummikub.game;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class is a memory representation of the game state. It executes and
 * validates player moves.<br>
 * Is responsible for the flow of the game.
 */
public class Game {

    // im unsure about how to do the board, like an array of tile arrays to show
    // each run or what?
    private List<Player> players;
    private Tile[][] board;
    private List<Tile> pool;
    private static Player currentPlayer;

    public static final byte GRID_ROWS = 1;
    public static final byte GRID_COLS = 13;

    /**
     * constructs a Game instance with a given number of player. <br>
     * creates the board, the pool and initializes and deals tiles.
     * 
     * @param numPlayers number of players for the game
     */
    Game(byte numPlayers) {

        if (numPlayers > 4 || numPlayers < 2)
            throw new IllegalArgumentException("A game can have a maximum of 4 players and a minimum of 2 players!");

        board = new Tile[GRID_ROWS][GRID_COLS];

        for (int row = 0; row < GRID_ROWS; ++row) {
            for (int col = 0; col < GRID_COLS; ++col) {
                board[row][col] = new Tile("lol", (byte) 0);
            }
        }
        players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player("Player " + i));
        }

        byte firstPlayer = (byte) (0 + (byte) (Math.random() * numPlayers - 1));
        currentPlayer = players.get(firstPlayer);

        pool = new ArrayList<>();

        initializeTiles();
        dealInitialTiles();
    }

    /**
     * this method initlizaes all 104 tiles, two runs of each
     * two runs up from 1-13 in each color (red, orange black or blue)
     * in addition to two jokers, and adds them to the pool.
     */
    private void initializeTiles() {
        String[] colors = { "Red", "Orange", "Black", "Blue" };
        for (String color : colors) {
            for (byte i = 0; i < 2; i++) {
                for (byte number = 1; number <= 13; number++) {
                    pool.add(new Tile(color, number));
                }
            }
        }
        // the interger 0 will be used in regard to a joker
        // two jokers are added into our game
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
     * @param   gameBoard  the game board
     * @return  true if the board state complies with the rules, false otherwise.
     */
    public boolean isValidBoard(Tile[][] gameBoard) {
        System.out.println( printBoard(gameBoard) );
        ArrayList<Tile> set = new ArrayList<>(); // Create an ArrayList to store number of tiles forming set
        for (int i = 0; i < gameBoard.length; i++){
            for (int y = 0; y < gameBoard[i].length; y++){ 
                Tile tile = gameBoard[i][y];
                if(tile.getNumber() != 0){ // assumed that null tiles have number of 0
                    set.add(tile);
                }
            }
            if (set.size() < 3){ 
                return false; // the block of tiles is too short 
            }else{
                for (int j = 0; j < set.size(); j++) {
                    System.out.println(set.get(j).getNumber() + " " + set.get(j).getColor());
                }
                if(!checkTile(set)) return false; // check if given tile is either stairs or group, return false if not
            }
        }
        return true; // checked all tiles, all are correct
    }

    // check if given tile is either group or stairs, i.e. is it legal?
    boolean checkTile(ArrayList<Tile> set) {
        return checkIfGroup(set) || checkIfStairs(set);
    }

    // check if they all have the same number and different colors, i.e. they are a group
    boolean checkIfGroup(ArrayList<Tile> set){

        // up to 4 tiles (thats how many colors there are)
        if (set.size() > 4) return false;

        // check if same number
        byte numOfFirst = set.get(0).getNumber();
        for (int i = 1; i < set.size(); i++) {
            byte numTmp = set.get(i).getNumber();
            if (numOfFirst != numTmp) return false;
        }

        // check if different colors
        int[] colorsCount = {0, 0, 0, 0};
        for (int i = 0; i < set.size(); i++) {
            switch(set.get(i).getColor()) {
                case "blue":
                    colorsCount[0] += 1;
                    break;
                case "red":
                    colorsCount[1] += 1;
                    break;
                case "green":
                    colorsCount[2] += 1;
                    break;
                case "black":
                    colorsCount[3] += 1;
                    break;
                default:
                    System.out.println("given color wasn't expected "+set.get(i).getColor());
                    // raise RunTimeError("given color wasn't expected "+set.get(i).getColor());
            }
        }
        for (int i = 0; i < colorsCount.length; i++) { // check if there is a color with more than 1 representative
            if (colorsCount[i] > 1 ) {
                return false;
            }
        }
        return true;
    }
    
    // check if the set is stairs, i.e. same color, incrementing blocks
    boolean checkIfStairs(ArrayList<Tile> set){

        // check if all same color
        String colorOfFirst = set.get(0).getColor();
        for (int i = 1; i < set.size(); i++) {
            if (!colorOfFirst.equals(set.get(i).getColor())) {
                return false;
            }
        }

        // check if incrementing
        byte numOfFirst = set.get(0).getNumber();
        for (int i = 0; i < set.size(); i++) {
            byte numTmp = set.get(i).getNumber();
            if (numTmp - i != numOfFirst) {
                return false;
            }
        }
        return true;
    }

    private void placeTile(Tile tile) {
        assert tile != null;
    }

    private boolean isGameOver() {
         for (Player player : players) {
            if (player.getHand() == null) {
                return true;
            }
        }
       return false;
    }

    public void playGame() {

        dealInitialTiles();

        while (!isGameOver()) {

           currentPlayer.startMove();
           while (!(currentPlayer.isMoveFinished())) {
               
        
           }
           isValidBoard(board);
           
           nextPlayer();
        }
    }

    void nextPlayer() {
        try {
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
        } catch (IndexOutOfBoundsException e) {
            currentPlayer = players.get(0);
        }
    }

    public Tile[][] getBoard() {
        return board;
    }

    static String printBoard(Tile[][] board) {
        String boardString = "";
        for (int row = 0; row < GRID_ROWS; ++row) {
            boardString += "\n";
            for (int col = 0; col < GRID_COLS; ++col) {
                boardString += board[row][col].getNumber() + " ";
            }
        }
        return boardString;
    }

    public static void main(String[] args) {
    
        Game game = new Game((byte) 4);
        System.out.println(currentPlayer + " starts the game.");
        System.out.print(printBoard(game.getBoard()));

    }
    
}
