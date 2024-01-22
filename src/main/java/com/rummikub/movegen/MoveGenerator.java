package com.rummikub.movegen;

import com.rummikub.Utils;
import com.rummikub.game.Tile;
import javafx.scene.paint.Color;


import java.util.ArrayList;
import java.util.TreeMap;

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

    private ArrayList<ArrayList<Tile>> validSets;

    private ArrayList<Tile> currentHand;
    enum Type {
        GROUPS,
        RUNS
    }
    private TreeMap<Tile, TreeMap<Type, ArrayList<ArrayList<Tile>>>> potentialSets;
    private int lockedTiles;
    private TreeMap<Tile, Integer> tileOccurrences;
    private ArrayList<ArrayList<ArrayList<Tile>>> solutions;
    private static MoveGenerator instance;


    public static MoveGenerator getInstance() {
        if (instance == null) {
            instance = new MoveGenerator();
        }
        return instance;
    }

    private MoveGenerator() {
        this.tiles = new ArrayList<>();
        this.validSets = new ArrayList<>();
    }

    public void load(Tile[][] currentState, Tile[][] currentHand) {
        this.tileOccurrences = new TreeMap<>();
        this.tiles = new ArrayList<>();
        this.potentialSets = new TreeMap<>();
        this.validSets = new ArrayList<>();
        this.solutions = new ArrayList<>();
        this.currentHand = Utils.Array2DToArrayList(currentHand);
        this.lockedTiles = 0;

        ArrayList<Tile> boardTiles = Utils.Array2DToArrayList(currentState);
        ArrayList<Tile> handTiles = Utils.Array2DToArrayList(currentHand);

        tiles.addAll(boardTiles);
        tiles.addAll(handTiles);

        tiles = Utils.orderByStairs(tiles);

        for (Tile t : tiles) {
            if (t.isLocked()) this.lockedTiles++;
        }

        findPotentialSets();
        filterGroupsAndRuns();
    }

    public ArrayList<ArrayList<ArrayList<Tile>>> getSolutions(Tile[][] state, Tile[][] hand) {
        load(state, hand);
        solve();
        return this.solutions;
    }

    void findPotentialSets() {


        // TODO generate permutations of runs from each tile (only the lowest one of each color interests me)
        // TODO then filter out the groups so that we have no duplicates (1b1r1o and 1r1o1b are duplicates, 2b2r2o and 2b2r2o are not, basically if they come from the same tile)
        // TODO add all valid sets to the the list, then come up with a backtracking approach to find valid board configurations
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

            int tileCount = 1;

            for (Tile tile2 : tiles) {

                if (sameTile(tile, tile2) && (tile != tile2)) {
                    tileCount++;
                }

                if ((tile2.getColor() == tile.getColor()) && (tile2.getNumber() > tile.getNumber()) && (!isNumberInRun(tileRun, tile2)) && isValidRun(tileRun, tile2)) {

                    tileRun.add(tile2); // grows the current run
                }


                if (tile2.getNumber() == tile.getNumber() && (isUniqueColor(tileGroup, tile2))) {

                    tileGroup.add(tile2);

                }

            }

            this.tileOccurrences.put(tile, tileCount);

            if (tileGroup.size() >= 3) {
                potentialGroups.add(tileGroup);
            }

            if (tileRun.size() >= 3) {

                potentialRuns.addAll(decomposeRun(tileRun));
            }


            this.potentialSets.put(tile, potentialSets);
        }

    }

    boolean sameTile(Tile t1, Tile t2) {
        return (t1.getNumber() == t2.getNumber()) && (t1.getColor() == t2.getColor());
    }


    ArrayList<ArrayList<Tile>> decomposeRun(ArrayList<Tile> run) {

        int size = run.size();
        ArrayList<ArrayList<Tile>> decomposedRun = new ArrayList<>();

        if (size > 3) {

            while (size >= 3) {

                ArrayList<Tile> partialRun = new ArrayList<>();

                for (int i = 0; i <= size - 1; i++) {
                    partialRun.add(run.get(i));
                }

                size--;

                decomposedRun.add(partialRun);

            }
        } else {
            decomposedRun.add(run);
        }

        return decomposedRun;

    }

    ArrayList<ArrayList<Tile>> decomposeGroup(ArrayList<Tile> group) {

        int size = group.size();


        ArrayList<ArrayList<Tile>> decomposedGroup = new ArrayList<>();

        if (size > 3) {
            decomposedGroup.add(group);
            for (int i = 0; i < 4; i++) {

                ArrayList<Tile> partialGroup = new ArrayList<>();

                switch (i) {
                    case 0:
                        partialGroup.add(group.get(0));
                        partialGroup.add(group.get(1));
                        partialGroup.add(group.get(2));
                        break;
                    case 1:
                        partialGroup.add(group.get(1));
                        partialGroup.add(group.get(2));
                        partialGroup.add(group.get(3));
                        break;
                    case 2:
                        partialGroup.add(group.get(0));
                        partialGroup.add(group.get(2));
                        partialGroup.add(group.get(3));
                        break;
                    case 3:
                        partialGroup.add(group.get(0));
                        partialGroup.add(group.get(1));
                        partialGroup.add(group.get(3));
                        break;
                }

                decomposedGroup.add(partialGroup);
            }

        } else {
            decomposedGroup.add(group);
        }

        return decomposedGroup;
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

        for (int i = 0; i < tileRunCopy.size() - 1; i++) {
            if (tileRunCopy.get(i + 1).getNumber() - tileRunCopy.get(i).getNumber() != 1) {
                return false;
            }
        }
        return true;
    }

    void filterGroupsAndRuns() {

        ArrayList<Tile> tilesToIgnore = new ArrayList<>();

        for (Tile t : this.potentialSets.keySet()) {

            this.validSets.addAll(this.potentialSets.get(t).get(Type.RUNS));


            if (!tilesToIgnore.contains(t)) {

                try {
                    ArrayList<Tile> group = this.potentialSets.get(t).get(Type.GROUPS).get(0);

                    for (int i = 1; i < group.size(); i++) {
                        tilesToIgnore.add(group.get(i));
                    }

                    ArrayList<ArrayList<Tile>> decomposedGroup = decomposeGroup(group);
                    this.validSets.addAll(decomposedGroup);


                } catch (IndexOutOfBoundsException ex) {
                    continue;
                }
            }
        }
    }


    void solve() {

        for (ArrayList<Tile> set : this.validSets) {

            ArrayList<ArrayList<Tile>> move = new ArrayList<>();
            move.add(set);

            for (ArrayList<Tile> setToAdd : this.validSets) {

                if (safeToAdd(move, setToAdd)) {
                    move.add(setToAdd);
                }

            }

            if (isValidMove(move)) {
                this.solutions.add(move);
            }

        }
    }

    private boolean isValidMove(ArrayList<ArrayList<Tile>> move) {

        int lockedTiles = 0;

        for (ArrayList<Tile> set : move) {
            for (Tile t : set) {
                if (t.isLocked()) lockedTiles++;
            }
        }

        return lockedTiles == this.lockedTiles;
    }

    private boolean safeToAdd(ArrayList<ArrayList<Tile>> move, ArrayList<Tile> setToAdd) {

        for (ArrayList<Tile> set : move) {
            if (conflictingTiles(set, setToAdd)) {
                return false;
            }
        }
        return true;
    }

    private boolean conflictingTiles(ArrayList<Tile> set, ArrayList<Tile> setToAdd) {

        for (Tile t2 : setToAdd) {
            if (set.contains(t2)) {
                return true;
            }
        }
        return false;
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

        t1.lock();
        t2.lock();
        t3.lock();
        t4.lock();
        t5.lock();
        t6.lock();
        t7.lock();
        t8.lock();
        t9.lock();
        t10.lock();
        t11.lock();
        t12.lock();


        Tile t13 = new Tile((byte) 1, Color.BLUE);
        Tile t14 = new Tile((byte) 3, Color.BLACK);
        Tile t15 = new Tile((byte) 5, Color.BLACK);

        Tile[][] testBoard = {{t1, t2, t3, t4}, {t5, t6, t7, t8}, {t9, t10, t11, t12}};
        Tile[][] testHand = {{t13, t14, t15}};

        MoveGenerator.getInstance().load(testBoard, testHand);


        MoveGenerator gen = MoveGenerator.getInstance();


        for (Tile t : MoveGenerator.getInstance().potentialSets.keySet()) {
            System.out.printf("Tile %d %s : %s\n", t.getNumber(), t.getColorString(), MoveGenerator.getInstance().potentialSets.get(t));
        }

        System.out.println("-------");

        for (Tile t : MoveGenerator.getInstance().potentialSets.keySet()) {
            System.out.printf("Tile %d %s : %d\n", t.getNumber(), t.getColorString(), gen.tileOccurrences.get(t));
        }

        System.out.println("-------------");


        System.out.println("VALID SETS:");
        System.out.println(gen.validSets);
        System.out.println(gen.validSets.size());

        System.out.println("***********************************************");
        gen.solve();
        System.out.println(gen.solutions.size());
        for (ArrayList<ArrayList<Tile>> board : gen.solutions) {
            for (ArrayList<Tile> row : board) {
                System.out.println(row);
            }
            System.out.println("---");
        }
    }


}
