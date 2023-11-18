package com.rummikub.game;

import com.rummikub.gui.MainMenu;
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
     public static Game getInstance(byte numPlayers) {
        if (instance == null)
            instance = new Game(numPlayers);
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
        Color[] colors = { Color.RED, Color.BLUE, Color.BLACK, Color.ORANGE };
        for (Color color : colors) {
            for (byte i = 0; i < 2; i++) {
                for (byte number = 1; number <= 13; number++) {
                    pool.add(new Tile(number, color));
                }
            }
        }
        // the interger 0 will be used in regard to a joker
        // two jokers are added into our game
        pool.add(new Tile((byte) 0, Color.RED));
        pool.add(new Tile((byte) 0, Color.BLACK));

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
        //make board into arrray list of array list
        boolean currentSet = false;
        ArrayList<Tile> set = new ArrayList<>();
        ArrayList<ArrayList<Tile>> allSets = new ArrayList<>();

        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if(gameBoard[i][j] != null){
                    currentSet = true;
                    set.add(gameBoard[i][j]);
                }
                if(gameBoard[i][j] == null && currentSet == true){
                    allSets.add(set);
                    set = new ArrayList<>();
                    currentSet = false;
                }
                
            }
        }

        if (set.size() != 0)
            allSets.add(set);

        //check each subset if its a valid group or set.
        for(int k = 0; k < allSets.size(); k++){
            ArrayList<Tile> subSet = allSets.get(k);
            if(!checkSubSet(subSet)){
                return false;
            }
        }
        return true; // Checked all tiles, all are correct
    }



    // Check if given tile is either group or stairs, i.e., is it legal?
    boolean checkSubSet(ArrayList<Tile> set) {
        if(set.size() < 3){
            return false;
        }
        return checkIfGroup(set) || checkIfStairs(set);
    }

    /**
     * Check if they all have the same number and different colors, i.e., they are a group.
     * @param set Set of tiles
     * @return true if the tiles form a group, false otherwise.
     */
    boolean checkIfGroup(ArrayList<Tile> set) {
        byte groupNumber = set.get(0).getNumber();
        ArrayList<Color> groupColor = new ArrayList<Color>();
        for(int i = 0; i < set.size(); i++){
            Tile currentTile = set.get(i);
            //check in the number on the tile is the same as the rest of the group
            if(currentTile.getNumber() != groupNumber && currentTile.getNumber() != 0){
                return false;
            }
            //if not a joker check if its colors is already in the group
            if(currentTile.getNumber() != 0){
            if(groupColor.contains(currentTile.getColor()) ){
                return false;
            }
            else{
                groupColor.add(currentTile.getColor());
            }
        }
        }

        return true;
    }

    /**
     * Check if the set is stairs, i.e., same color, incrementing blocks.
     * @param set Set of tiles
     * @return true if the tiles form a run, false otherwise.
     */
    boolean checkIfStairs(ArrayList<Tile> set){

        // check if all same color
        Color colorOfFirst = set.get(0).getColor();
        for (int i = 1; i < set.size(); i++) {
            if (!colorOfFirst.equals(set.get(i).getColor()) && !set.get(i).getColor().equals("any") && !colorOfFirst.equals("any")) {
                return false;
            }
        }

        // check if incrementing
        byte numOfFirst = set.get(0).getNumber();
        for (int i = 1; i < set.size(); i++) {
            byte numTmp = set.get(i).getNumber();
            if (numTmp - 1 != numOfFirst && numOfFirst != 0 && numTmp != 0) {
                return false;
            }
            numOfFirst = numTmp;
        }
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

    // this method just save group or run to arraylist. Does not check whether the group or run are legal.
    // like if the row is [1,2,3,0,6,6,6,0,7,8,9], then save {1,2,3}, {6,6,6}, {7,8,9}, 0=null
    // if add to isValidBoard method add boolean;
    public ArrayList<Tile> getSubset(Tile[][] gameBoard){

        ArrayList<Tile> set = new ArrayList<>();
        ArrayList<Tile> subset = new ArrayList<>();
        int a = 0;
        // boolean valid = false;

        for(int r = 0; r < gameBoard.length; r++){
            for(int l = 0; l < gameBoard[r].length; l++){

                if(gameBoard[r][l] != null){
                    set.add(gameBoard[r][l]);

                } else if(gameBoard[r][l] == null){

                    if(checkSet(set)){
                        subset.addAll(a,set);
                        set.clear();
                        a++;
                        // valid = true;
                    } 
                } 
            }
        } // return valid;
        
        return subset;

    }

    public boolean checkSet(ArrayList<Tile> set){
        if(set.size() < 3){
            return false;
        }
        return true;
    }

}
