package com.rummikub.game;

import com.rummikub.gui.MainMenu;
import com.rummikub.gui.Tile;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * This class is a memory representation of the game state. It executes and
 * validates player moves.<br>
 * Is responsible for the flow of the game.
 */
public class Game {

    private List<Player> players;
    //private Tile[][] board;
    private List<Tile> pool;
    public Player currentPlayer;

    private static Game instance;


    public static final byte GRID_ROWS = 1;
    public static final byte GRID_COLS = 13;


    public static Game getInstance() {
        if (instance == null)
            instance = new Game(MainMenu.NUM_OF_PLAYERS);
        return instance;
    }

    public static void endGame() {
        instance = null;
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * constructs a Game instance with a given number of player. <br>
     * creates the board, the pool and initializes and deals tiles.
     * 
     * @param numPlayers number of players for the game
     */
    private Game(byte numPlayers) {

        if (numPlayers > 4 || numPlayers < 2)
            throw new IllegalArgumentException("A game can have a maximum of 4 players and a minimum of 2 players!");

        //board = new Tile[GRID_ROWS][GRID_COLS];

        for (int row = 0; row < GRID_ROWS; ++row) {
            for (int col = 0; col < GRID_COLS; ++col) {
                //board[row][col] = new Tile("lol", (byte) 0);
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
        Color[] colors = {Color.RED, Color.BLUE, Color.BLACK, Color.ORANGE };
        for (Color color : colors) {
            for (byte i = 0; i < 2; i++) {
                for (byte number = 1; number <= 13; number++) {
                    pool.add(new Tile(color, number));
                }
            }
        }
        // the interger 0 will be used in regard to a joker
        // two jokers are added into our game
        pool.add(new Tile(Color.RED));
        pool.add(new Tile(Color.BLACK));

        Collections.shuffle(pool);
    }

    /**
     * this method first shuffles the pool and then deals each player 14 tile
     */
    public void dealInitialTiles() {

        for (Player player : players) {
            Tile[][] tempHand = player.getHand();
            for (int i = 0; i < 14; i++) {
                Tile drawnTile = pool.remove(0);
                tempHand[0][i] = drawnTile;

            }
            player.setHand(tempHand);
        }
    }

    /**
     * This method checks the state of the entire board after a move has been made.
     * @param gameBoard the game board
     * @return true if the board state complies with the rules, false otherwise.
     */
    public boolean isValidBoard(Tile[][] gameBoard) {
        // TODO
        return true; // Checked all tiles, all are correct
    }

    // Check if given tile is either group or stairs, i.e., is it legal?
    boolean checkTile(ArrayList<Tile> set) {
        return checkIfGroup(set) || checkIfStairs(set);
    }

    /**
     * Check if they all have the same number and different colors, i.e., they are a group.
     * @param set Set of tiles
     * @return true if the tiles form a group, false otherwise.
     */
    boolean checkIfGroup(ArrayList<Tile> set) {
        // TODO
        return true;
    }

    /**
     * Check if the set is stairs, i.e., same color, incrementing blocks.
     * @param set Set of tiles
     * @return true if the tiles form a run, false otherwise.
     */
    boolean checkIfStairs(ArrayList<Tile> set) {
        //TODO
        return true;
    }

    /**
     * checks 
     * @param currentHand current state of the board after moved was made
     * @param prevHand previous state of the board
     * @return
     */
    public boolean isValidFirstMove(Tile[][] currentHand, Tile[][] prevHand){
        int sumCurrHand = sumPointsOfATile(currentHand);
        int sumPrevHand = sumPointsOfATile(prevHand);

        System.out.println("hey");

        return sumCurrHand - sumPrevHand >= 30;
    }

    
    private int sumPointsOfATile(Tile[][] tile) {
        int sum = 0;

        for (int row = 0; row < tile.length; row++) {
            for (int col = 0; col < tile[row].length; col++) {
                Tile piece = tile[row][col];
                if (piece != null) {
                    if (piece.getNumber() == 0) { // its a joker; potential value only used if its stairs
                        Tile tile1, tile2;
                        int potentialValue;
                        if(col == 0) {
                            tile1 = tile[row][col+1];
                            tile2 = tile[row][col+2];
                            potentialValue = (int)tile1.getNumber()-1;                        
                        }
                        else if(col == tile[row].length-1) {
                            tile1 = tile[row][tile[row].length-2];
                            tile2 = tile[row][tile[row].length-3];
                            potentialValue = (int)tile1.getNumber()+1; 
                        }
                        else {
                            tile1 = tile[row][col-1];
                            tile2 = tile[row][col+1];
                            potentialValue = (int)tile1.getNumber()+1; 
                        }
                        if(tile1.getNumber() == tile2.getNumber()) { // its a group
                            sum += (int)tile1.getNumber();
                        } else sum += potentialValue; // its stairs
                    }
                    else sum += piece.getNumber();
                }
            }
        }

        return sum;
    }

    public boolean isGameOver() {
         for (Player player : players) {
            if (player.getHand() == null) {
                return true;
            }
        }
       return false;
    }


    public void nextPlayer() {
        try {
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
        } catch (IndexOutOfBoundsException e) {
            currentPlayer = players.get(0);
        }
    }



    public static String printBoard(Tile[][] board) {
        String boardString = "";
        for (int row = 0; row < board.length; ++row) {
            boardString += "\n";
            for (int col = 0; col < board[0].length; ++col) {
                if (board[row][col] == null)
                    boardString += "n" + " ";
                else
                    boardString += board[row][col].getNumber() + " ";
            }
        }
        return boardString;
    }

    public List<Tile> getPool() {
        return pool;
    }

}
