package com.rummikub.model;
import com.rummikub.game.Tile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class UniqueKeyGenerator {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Set&&NB.txt"));
            String line;

            while ((line = reader.readLine()) != null) {

                String[] dataSets = line.split(";");
                String dataSet = dataSets[0];

                String[] objects = dataSet.replaceAll("[{}]", "").replaceAll("[,]", "").split("\\.");

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

                StringBuilder result = new StringBuilder();
                for (String element : objects) {
                    result.append(element.replaceAll("[()]", ""));
                }

                System.out.println("{"+result.toString()+ "};"+dataSets[0]+";"+dataSets[1]);

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
