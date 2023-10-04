package com.rummikub.game;

import org.junit.Test;
import org.junit.Test.None;

public class GameTest {

    
    @Test(expected = None.class)
    public void nextPlayerWhenOutOfBoundsTest() {
        //given
        Game g = new Game((byte) 4);

        //when
        g.nextPlayer();

        //then no exception thrown
    }

}


