package com.rummikub.game;


import org.junit.Test;
import org.junit.Test.None;
import static org.junit.Assert.*;

public class GameTest {
    @Test
    public void testNextPlayer() {
        Game game = new Game((byte) 4);
        Player firstPlayer = game.currentPlayer;
        game.nextPlayer();
        Player secondPlayer = game.currentPlayer;
        assertNotEquals(firstPlayer, secondPlayer);
    }
    @Test
    public void testCircleGame() {
        Game game = new Game((byte) 2);
        Player firstPlayer = game.currentPlayer;
        game.nextPlayer();game.nextPlayer();
        Player secondPlayer = game.currentPlayer;
        assertEquals(firstPlayer, secondPlayer);
    }
    @Test(expected = None.class)
    public void nextPlayerWhenOutOfBoundsTest() {
        //given
        Game game = new Game((byte) 4);
        int currentIndex = game.players.indexOf(game.currentPlayer);
        //when
        game.nextPlayer();
        //then no exception thrown
    }
    @Test
    public void testDealInitialTiles() {
        Game game = new Game((byte) 2);
        game.dealInitialTiles();
        for (Player player : game.players) {
            assertEquals(14, player.getHand().size());
        }
        assertEquals(106 - (14 * game.players.size()), game.pool.size());
    }



    // testing the method isValidBoard
    Game gaming = new Game((byte) 2); 
    Tile t0 = new Tile(null, (byte) 0); // tile with number 0 is null
    Tile t1 = new Tile("blue", (byte) 1);
    Tile t2 = new Tile("blue", (byte) 2);
    Tile t3 = new Tile("blue", (byte) 3);
    Tile t4 = new Tile("blue", (byte) 4);
    Tile t5 = new Tile("blue", (byte) 5);
    Tile t6 = new Tile("blue", (byte) 6);
    Tile t7 = new Tile("blue", (byte) 7);
    Tile t8 = new Tile("blue", (byte) 8);
    Tile t9 = new Tile("blue", (byte) 9);
    Tile t10 = new Tile("blue", (byte)10);
    Tile t11 = new Tile("blue", (byte) 11);
    Tile t12 = new Tile("blue", (byte) 12);
    Tile t13  = new Tile("blue", (byte) 13);

    Tile t14 = new Tile("red", (byte) 1);
    Tile t15 = new Tile("red", (byte) 1);
    Tile t16 = new Tile("red", (byte) 1);
    
    @Test 
    public void testRuns() { 
        Tile[][]  board = {
        {t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13}
        };
        assertEquals(true, gaming.isValidBoard(board));
    }

    @Test 
    public void testNull() { 
        Tile[][]  board = {
        {t0, t0, t0, t0, t0, t0, t0, t0, t0, t0, t0, t0, t0},
        {t0, t0, t0, t0, t0, t0, t0, t0, t0, t0, t0, t0, t0}
        };
        assertEquals(true, gaming.isValidBoard(board));
    }

    @Test   
    public void testRun() { 
        Tile[][]  board = {
        {t1, t2, t3, t4, t0, t6, t7, t8, t9, t0, t11, t12, t13}
        };
        assertEquals(true, gaming.isValidBoard(board));
    }

    @Test 
    public void testRun1() { 
        Tile[][]  board = {
        {t1, t2, t3, t0, t5, t6, t7, t0, t9, t10, t11, t0, t0}
        };
        assertEquals(true, gaming.isValidBoard(board));
    }

    @Test 
    public void testAllEqual() { 
        Tile[][]  board = {
        {t1, t1, t1, t1, t1, t1, t1, t1, t1, t1, t1, t1, t1},
        {t1, t1, t1, t1, t1, t1, t1, t1, t1, t1, t1, t1, t1}
        };
        assertEquals(false, gaming.isValidBoard(board));
    }

    @Test 
    public void testGroup() { 
        Tile[][]  board = {
        {t1, t14, t15, t16, t0, t0, t0, t0, t0, t0, t0, t0, t0},
        {t0, t0, t0, t0, t1, t14, t15, t16, t0, t0, t0, t0, t0},
        {t0, t0, t0, t0, t0, t0, t0, t0, t0, t1, t14, t15, t16,}
        };
        assertEquals(true, gaming.isValidBoard(board));
    }
        
    @Test 
    public void testGroupDoubleTile() { 
        Tile[][]  board = {
        {t1, t14, t15, t16, t1, t0, t0, t0, t14, t15, t16, t0, t0}
        };
        assertEquals(false, gaming.isValidBoard(board));
    }

    @Test 
    public void testFalseBoard() { 
        Tile[][]  board = {
        {t0, t0, t0, t1, t2, t0, t1, t2, t3, t0, t0, t0, t0}        
        };
        assertEquals(false, gaming.isValidBoard(board));
    }
    
}


