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
 * This class is a memory representation of the game state. It executes and validates player moves.<br>
 * Is responsible for the flow of the game.
 */
 public class Game {

    //im unsure about how to do the board, like an array of tile arrays to show each run  or what?
    private List<Player> players;
    private static Tile[][]  board;
    private List<Tile> pool;
    private static Player currentPlayer;

    public static final byte GRID_ROWS = 10;
    public static final byte GRID_COLS = 15;

    /**
     * constructs a Game instance with a given number of player. <br>
     * creates the board, the pool and initializes and deals tiles.
     * @param numPlayers  number of players for the game
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

        byte firstPlayer = (byte)(0 + (byte)(Math.random() * numPlayers - 1));
        currentPlayer = players.get(firstPlayer);

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
private boolean isValidBoard(Tile[][] gameBoard) {
    // Check rows
    for (int row = 0; row < gameBoard.length; row++) {
        if (!isValidSetOrRun(Arrays.stream(gameBoard[row]).filter(Objects::nonNull).collect(Collectors.toList()))) {
            return false;
        }
    }

    // Check columns
    for (int col = 0; col < gameBoard[0].length; col++) {
        List<Tile> columnTiles = new ArrayList<>();
        for (int row = 0; row < gameBoard.length; row++) {
            Tile tile = gameBoard[row][col];
            if (tile != null) {
                columnTiles.add(tile);
            }
        }
        if (!isValidSetOrRun(columnTiles)) {
            return false;
        }
    }

    // Check for the starting tile in the initial meld
    boolean hasStartingTile = false;
    for (int row = 0; row < gameBoard.length; row++) {
        for (int col = 0; col < gameBoard[0].length; col++) {
            Tile tile = gameBoard[row][col];
            if (tile != null && tile.getNumber() == 1) {
                hasStartingTile = true;
                break;
            }
        }
        if (hasStartingTile) {
            break;
        }
    }

    return hasStartingTile;
}

private boolean isValidSetOrRun(List<Tile> tiles) {
    if (tiles.isEmpty()) {
        return true; // An empty set or run is valid
    }

    // Group tiles by number
    Map<Byte, List<Tile>> groupedByNumber = tiles.stream()
            .collect(Collectors.groupingBy(Tile::getNumber));

    // Check each group for valid sets or runs
    for (List<Tile> group : groupedByNumber.values()) {
        if (group.size() < 3) {
            return false; // A set or run must have at least 3 tiles
        }
        // Check if the group forms a valid set (same number, different colors)
        if (!group.stream().allMatch(t -> t.getColor().equals(group.get(0).getColor()))) {
            return false;
        }
    }
    System.out.println("its good");
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
        game.isValidBoard(board);


    }
}
