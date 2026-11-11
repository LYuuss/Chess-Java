package com.javachess.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    @Test
    void shouldCorrectlyInitializeGameState(){
        GameState initial = GameState.initial();

        assertEquals(Board.initial(), initial.getBoard());
        assertEquals(Color.WHITE, initial.getSideToMove());
        assertEquals(new CastlingRights(true, true, true, true), initial.getCastlingRights());
        assertTrue(initial.getEnPassant().isEmpty());
        assertEquals(0, initial.getHalfMoveClock());
        assertEquals(1, initial.getFullMoveNumber());
    }
}