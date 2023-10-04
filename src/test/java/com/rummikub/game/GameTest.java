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



}


