package com.rummikub.game;
import javafx.scene.paint.Color;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.ArrayList;


public class GameTest {
    private Game game;

    // @Before
    // public void setUp(){

    //     game = Game.getInstance((byte)4);
    // }
    // @After
    // public void tearDown(){
    //     Game.endGame();
    // }

    @Test
    public void testIsValidBoard() {
        game = Game.getInstance((byte)4);
        Tile[][] emptyBoard = new Tile[Game.GRID_ROWS][Game.GRID_COLS];
        assertTrue(game.isValidBoard(emptyBoard));

        Tile[][] validGroupBoard = createValidGroupBoard();
        assertTrue(game.isValidBoard(validGroupBoard));

        Tile[][] validRunBoard = createValidRunBoard();
        assertTrue(game.isValidBoard(validRunBoard));

        Tile[][] invalidBoard = createInvalidBoard();
        assertFalse(game.isValidBoard(invalidBoard));

        Tile[][] mixedBoard = createMixedBoard();
        assertFalse(game.isValidBoard(mixedBoard));
    }

    private Tile[][] createValidGroupBoard() {
        Tile[][] board = new Tile[Game.GRID_ROWS][Game.GRID_COLS];
        board[0][0] = new Tile((byte) 1, Color.RED);
        board[0][1] = new Tile((byte) 1, Color.BLUE);
        board[0][2] = new Tile((byte) 1, Color.BLACK);
        return board;
    }

    private Tile[][] createValidRunBoard() {
        Tile[][] board = new Tile[Game.GRID_ROWS][Game.GRID_COLS];
        board[0][0] = new Tile( (byte) 1, Color.RED);
        board[0][1] = new Tile( (byte) 2, Color.RED);
        board[0][2] = new Tile( (byte) 3, Color.RED);
        return board;
    }

    private Tile[][] createInvalidBoard() {
        Tile[][] board = new Tile[Game.GRID_ROWS][Game.GRID_COLS];
        board[0][0] = new Tile((byte) 1, Color.RED) ;
        board[0][1] = new Tile( (byte) 1,Color.BLUE);
        return board;
    }

    private Tile[][] createMixedBoard() {
        Tile[][] board = new Tile[Game.GRID_ROWS][Game.GRID_COLS];
        board[0][0] = new Tile((byte) 1, Color.RED);
        board[0][1] = new Tile((byte) 2, Color.RED);
        board[0][3] = new Tile((byte) 4, Color.RED); // Gap in the run
        board[0][4] = new Tile((byte) 5, Color.RED);
        return board;
    }



    @Test
    public void validRun(){
        game = Game.getInstance((byte)4);
        Tile tile1 = new Tile((byte) 5, Color.RED);
        Tile tile2 = new Tile((byte) 6, Color.RED);
        Tile tile3 = new Tile((byte) 7, Color.RED);
        Tile tile4 = new Tile((byte) 8, Color.RED);
        
        ArrayList<Tile> test = new ArrayList<>();
        test.add(tile1);
        test.add(tile2);
        test.add(tile3);
        test.add(tile4);

        assertEquals(true, game.checkIfStairs(test));

    }

    @Test
    public void validRunJokers(){
        game = Game.getInstance((byte)4);
        Tile tile1 = new Tile((byte) 0, Color.RED);
        Tile tile2 = new Tile((byte) 2, Color.RED);
        Tile tile3 = new Tile((byte) 3, Color.RED);
        Tile tile4 = new Tile((byte) 0, Color.RED);
        
        ArrayList<Tile> test = new ArrayList<>();
        test.add(tile1);
        test.add(tile2);
        test.add(tile3);
        test.add(tile4);

        assertEquals(true, game.checkIfStairs(test));

    }
     @Test
    public void validGroup(){
        game = Game.getInstance((byte)4);
        Tile tile1 = new Tile((byte) 11, Color.RED);
        Tile tile2 = new Tile((byte) 11, Color.BLUE);
        Tile tile3 = new Tile((byte) 11, Color.BLACK);
        //Tile tile4 = new Tile((byte) 5, Color.ORANGE);
        
        ArrayList<Tile> test = new ArrayList<>();
        test.add(tile1);
        test.add(tile2);
        test.add(tile3);
        //test.add(tile4);

        assertEquals(true, game.checkIfGroup(test));

    }

    @Test
    public void validGroupJokers(){
        game = Game.getInstance((byte)4);
        Tile tile1 = new Tile((byte) 5, Color.RED);
        Tile tile2 = new Tile((byte) 5, Color.ORANGE);
        Tile tile3 = new Tile((byte) 0, Color.RED);
        //Tile tile4 = new Tile((byte) 0, Color.BLUE);

        
        ArrayList<Tile> test = new ArrayList<>();
        test.add(tile1);
        test.add(tile2);
        test.add(tile3);
        //test.add(tile4);

        assertEquals(true, game.checkIfGroup(test));

    }

    @Test
    public void breaking(){
        game = Game.getInstance((byte)4);
        Tile[][] board = new Tile[Game.GRID_ROWS][Game.GRID_COLS];
        Tile tile1 = new Tile((byte) 11, Color.RED);
        Tile tile2 = new Tile((byte) 11, Color.BLUE);
        Tile tile3 = new Tile((byte) 11, Color.BLACK);
        board[0][0] = tile1;
        board[0][1] = tile2;
        board[0][3] = tile3;
        
        assertEquals(true, game.isValidBoard(board));
    }
}
