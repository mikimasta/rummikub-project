package com.rummikub.game.Boards;

import com.rummikub.game.Tile;
import com.rummikub.model.ValidSets;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GeneratingLoops {
    public static void main(String[] args) {
        ArrayList<ArrayList<Tile>> validUniqueSets = ValidSets.getArrayListUniqueSets();
        ArrayList<ArrayList<ArrayList<Tile>>> validBoards = getValidBoards(validUniqueSets);

        printBoards(validBoards);
    }

    static ArrayList<ArrayList<ArrayList<Tile>>> getValidBoards(ArrayList<ArrayList<Tile>> validUniqueSets) {
        ArrayList<ArrayList<ArrayList<Tile>>> validBoards = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<Tile>>> boards = generateCombinations(validUniqueSets);

        for (ArrayList<ArrayList<Tile>> combination : boards) {
            if (isBoardValid(combination)) {
                validBoards.add(new ArrayList<>(combination));
            }
        }

        return validBoards;
    }

    static void printBoards(ArrayList<ArrayList<ArrayList<Tile>>> boards) {
        for (ArrayList<ArrayList<Tile>> board : boards) {
            System.out.println("Board:");
            for (ArrayList<Tile> row : board) {
                System.out.println(row);
            }
            System.out.println("---------");
        }
    }

    static ArrayList<ArrayList<ArrayList<Tile>>> generateCombinations(ArrayList<ArrayList<Tile>> validUniqueSets) {
        ArrayList<ArrayList<ArrayList<Tile>>> combinations = new ArrayList<>();
        combinationsRecursively(validUniqueSets, 0, new ArrayList<>(), combinations);
        return combinations;
    }

    static void combinationsRecursively(ArrayList<ArrayList<Tile>> validUniqueSets, int index,
                                        ArrayList<ArrayList<Tile>> currentCombination, ArrayList<ArrayList<ArrayList<Tile>>> combinations) {
        if (index == validUniqueSets.size()) {
            combinations.add(new ArrayList<>(currentCombination));
            return;
        }
        for(ArrayList<Tile> uniqueSet: validUniqueSets){
            currentCombination.add(uniqueSet);
            combinationsRecursively(validUniqueSets, index + 1, currentCombination, combinations);
        }
    }

    static boolean isBoardValid(ArrayList<ArrayList<Tile>> board) {
        ArrayList<Tile> boardList = new ArrayList<>();
        for (ArrayList<Tile> set : board) {
            boardList.addAll(set);
        }
        return boardList.size() <= 104 && !hasRepetition(boardList);
    }

    static boolean hasRepetition(ArrayList<Tile> board) {
        Map<Tile, Integer> count = new HashMap<>();
        for (Tile tile : board) {
            count.put(tile, count.getOrDefault(tile, 0) + 1);

            if (count.get(tile) > 2) {
                return true;
            }
        }
        return false;
    }
}

