package com.rummikub.game.AI;
import javafx.scene.paint.Color;
import com.rummikub.game.Tile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ValidSets {
    private static final HashMap<String, ArrayList<Tile>> immutableMap = generateImmutableHashMap();
    private static HashMap<String, ArrayList<Tile>> generateImmutableHashMap() {
        HashMap<String, ArrayList<Tile>> validSets =new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\mespi\\OneDrive\\Escritorio\\DACS2\\Project 2.1\\Key_Set_NB_J-1.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String[] neighbours = parts[2].replaceAll("[{}]", "").split("\\.");
                ArrayList<Tile> nb= new ArrayList<>();
                for (String neighbour : neighbours) {
                    if (!neighbour.isEmpty()) {
                        String[] numColor = neighbour.replaceAll("[()]", "").split(",");
                        Tile tile = new Tile(Byte.parseByte(numColor[0]), getColorFromString(numColor[1]));
                        nb.add(tile);
                    } else {
                        nb.add(null);
                    }
                }

                String key = parts[0].replaceAll("[{}]", "");
                validSets.put(key, nb);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return validSets;
    }
    public static HashMap<String, ArrayList<Tile>> getImmutableMap() {
        return immutableMap;
    }

    public static ArrayList<Tile> getValueForKey(String key) {
        return immutableMap.get(key);
    }

    public static Color getColorFromString(String str) {
        if (str.equalsIgnoreCase("Red")) {
            return Color.RED;
        } else if (str.equalsIgnoreCase("Blue")) {
            return Color.BLUE;
        } else if (str.equalsIgnoreCase("Black")) {
            return Color.BLACK;
        } else if (str.equalsIgnoreCase("Orange")) {
            return Color.ORANGE;
        } else if (str.equalsIgnoreCase("None")) {
            return null;
        } else {
            return null;
        }
    }
}
