package com.rummikub.game;
import java.sql.*;
import java.util.ArrayList;
import java.util.stream.IntStream;
import javafx.scene.paint.Color;



public class database {

static int comboCounter = 0;
static int runCounter = 0;
static int groupCounter = 0;




    // JDBC database URL, here "jdbc:sqlite:test.db" is the URL for a SQLite database named "test.db"
    //private static final String DATABASE_URL = "jdbc:sqlite:rummikub.db";

    public static void main(String[] args) {
        //get all the tiles (but onlu one set of each color) 
        ArrayList<Tile> rummikubTiles = new ArrayList<Tile>();
        Color[] colors = {Color.RED, Color.BLUE, Color.BLACK, Color.ORANGE };
        for (Color color : colors) {
                for (byte number = 1; number <= 13; number++) {
                    rummikubTiles.add(new Tile(number, color));
                }
        }
        // the interger 0 will be used in regard to a joker
        // two jokers are added into our game
        //rummikubTiles.add(new Tile((byte) 0, Color.RED));
        //rummikubTiles.add(new Tile((byte) 0, Color.BLACK));

        ArrayList<ArrayList<Tile>> result = findGroupsAndRuns(rummikubTiles);

        System.out.println(result.size());
        // Store the information in the database
        //storeInDatabase(result);
    }

    public static    ArrayList<ArrayList<Tile>>  findGroupsAndRuns(ArrayList<Tile> tiles) {
         ArrayList<ArrayList<Tile>>  groupsAndRuns = new ArrayList<>();
        // Game game = Game.getInstance((byte)4);
            // Generate all possible groups of 3 or 4 tiles
            for (int groupSize = 3; groupSize <= 5; groupSize++) {
                //valid combos of groupSize size
                comboCounter = 0;
                runCounter = 0;
                groupCounter = 0;
                ArrayList<ArrayList<Tile>> validCombos = combos(tiles, groupSize);
                System.out.println("number of combos for " + groupSize + " equals " + validCombos.size());
                System.out.println("number of combos made for " + groupSize + " equals " + comboCounter);
                System.out.println("number of runs made for " + groupSize + " equals " + runCounter);
                System.out.println("number of groups made for " + groupSize + " equals " + groupCounter);



                //add to to toal groups and runs
                groupsAndRuns.addAll(validCombos);
            }
       
        // for(int i = 0; i < groupsAndRuns.get(5).size(); i++){
        //     System.out.print((groupsAndRuns.get(0)).get(i).getNumber());
        //     //System.out.print((groupsAndRuns.get(0)).get(i).getColor());

        //     System.out.println();

        // }
        return groupsAndRuns;
    }

    private static ArrayList<ArrayList<Tile>> combos(ArrayList<Tile> tiles, int size){
    
    ArrayList<ArrayList<Tile>> result = new ArrayList<>();
    ArrayList<Tile> currentCombination = new ArrayList<>();
    generateCombinationsHelper(tiles, size, 0, currentCombination, result);
    
    return result;
    }

    private static  void generateCombinationsHelper(ArrayList<Tile> elements, int size, int start, ArrayList<Tile> currentCombination, ArrayList<ArrayList<Tile>> result) {
    Game game = Game.getInstance((byte)4);

    if (size == 0) {
        comboCounter++;

        if(game.checkIfStairs(currentCombination) || game.checkIfGroup(currentCombination)){
            //only add valid combination
            result.add(new ArrayList<>(currentCombination));       
        }
        if(game.checkIfStairs(currentCombination)){
           runCounter++;    
        }
        if(game.checkIfGroup(currentCombination)){
           groupCounter++;    
        }
        return;
    }

    for (int i = start; i < elements.size(); i++) {
        // Add the current element to the combination
        currentCombination.add(elements.get(i));

        // Recursively generate combinations for the remaining elements
        //if(game.checkIfStairs(currentCombination) || game.checkIfGroup(currentCombination)){
            //only continue if partially valid
            generateCombinationsHelper(elements, size - 1, i + 1, currentCombination, result);
        //}

        // Backtrack: remove the last added element to explore other possibilities
        currentCombination.remove(currentCombination.size() - 1);

    }
}


    // private static void storeInDatabase(List<List<Tile>> groupsAndRuns) {
    //     try (Connection connection = DriverManager.getConnection(DATABASE_URL);
    //          Statement statement = connection.createStatement()) {

    //         // Create the "rummikub" table if it doesn't exist
    //         String createTableSQL = "CREATE TABLE IF NOT EXISTS rummikub (id INTEGER PRIMARY KEY, tile INTEGER)";
    //         statement.execute(createTableSQL);

    //         // Insert each tile into the database
    //         for (List<Integer> groupOrRun : groupsAndRuns) {
    //             for (Integer tile : groupOrRun) {
    //                 String insertSQL = "INSERT INTO rummikub (tile) VALUES (" + tile + ")";
    //                 statement.execute(insertSQL);
    //             }
    //         }

    //         System.out.println("Data stored in the database.");

    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }

    // private static List<List<Integer>> retrieveFromDatabase() {
    //     List<List<Integer>> retrievedData = new ArrayList<>();

    //     try (Connection connection = DriverManager.getConnection(DATABASE_URL);
    //          Statement statement = connection.createStatement()) {

    //         // Select all tiles from the database
    //         String selectSQL = "SELECT tile FROM rummikub";
    //         ResultSet resultSet = statement.executeQuery(selectSQL);

    //         // Retrieve the tiles and add them to the result list
    //         List<Integer> currentGroupOrRun = new ArrayList<>();
    //         while (resultSet.next()) {
    //             currentGroupOrRun.add(resultSet.getInt("tile"));
    //         }

    //         // Close the result set and add the last group or run to the list
    //         resultSet.close();
    //         retrievedData.add(currentGroupOrRun);

    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }

    //     return retrievedData;
    // }
}

