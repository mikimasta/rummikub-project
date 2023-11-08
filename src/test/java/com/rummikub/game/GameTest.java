package com.rummikub.game;
import javafx.scene.paint.Color;
import org.junit.Test;
import org.junit.Test.None;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.After;


public class GameTest {
    private Game game;

    @Before
    public void setUp(){

        game = Game.getInstance((byte)4);
    }
    @After
    public void tearDown(){
        Game.endGame();
    }


/*
    @Test
    public void testDealInitialTiles() {
        for (Player player : game.players) {
            int count = 0;
            for (int row = 0; row < player.getHand().length; row++) {
                for (int col = 0; col < player.getHand()[row].length; col++) {
                    if (player.getHand()[row][col] != null) {
                        count++;
                    }
                }
            }
            assertEquals(14, count);
        }
        assertEquals(106 - (14 * game.players.size()), game.pool.size());
    }
    @Test
    public void testNextPlayer() {
        Player firstPlayer = game.currentPlayer;
        game.nextPlayer();
        Player secondPlayer = game.currentPlayer;
        assertNotEquals(firstPlayer, secondPlayer);
    }
    @Test
    public void testCircleGame() {
        Player firstPlayer = game.currentPlayer;
        game.nextPlayer();
        Player secondPlayer = game.currentPlayer;
        assertEquals(firstPlayer, secondPlayer);
    }
    @Test(expected = None.class)
    public void nextPlayerWhenOutOfBoundsTest() {
        //given
        int currentIndex = game.players.indexOf(game.currentPlayer);
        //when
        game.nextPlayer();
        //then no exception thrown
    }
*/
    @Test
    public void testIsValidBoard() {

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
        board[0][0] = new Tile((byte) 1, Color.RED);
        board[0][1] = new Tile((byte) 2, Color.RED);
        board[0][2] = new Tile((byte) 3, Color.RED);
        return board;
    }

    private Tile[][] createInvalidBoard() {
        Tile[][] board = new Tile[Game.GRID_ROWS][Game.GRID_COLS];
        board[0][0] = new Tile((byte) 1, Color.RED);
        board[0][1] = new Tile((byte) 1, Color.BLUE);
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

}


/*
*/


//    @Test
//    public void testNextPlayer() {
//        Game game = new Game((byte) 4);
//        Player firstPlayer = Game.currentPlayer;
//        game.nextPlayer();
//        Player secondPlayer = Game.currentPlayer;
//        assertNotEquals(firstPlayer, secondPlayer);
//    }
//    @Test
//    public void testCircleGame() {
//        Game game = new Game((byte) 2);
//        Player firstPlayer = Game.currentPlayer;
//        game.nextPlayer();
//        Player secondPlayer = Game.currentPlayer;
//        assertEquals(firstPlayer, secondPlayer);
//    }
//    @Test(expected = None.class)
//    public void nextPlayerWhenOutOfBoundsTest() {
//        //given
//        Game game = new Game((byte) 4);
//        int currentIndex = game.players.indexOf(Game.currentPlayer);
//        //when
//        game.nextPlayer();
//        //then no exception thrown
//    }
//    @Test
//    public void testDealInitialTiles() {
//        Game game = new Game((byte) 2);
//        game.dealInitialTiles();
//        for (Player player : game.players) {
//            assertEquals(14, player.getHand().size());
//        }
//        assertEquals(106 - (14 * game.players.size()), game.pool.size());
//    }
//
//
//
//    // testing the method isValidBoard
//    Game gaming = new Game((byte) 2);
//    Tile t0 = new Tile(null, (byte) 0); // tile with number 0 is null
//    Tile t1 = new Tile(Color.BLUE, (byte) 1);
//    Tile t2 = new Tile(Color.BLUE, (byte) 2);
//    Tile t3 = new Tile(Color.BLUE, (byte) 3);
//    Tile t4 = new Tile(Color.BLUE, (byte) 4);
//    Tile t5 = new Tile(Color.BLUE, (byte) 5);
//    Tile t6 = new Tile(Color.BLUE, (byte) 6);
//    Tile t7 = new Tile(Color.BLUE, (byte) 7);
//    Tile t8 = new Tile(Color.BLUE, (byte) 8);
//    Tile t9 = new Tile(Color.BLUE, (byte) 9);
//    Tile t10 = new Tile(Color.BLUE, (byte)10);
//    Tile t11 = new Tile(Color.BLUE, (byte) 11);
//    Tile t12 = new Tile(Color.BLUE, (byte) 12);
//    Tile t13  = new Tile(Color.BLUE, (byte) 13);
//
//    Tile t14 = new Tile(Color.RED, (byte) 1);
//    Tile t15 = new Tile(Color.RED, (byte) 1);
//    Tile t16 = new Tile(Color.RED, (byte) 1);
//
//    @Test
//    public void testRuns() {
//        Tile[][]  board = {
//        {t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13}
//        };
//        assertEquals(true, gaming.isValidBoard(board));
//    }
//
//    @Test
//    public void testNull() {
//        Tile[][]  board = {
//        {t0, t0, t0, t0, t0, t0, t0, t0, t0, t0, t0, t0, t0},
//        {t0, t0, t0, t0, t0, t0, t0, t0, t0, t0, t0, t0, t0}
//        };
//        assertEquals(true, gaming.isValidBoard(board));
//    }
//
//    @Test
//    public void testRun() {
//        Tile[][]  board = {
//        {t1, t2, t3, t4, t0, t6, t7, t8, t9, t0, t11, t12, t13}
//        };
//        assertEquals(true, gaming.isValidBoard(board));
//    }
//
//    @Test
//    public void testRun1() {
//        Tile[][]  board = {
//        {t1, t2, t3, t0, t5, t6, t7, t0, t9, t10, t11, t0, t0}
//        };
//        assertEquals(true, gaming.isValidBoard(board));
//    }
//
//    @Test
//    public void testAllEqual() {
//        Tile[][]  board = {
//        {t1, t1, t1, t1, t1, t1, t1, t1, t1, t1, t1, t1, t1},
//        {t1, t1, t1, t1, t1, t1, t1, t1, t1, t1, t1, t1, t1}
//        };
//        assertEquals(false, gaming.isValidBoard(board));
//    }
//
//    @Test
//    public void testGroup() {
//        Tile[][]  board = {
//        {t1, t14, t15, t16, t0, t0, t0, t0, t0, t0, t0, t0, t0},
//        {t0, t0, t0, t0, t1, t14, t15, t16, t0, t0, t0, t0, t0},
//        {t0, t0, t0, t0, t0, t0, t0, t0, t0, t1, t14, t15, t16,}
//        };
//        assertEquals(true, gaming.isValidBoard(board));
//    }
//
//    @Test
//    public void testGroupDoubleTile() {
//        Tile[][]  board = {
//        {t1, t14, t15, t16, t1, t0, t0, t0, t14, t15, t16, t0, t0}
//        };
//        assertEquals(false, gaming.isValidBoard(board));
//    }
//
//    @Test
//    public void testFalseBoard() {
//        Tile[][]  board = {
//        {t0, t0, t0, t1, t2, t0, t1, t2, t3, t0, t0, t0, t0}
//        };
//        assertEquals(false, gaming.isValidBoard(board));
//    }
//
//}
