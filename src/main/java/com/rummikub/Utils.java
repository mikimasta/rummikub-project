package com.rummikub;

import com.rummikub.game.BaselineAgent;
import com.rummikub.game.Tile;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Utilities for operations on arrays and arraylists
 */
public class Utils {

    /**
     * sorting method to order tiles by runs
     * @param rack 2D array representing the rack of the player
     * @return the oredered by runs tiles of the player
     */
    public static Tile[][] orderRackByStairs(Tile[][] rack) {
        ArrayList<Tile> tileList = new ArrayList<>(Array2DToArrayList(rack));

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

    public static ArrayList<Tile> orderByStairs(ArrayList<Tile> rack) {
        ArrayList<Tile> tileList = new ArrayList<>(rack);

        //sort the arraylist
        tileList.sort(Comparator.comparing(Tile::getNumber).thenComparing(Tile::getColorString));

        return tileList;
    }
    public static ArrayList<Tile> orderByGroup(ArrayList<Tile> rack) {
        ArrayList<Tile> tileList = new ArrayList<>(rack);

        //sort the arraylist
        tileList.sort(Comparator.comparing(Tile::getColorString).thenComparing(Tile::getNumber));

        return tileList;
    }

    /**
     * sorting method to order tiles by groups
     * @param rack 2D array representing the rack of the player
     * @return the ordered by groups tiles of the player
     */
    public static Tile[][] orderRackByGroup(Tile[][] rack) {
        ArrayList<Tile> tileList = new ArrayList<>(Array2DToArrayList(rack));

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
     * transforms a 2D array to an arraylist
     * @param arr 2D array
     * @return an arraylist
     */
    public static ArrayList<Tile> Array2DToArrayList(Tile[][] arr) {
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

    public static ArrayList<Tile> deepCopy(ArrayList<Tile> tileList) {
        ArrayList<Tile> deepCopy = new ArrayList<>(tileList.size());

        for (Tile t : tileList) {
            deepCopy.add(t.clone());
        }

        return deepCopy;
    }

    public static Tile[][] listTo2dArray(ArrayList<ArrayList<Tile>> list) {
        int rows = list.size();
        int cols = 0;

        for (ArrayList<Tile> rowList : list) {
            cols = Math.max(cols, rowList.size());
        }


        Tile[][] tileArray = new Tile[rows][cols];

        for (int i = 0; i < rows; i++) {
            ArrayList<Tile> rowList = list.get(i);
            for (int j = 0; j < rowList.size(); j++) {
                tileArray[i][j] = rowList.get(j);
            }
        }

        return tileArray;
    }
    public static ArrayList<ArrayList<Tile>> convertToArrayList(Tile[][] tileArray) {
        ArrayList<ArrayList<Tile>> arrayListOfArrayList = new ArrayList<>();

        for (Tile[] row : tileArray) {
            ArrayList<Tile> rowList = new ArrayList<>();
            for (Tile tile : row) {
                rowList.add(tile);
            }
            arrayListOfArrayList.add(rowList);
        }

        return arrayListOfArrayList;
    }
}
