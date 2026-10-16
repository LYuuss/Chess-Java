package com.javachess.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PieceTest {
    @Test
    void piecesHaveValueEquality() {
        assertEquals(new Piece(Color.WHITE, PieceType.QUEEN),
                     new Piece(Color.WHITE, PieceType.QUEEN));
    }

    @Test
    void rejectsNullComponents() {
        assertThrows(NullPointerException.class, () -> new Piece(null, PieceType.KING));
        assertThrows(NullPointerException.class, () -> new Piece(Color.WHITE, null));
    }

    @Test
    void oppositeIsSymmetric() {
        assertEquals(Color.BLACK, Color.WHITE.opposite());
        assertEquals(Color.WHITE, Color.BLACK.opposite());
    }
}
