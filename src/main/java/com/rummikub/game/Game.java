package com.rummikub.game;

import javafx.scene.paint.Color;

import java.util.*;

/**
 * This class is a memory representation of the game state. It executes and
 * validates player moves.<br>
 * Is responsible for the flow of the game.
 */
public class Game {

    private List<Player> players;
    private List<Tile> pool;
    public Player currentPlayer;

    private static Game instance;


    public static final byte GRID_ROWS = 1;
    public static final byte GRID_COLS = 13;

    public static byte NUM_OF_PLAYERS;
    public static boolean isAIGame;

    public static Game getInstance() {
        if (instance == null)
            instance = new Game(NUM_OF_PLAYERS, isAIGame);
        return instance;
    }
    public static Game getInstance(byte numPlayers, boolean gameAI) {
        if (instance == null)
            instance = new Game(numPlayers, gameAI);
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
     * @param isAIGame if a game is ai
     */
    Game(byte numPlayers, boolean isAIGame) {

        if (numPlayers > 4 || numPlayers < 2)
            throw new IllegalArgumentException("A game can have a maximum of 4 players and a minimum of 2 players!");

        this.isAIGame = isAIGame;

        for (int row = 0; row < GRID_ROWS; ++row) {
            for (int col = 0; col < GRID_COLS; ++col) {
                //board[row][col] = new Tile("lol", (byte) 0);
            }
        }
        boolean isAnAI = true;
        players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player("Player " + i, isAnAI));
            isAnAI = false;
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
        pool.add(new Tile((byte) -1, Color.RED));
        pool.add(new Tile((byte) -1, Color.BLACK));

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
    static boolean checkTile(ArrayList<Tile> set) {
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
        int red = 0, orange = 0, blue = 0, black = 0;
        for (int i = 0; i < set.size() ; i++){
            if (set.get(i).getNumber() != -1){ // tile is not a joker
                String color = set.get(i).getColorString();
                if (color.equals("red")){
                    red++;
                }else if (color.equals("orange")){
                    orange++;
                }else if (color.equals("blue")){
                    blue++;
                }else if (color.equals("black")){
                    black++;
                }
            }
        }
        if (red > 1 || orange > 1 || blue > 1 || black > 1){
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
            } else if (set.get(i).getNumber() != -1 && !colorOfFirst.equals(color)) {
                return false;
            }
        }

        // check if incrementing
        byte numOfFirst = set.get(0).getNumber();
        for (int i = 1; i < set.size(); i++) {
            byte numTmp = set.get(i).getNumber();
            if (numOfFirst == 0) {
                numOfFirst = numTmp;
            } else {
                // If there is no joker, numbers should increment
                if (numTmp - 1 != numOfFirst && numTmp != 0) {
                    return false;
                }
                numOfFirst = numTmp;
            }
        }
        return true;
    }

    /**
     * checks whether the first move made is valid or not (>= 30)
     * @param board current state of the board after moved was made
     * @return
     */
    public boolean isValidFirstMove(Tile[][] board) {

        int total = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Tile t = board[i][j];

                if (t != null && !t.isLocked()) {

                    if (t.getNumber() == -1) {

                        if (board[i][j + 1] != null) {

                            if (board[i][j - 1] != null) {

                                if (board[i][j + 1] == board[i][j - 1]) {
                                    total += board[i][j + 1].getNumber();
                                } else {
                                    total += board[i][j - 1].getNumber() + 1;
                                }

                            } else {
                                if (board[i][j + 1] == board[i][j + 2]) {
                                    total += board[i][j + 1].getNumber();
                                } else {
                                    total += board[i][j + 1].getNumber() - 1;
                                }
                            }

                        } else {

                            if (board[i][j - 1] == board[i][j - 2]) {
                                total += board[i][j - 1].getNumber();
                            } else {
                                total += board[i][j - 1].getNumber() + 1;
                            }

                        }

                    } else {
                        total += t.getNumber();
                    }
                }
            }
        }

        return total >= 30;

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

    /**
     * sorting method to order tiles by runs
     * @param rack 2D array representing the rack of the player
     * @return the oredered by runs tiles of the player
     */
    public static Tile[][] orderRackByStairs(Tile[][] rack) {
        ArrayList<Tile> tileList = new ArrayList<>(BaselineAgent.TwodArrayToArrayList(rack));

        //sort the arraylist
        tileList.sort(Comparator.comparing(Tile::getNumber).thenComparing(Tile::getColorString));

        // add null tiles to the the set so that number of tiles in set
        // equals the number of tiles in arraylist
        for (int i = tileList.size(); i < rack[0].length * 2; i++){
            tileList.add(null);
        }

        BaselineAgent.arrayListToRack(tileList, rack);

        return rack;
    }

    /**
     * sorting method to order tiles by groups
     * @param rack 2D array representing the rack of the player
     * @return the ordered by groups tiles of the player
     */
    public static Tile[][] orderRackByGroup(Tile[][] rack) {
        ArrayList<Tile> tileList = new ArrayList<>(BaselineAgent.TwodArrayToArrayList(rack));

        //sort the arraylist
        tileList.sort(Comparator.comparing(Tile::getColorString).thenComparing(Tile::getNumber));

        // add null tiles to the the set so that number of tiles in set
        // equals the number of tiles in arraylist
        for (int i = tileList.size(); i < rack[0].length * 2; i++){
            tileList.add(null);
        }

        rack = BaselineAgent.arrayListToRack(tileList, rack);

        return rack;
    }

    /**
     * sorting method to order tiles by groups
     * @param set rack of tiles of the player
     * @return the ordered by groups tiles of the player
     */
    public static ArrayList<Tile> orderRackByGroup(ArrayList<Tile> set){
        set.sort(Comparator.comparing(Tile::getNumber).thenComparing(Tile::getColorString));
        return set;
    }

    /**
     * sorting method to order tiles by runs
     * @param set rack of tiles of the player
     * @return the oredered by runs tiles of the player
     */
    public static ArrayList<Tile> orderRackByStairs(ArrayList<Tile> set){
        set.sort(Comparator.comparing(Tile::getColorString).thenComparing(Tile::getNumber));
        return set;
    }

    /**
     * method to calculate the ending score of a player.
     * @param rack rack of tiles of a player
     * @return  the end score of players that lost, score is negative
     */
    public int calculateEndScore(Tile[][] rack) {
        int count = 0;
        for (int i = 0; i < rack.length; i++) {
            for (int y = 0; y < rack[0].length; y++) {
                if (rack[i][y] != null){
                    if (rack[i][y].getNumber() == (byte) -1){ // if tile is a joker +30
                        count += 30;
                    }else{
                        count += rack[i][y].getNumber();
                    }
                }

            }
        }
        return -count;
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



    public static ArrayList<Tile> orderSetStairs(ArrayList<Tile> set) {
        Tile joker = new Tile((byte) -1, Color.BROWN);
        if(set.contains(joker)){
            set.remove(joker);
            set.sort(Comparator.comparing(Tile::getColorString).thenComparing(Tile::getNumber));
            set.add(joker);
        }else{
            set.sort(Comparator.comparing(Tile::getColorString).thenComparing(Tile::getNumber));
        }
        return set;
    }


    public static ArrayList<Tile> orderSetGroup(ArrayList<Tile> set) {
        set.sort(Comparator.comparing(Tile::getNumber).thenComparing(Tile::getColorString));
        return set;
    }




}