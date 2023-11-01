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
        int count;
        ArrayList<Tile> set = new ArrayList<>();

        for (int i = 0; i < gameBoard.length; i++) {
            count = 0;
            set.clear();

            for (int y = 0; y < gameBoard[i].length; y++) {
                Tile tile = gameBoard[i][y];
                // Checks for illegal subset of tiles of length 1 or 2
                if ((tile == null) && (count > 0) && (count < 3)) {
                    System.out.println("test des valides");
                    return false; 
                } else if (tile == null) {
                    count = 0;
                } else {
                    count++;
                }

                if (tile != null) {
                    set.add(tile);
                }
            }

            if (set.size() < 3 && set.size() != 0) {
                return false; // The block of tiles is too short
            } else if (set.size() > 2) {
                if (!checkTile(set)) {
                    return false; // Check if given tile is either stairs or group, return false if not
                }
            }
        }

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
        // Check if same number
        int count = 1;
        byte numFirst = set.get(0).getNumber();

        for (int i = 1; i < set.size(); i++) {
            byte numTmp = set.get(i).getNumber();

            if (numFirst == numTmp || numFirst == 0) { 
                count++;
            } else {
                if ((count < 3) || (count > 4)) {
                    return false; // Group can't be smaller than 3 or bigger than 4
                }
                numFirst = numTmp;
            }
        }

        int nbColors = 0;
        numFirst = set.get(0).getNumber();
        Set<Color> colors = new HashSet<>();

        for (int i = 0; i < set.size(); i++) {
            if ((!colors.contains(set.get(i).getColor()) || set.get(i).getColor().equals("joker")) && (numFirst == set.get(i).getNumber() || numFirst == 0)) {                nbColors++;
                nbColors++;
                colors.add(set.get(i).getColor());
                
                if (nbColors > 4) { // group cannot be greater then the number of colors
                    return false;
                }
            } else {
                if (nbColors < 3){ // group can't be smaller than 3
                    return false;
                }
                nbColors = 1;
                colors.clear();
                colors.add(set.get(i).getColor());
                numFirst = set.get(i).getNumber();
            }
        }
        if (nbColors > 4) {
            return false;
        }
        return true;
    }

    /**
     * Check if the set is stairs, i.e., same color, incrementing blocks.
     * @param set Set of tiles
     * @return true if the tiles form a run, false otherwise.
     */
    boolean checkIfStairs(ArrayList<Tile> set) {
        // Check if all same color
        Color colorOfFirst = set.get(0).getColor();
        
        for (int i = 1; i < set.size(); i++) {
            if (!colorOfFirst.equals(set.get(i).getColor()) && set.get(i).getNumber() != 0) {
                return false;
            }
        }

        // Check if incrementing
        int count = 1;
        byte numOfFirst = set.get(0).getNumber();

        for (int i = 1; i < set.size(); i++) {
            byte numTmp = set.get(i).getNumber();
            if (numOfFirst + (byte) 1 == numTmp || numTmp == 0) {
                count++;
            } else {
                if (count < 3) {
                    return false; // Run smaller than 3
                }
                count = 1;
            }
            numOfFirst = numTmp;
        }
        return true;
    }

    public boolean isValidFirstMove(Tile[][] currentHand, Tile[][] prevHand){


        return true;
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
