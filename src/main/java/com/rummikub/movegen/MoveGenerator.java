package com.rummikub.movegen;

import com.rummikub.Utils;
import com.rummikub.game.Tile;
import javafx.scene.paint.Color;


import java.text.Format;
import java.util.ArrayList;
import java.util.TreeMap;

// LESGOO U GOT THIS EZ SLAP
public class MoveGenerator {


    // new approach:
    // 1. throw both the hand and board into one list /check
    // 2. sort them using 111 button (Nathan's sorting), this allows for easier checking for validity of runs, just ascending /check
    // 3. iterate over all the tiles one by one:
    //      - for each tile storing a potential group AND run it can be in (TreeMap)
    //      - check for both the resulting sets whether they are valid or not
    //      - memoization: store both valid and invalid sets in lists because they will repeat,
    //      no point in checking the validity of the same set twice
    // 4. iterate over all the valid sets generated this way and make sure that the tiles that were
    //     on board are included (count occurrences of locked tiles seems the easiest)
    // 5. generate permutations of resulting valid sets (powerset)
    // 6. you now have all the potential moves.


    // TODO when tiles are the same, eg (1b2b3b and 1b2b3b) not sure how to proceed /solved by misia
    // TODO conflicting tiles? 1: [1b1o1r], [1b2b3b]; which to play? if both sets are possible, add to conflicting, unless we have two 1b's
    //      - maybe this is not a problem? maybe checking if the number of locked tiles is no more no less than the rest of the board is enough?
    //      - but then what if all comes from hand, then # of locked tiles is 0 anyway
    //      - OR add all the lists regardless, then from the valid moves list just pick a random one and propagate the constraints (count the occurrences of a tile, if not enough then the move cannot be made)
    private ArrayList<Tile> tiles;
    private ArrayList<ArrayList<Tile>> invalidSets;
    private ArrayList<ArrayList<Tile>> validSets;

    private ArrayList<Tile> currentHand; //important!
    enum Type {
        GROUPS,
        RUNS
    }
    private TreeMap<Tile, TreeMap<Type, ArrayList<ArrayList<Tile>>>> potentialSets;

    private TreeMap<Integer, ArrayList<ArrayList<Tile>>> permutations;
    private static MoveGenerator instance;


    public static MoveGenerator getInstance() {
        if (instance == null) {
            instance = new MoveGenerator();
        }
        return instance;
    }

    private MoveGenerator() {
        this.tiles = new ArrayList<>();
        this.invalidSets = new ArrayList<>();
        this.validSets = new ArrayList<>();
    }

    public void load(Tile[][] currentState, Tile[][] currentHand) {
        this.tiles = new ArrayList<>();
        this.potentialSets = new TreeMap<>();
        this.validSets = new ArrayList<>();
        this.invalidSets = new ArrayList<>();
        this.permutations = new TreeMap<>();
        this.currentHand = Utils.Array2DToArrayList(currentHand);

        ArrayList<Tile> boardTiles = Utils.Array2DToArrayList(currentState);
        ArrayList<Tile> handTiles = Utils.Array2DToArrayList(currentHand);

        tiles.addAll(boardTiles);
        tiles.addAll(handTiles);

        tiles = Utils.orderByStairs(tiles);
    }

    void findPotentialSets() {

        // 1. gather a single group and run for each tile (for runs just for the smallest tile of a given colour), store them in the TreeMap
        // 2. get all the combinations of that particular group and run (decompose), reassign the TreeMap value
        // 3. check for validity, add the unique valid ones to potentialGroups and potentialRuns respectively


        // TODO now the groups and runs are generated well. I will need to filter out the invalid ones and find a way to distinguish equal/not equal groups
        for (Tile tile : tiles) {
            TreeMap<Type, ArrayList<ArrayList<Tile>>> potentialSets = new TreeMap<>();
            ArrayList<ArrayList<Tile>> potentialGroups = new ArrayList<>();
            ArrayList<ArrayList<Tile>> potentialRuns = new ArrayList<>();

            potentialSets.put(Type.GROUPS, potentialGroups); // puts an empty groups and runs array for each tile
            potentialSets.put(Type.RUNS, potentialRuns);

            ArrayList<Tile> tileGroup = new ArrayList<>();
            ArrayList<Tile> tileRun = new ArrayList<>(); // creates one big run which will then be decomposed

            tileGroup.add(tile);
            tileRun.add(tile);

            for (Tile tile2 : tiles) {


                if ((tile2.getColor() == tile.getColor()) && (tile2.getNumber() > tile.getNumber()) && (!isNumberInRun(tileRun, tile2)) && isValidRun(tileRun, tile2)) {

                    tileRun.add(tile2); // grows the current run
                }


                if (tile2.getNumber() == tile.getNumber() && (isUniqueColor(tileGroup, tile2))) {

                    tileGroup.add(tile2);

                }

            }

            potentialGroups.add(tileGroup);
            potentialRuns.add(tileRun);

            this.potentialSets.put(tile, potentialSets);
        }

    }

    private boolean isUniqueColor(ArrayList<Tile> tileGroup, Tile tileToAdd) {
        for (Tile tile : tileGroup)  {
            if (tile.getColor() == tileToAdd.getColor()) {
                return false;
            }
        }
        return true;
    }

    private boolean isNumberInRun(ArrayList<Tile> tileRun, Tile tileToAdd) {
        for (Tile tile : tileRun)  {
            if (tile.getNumber() == tileToAdd.getNumber()) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidRun(ArrayList<Tile> tileRun, Tile tileToAdd) {
        ArrayList<Tile> tileRunCopy = Utils.deepCopy(tileRun);
        tileRunCopy.add(tileToAdd.clone());

        for (int i = 0; i < tileRunCopy.size()- 1; i++) {
            if (tileRunCopy.get(i + 1).getNumber() - tileRunCopy.get(i).getNumber() != 1) {
                return false;
            }
        }
        return true;
    }



    public static void main(String[] args) {
        Tile t1 = new Tile((byte) 1, Color.RED);
        Tile t2 = new Tile((byte) 2, Color.RED);
        Tile t3 = new Tile((byte) 3, Color.RED);
        Tile t4 = new Tile((byte) 4, Color.RED);
        Tile t5 = new Tile((byte) 1, Color.ORANGE);
        Tile t6 = new Tile((byte) 2, Color.ORANGE);
        Tile t7 = new Tile((byte) 3, Color.ORANGE);
        Tile t8 = new Tile((byte) 4, Color.ORANGE);
        Tile t9 = new Tile((byte) 1, Color.BLACK);
        Tile t10 = new Tile((byte) 2, Color.BLACK);
        Tile t11 = new Tile((byte) 3, Color.BLACK);
        Tile t12 = new Tile((byte) 4, Color.BLACK);
        Tile tx = new Tile((byte) 11, Color.BLACK);
        Tile ty = new Tile((byte) 12, Color.BLACK);
        Tile tz = new Tile((byte) 13, Color.BLACK);


        Tile t13 = new Tile((byte) 1, Color.BLUE);
        Tile t14 = new Tile((byte) 3, Color.BLACK);
        Tile t15 = new Tile((byte) 5, Color.BLACK);

        Tile[][] testBoard = {{t1, t2, t3, t4}, {t5, t6, t7, t8}, {t9, t10, t11, t12}};
        Tile[][] testHand = {{t13, t14, t15, tx, ty, tz}};

        MoveGenerator.getInstance().load(testBoard, testHand);
        MoveGenerator.getInstance().findPotentialSets();

        System.out.println(MoveGenerator.getInstance().potentialSets.size());

        for (Tile t : MoveGenerator.getInstance().potentialSets.keySet()) {
            System.out.printf("Tile %d %s : %s\n", t.getNumber(), t.getColorString(), MoveGenerator.getInstance().potentialSets.get(t));
        }

    }


}
