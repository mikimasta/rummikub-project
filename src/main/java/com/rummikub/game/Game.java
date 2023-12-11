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
        // the interger -1 will be used in regard to a joker
        // two jokers are added into our game
        pool.add(new Tile((byte) -1, Color.BROWN));
        pool.add(new Tile((byte) -1, Color.BROWN));

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
     * this method checks the state of the entire board after a move has been made.
     * @param   gameBoard  the game board
     * @return  true if the board state complies with the rules, false otherwise.
     */
    public boolean isValidBoard(Tile[][] gameBoard) {
        ArrayList<Tile> set = new ArrayList<>();

        for (int i = 0; i < gameBoard.length; i++) {
            set.clear();

            for (int y = 0; y < gameBoard[i].length; y++) {
                Tile tile = gameBoard[i][y];
                // Checks for illegal subset of tiles of length 1 or 2
                if ((tile == null) && (set.size() > 0) && (set.size() < 3)) {
                    return false; 
                } else if (tile == null && set.size() > 2) {
                    if (!checkTile(set)) {
                        return false; // Check if given tile is either stairs or group, return false if not
                    }
                    set.clear();
                } else {
                }
                if (tile != null) {
                    set.add(tile);
                }
            }

            if (set.size() < 3 && set.size() != 0) {
                return false; // The block of tiles is too short
            } 

        }
        return true; // Checked all tiles, all are correct
    }

    // check if given tile is either group or stairs, i.e. is it legal?
    boolean checkTile(ArrayList<Tile> set) {
        return checkIfGroup(set) || checkIfStairs(set);
    }

    /**
     * checks if they all have the same number and different colors, i.e., they are a group.
     * @param set set of tiles
     * @return true if the tiles form a group false otherwise
     */
    static boolean checkIfGroup(ArrayList<Tile> set){

        // up to 4 tiles (thats how many colors there are)
        if (set.size() > 4) {
            return false;
        } 

        // check if same number
        byte numOfFirst = set.get(0).getNumber();
        for (int i = 1; i < set.size(); i++) {
            byte numTmp = set.get(i).getNumber();
            if (numOfFirst == -1){ // tile is a joker
                numOfFirst = numTmp;
            }else if (numOfFirst != -1 && numOfFirst != numTmp && numTmp != -1){
                return false;
            }
        }
        // check if different colors
        int red = 0, green = 0, blue = 0, black = 0; 
        for (int i = 0; i < set.size() ; i++){
            if (set.get(i).getNumber() != -1){ // tile is not a joker
                Color color = set.get(i).getColor();
                if (color == Color.RED){
                    red++;
                }else if (color == Color.GREEN){
                    green++;
                }else if (color == Color.BLUE){
                    blue++;
                }else if (color == Color.BLACK){
                    black++;
                }
            }
        }
        if (red > 1 || green > 1 || blue > 1 || black > 1){
            return false;
        }

        return true;
    }

    /**
     * check if the set is stairs, i.e., same color, incrementing blocks.
     * @param set set of tiles
     * @return true if the tiles form a run, false otherwise
     */
    static boolean checkIfStairs(ArrayList<Tile> set){

        // check if all same color
        Color colorOfFirst = set.get(0).getColor();
        for (int i = 1; i < set.size(); i++) {
            Color color = set.get(i).getColor();
            if (set.get(0).getNumber() == -1){ // first tile is a joker
                colorOfFirst = color;
            }else if (set.get(i).getNumber() != -1 && !colorOfFirst.equals(color)) {
                return false;
            }
        }

        // check if incrementing
        byte numOfFirst = set.get(0).getNumber();
        for (int i = 1; i < set.size(); i++) {
            byte numTmp = set.get(i).getNumber();
            if (numOfFirst == -1) { // first number is a joker
                numOfFirst = (byte) (numTmp - 1);
            }
            if (numTmp == -1) { // current number is a joker
                numTmp = (byte) (numOfFirst + 1);
            }
            if (numTmp - 1 != numOfFirst || numTmp > 13 || numOfFirst < 1) {
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


    public static ArrayList<ArrayList<Tile>> boardArrayToList(Tile[][] gameBoard){
        ArrayList<ArrayList<Tile>> board = new ArrayList<>();

        for (int i = 0; i < gameBoard.length; i++) {
            int count = 0;
            ArrayList<Tile> set = new ArrayList<>();
            for (int y = 0; y < gameBoard[i].length; y++) {
                Tile tile = gameBoard[i][y];
                if (tile != null) {
                    assert set != null;
                    set.add(tile);
                    count++;
                }
                if (((tile == null) && (count > 2))||(y==(gameBoard[i].length-1)&& set!=null)) {
                    count = 0;
                    board.add(set);
                    set = null;
                }
            }
        }
        return board;
    }
    public static ArrayList<String> boardListToSetKey (ArrayList<ArrayList<Tile>> gameBoard){
        ArrayList<String> board = new ArrayList<>();
        for(ArrayList<Tile> set: gameBoard){
            String setKey="";
            if(set.size()<6){
                for (int i = 0; i < set.size(); i++) {
                    Tile tile = set.get(i);
                    setKey += ("(" + tile.getNumber() + "," + getStringFromColor(tile) + ").");
                }
                board.add(getUniqueKeyString(setKey));
            }
            else{
                int n = set.size()/3;
                for (int x = 0; x < n - 1 ; x++) {
                    for (int i = 0; i < 3; i++) {
                        Tile tile = set.get(i+x);
                        setKey += ("(" + tile.getNumber() + "," + getStringFromColor(tile) + ").");
                    }
                    board.add(getUniqueKeyString(setKey));
                    setKey="";
                }
                for (int i = 3*(n-1); i <set.size(); i++) {
                    Tile tile = set.get(i);
                    setKey += ("(" + tile.getNumber() + "," + getStringFromColor(tile) + ").");
                }
                board.add(getUniqueKeyString(setKey));
            }

        }
        return board;
    }
    public static String getUniqueKeyString(String setKey){
        String[] objects = setKey.replaceAll(",", "").split("\\.");
        Arrays.sort(objects, (s1, s2) -> {
            int num1 = Integer.parseInt(s1.replaceAll("[^0-9]", ""));
            int num2 = Integer.parseInt(s2.replaceAll("[^0-9]", ""));
            String word1 = s1.replaceAll("[^a-zA-Z]", "");
            String word2 = s2.replaceAll("[^a-zA-Z]", "");

            if (num1 != num2) {
                return Integer.compare(num1, num2);
            } else {
                return word1.compareTo(word2);
            }
        });
        StringBuilder setKeyString = new StringBuilder();
        for (String element : objects) {
            setKeyString.append(element.replaceAll("[()]", ""));
        }
        return (setKeyString.toString());
    }
    public static String getStringFromColor(Tile tile){
        String color = "";
        if (tile != null) {
            if (tile.getColor() == Color.RED) {
                color = "Red";
            } else if (tile.getColor() == Color.BLUE) {
                color = "Blue";
            } else if (tile.getColor() == Color.BLACK) {
                color = "Black";
            } else if (tile.getColor() == Color.ORANGE) {
                color = "Orange";
            } else {
                color = "None";
            }
        }
        return color;
    }

    public static ArrayList<Tile> TwodArrayToArrayList(Tile[][] arr){
        ArrayList<Tile> arrList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] != null) {
                    arrList.add(arr[i][j]);
                }
            }
        }
        return arrList;
    }

    public static ArrayList<Tile> orderSetGroup(ArrayList<Tile> set) {
        set.sort(Comparator.comparing(Tile::getNumber).thenComparing(Tile::getColorString));
        return set;
    }

    public static ArrayList<Tile> orderSetStairs(ArrayList<Tile> rack) {
        rack.sort(Comparator.comparing(Tile::getColorString).thenComparing(Tile::getNumber));
        return rack;
    }

    public static void main(String[] args) {

        Tile joker = new Tile((byte) -1, Color.BROWN);

        Tile rr1 = new Tile((byte) 1, Color.RED);
        Tile rr2 = new Tile((byte) 2, Color.RED);
        Tile rr3 = new Tile((byte) 3, Color.RED);
        Tile rr4 = new Tile((byte) 4, Color.RED);
        Tile rr5 = new Tile((byte) 5, Color.RED);
        Tile rr6 = new Tile((byte) 6, Color.RED);
        Tile rr7 = new Tile((byte) 7, Color.RED);
        Tile rr8 = new Tile((byte) 8, Color.RED);
        Tile rr9 = new Tile((byte) 9, Color.RED);
        Tile rr10 = new Tile((byte) 10, Color.RED);
        Tile rr11 = new Tile((byte) 11, Color.RED);
        Tile rr12 = new Tile((byte) 12, Color.RED);
        Tile rr13 = new Tile((byte) 13, Color.RED);

        Tile oo1 = new Tile((byte) 1, Color.ORANGE);
        Tile oo2 = new Tile((byte) 2, Color.ORANGE);
        Tile oo3 = new Tile((byte) 3, Color.ORANGE);
        Tile oo4 = new Tile((byte) 4, Color.ORANGE);
        Tile oo5 = new Tile((byte) 5, Color.ORANGE);
        Tile oo6 = new Tile((byte) 6, Color.ORANGE);
        Tile oo7 = new Tile((byte) 7, Color.ORANGE);
        Tile oo8 = new Tile((byte) 8, Color.ORANGE);
        Tile oo9 = new Tile((byte) 9, Color.ORANGE);
        Tile oo10 = new Tile((byte) 10, Color.ORANGE);
        Tile oo11 = new Tile((byte) 11, Color.ORANGE);
        Tile oo12 = new Tile((byte) 12, Color.ORANGE);
        Tile oo13 = new Tile((byte) 13, Color.ORANGE);

        Tile bu1 = new Tile((byte) 1, Color.BLUE);
        Tile bu2 = new Tile((byte) 2, Color.BLUE);
        Tile bu3 = new Tile((byte) 3, Color.BLUE);
        Tile bu4 = new Tile((byte) 4, Color.BLUE);
        Tile bu5 = new Tile((byte) 5, Color.BLUE);
        Tile bu6 = new Tile((byte) 6, Color.BLUE);
        Tile bu7 = new Tile((byte) 7, Color.BLUE);
        Tile bu8 = new Tile((byte) 8, Color.BLUE);
        Tile bu9 = new Tile((byte) 9, Color.BLUE);
        Tile bu10 = new Tile((byte) 10, Color.BLUE);
        Tile bu11 = new Tile((byte) 11, Color.BLUE);
        Tile bu12 = new Tile((byte) 12, Color.BLUE);
        Tile bu13 = new Tile((byte) 13, Color.BLUE);

        Tile bl1 = new Tile((byte) 1, Color.BLACK);
        Tile bl2 = new Tile((byte) 2, Color.BLACK);
        Tile bl3 = new Tile((byte) 3, Color.BLACK);
        Tile bl4 = new Tile((byte) 4, Color.BLACK);
        Tile bl5 = new Tile((byte) 5, Color.BLACK);
        Tile bl6 = new Tile((byte) 6, Color.BLACK);
        Tile bl7 = new Tile((byte) 7, Color.BLACK);
        Tile bl8 = new Tile((byte) 8, Color.BLACK);
        Tile bl9 = new Tile((byte) 9, Color.BLACK);
        Tile bl10 = new Tile((byte) 10, Color.BLACK);
        Tile bl11 = new Tile((byte) 11, Color.BLACK);
        Tile bl12 = new Tile((byte) 12, Color.BLACK);
        Tile bl13 = new Tile((byte) 13, Color.BLACK);

        ArrayList<Tile> set1 = new ArrayList<>();
        set1.add(bl5);
        set1.add(bl6);
        set1.add(joker);
        set1.add(bl8);
        set1.add(bl9);

        ArrayList<Tile> set2 = new ArrayList<>();
        set2.add(bu5);
        set2.add(bu6);
        set2.add(bu7);
        set2.add(bu4);

        ArrayList<Tile> set3 = new ArrayList<>();
        set3.add(rr1);
        set3.add(rr2);
        set3.add(rr3);

        ArrayList<Tile> set4 = new ArrayList<>();
        set4.add(oo10);
        set4.add(oo11);
        set4.add(oo12);
        set4.add(oo13);

        ArrayList<Tile> set5 = new ArrayList<>();
        set5.add(rr11);
        set5.add(oo11);
        set5.add(bu11);
        set5.add(bl11);

        ArrayList<Tile> set6 = new ArrayList<>();
        set6.add(rr7);
        set6.add(bl7);
        set6.add(bu7);

        ArrayList<ArrayList<Tile>> fullBoard = new ArrayList<>();
        fullBoard.add(set1);
        fullBoard.add(set2);
        fullBoard.add(set3);
        fullBoard.add(set4);
        fullBoard.add(set5);
        fullBoard.add(set6);

        ArrayList<Tile> hand = new ArrayList<>();
        hand.add(rr4);
        hand.add(rr5);
        hand.add(oo5);
        hand.add(bl11);
        hand.add(rr11);
        hand.add(bu3);
        hand.add(bu6);
        hand.add(bu5);
        hand.add(bu7);
        hand.add(oo8);
        hand.add(oo9);

        ArrayList<ArrayList<Tile>> board2 = new ArrayList<>();
        board2.add(set1);
        System.out.println(boardListToSetKey(board2));
        System.out.println(set1);
        System.out.println(orderSetStairs(set1));
        orderSetStairs(set1);

    }
}
