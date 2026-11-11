package com.javachess.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CastlingRightsTest {

    @Test
    void shouldRemoveWhiteKingSideRight() {
        CastlingRights initial = new CastlingRights(
            true, true, true, true
        );

        CastlingRights updated = initial.withoutRookRight(
            Color.WHITE,
            Position.fromAlgebraic("h1")
        );

        assertEquals(
            new CastlingRights(false, true, true, true),
            updated
        );

        assertEquals(
            new CastlingRights(true, true, true, true),
            initial
        );
    }

    @Test
    void shouldRemoveWhiteQueenSideRight() {
        CastlingRights initial = new CastlingRights(
            true, true, true, true
        );

        CastlingRights updated = initial.withoutRookRight(
            Color.WHITE,
            Position.fromAlgebraic("a1")
        );

        assertEquals(
            new CastlingRights(true, false, true, true),
            updated
        );

        assertEquals(
            new CastlingRights(true, true, true, true),
            initial
        );
    }

    @Test
    void shouldRemoveBlackKingSideRight() {
        CastlingRights initial = new CastlingRights(
            true, true, true, true
        );

        CastlingRights updated = initial.withoutRookRight(
            Color.BLACK,
            Position.fromAlgebraic("h8")
        );

        assertEquals(
            new CastlingRights(true, true, false, true),
            updated
        );

        assertEquals(
            new CastlingRights(true, true, true, true),
            initial
        );
    }

    @Test
    void shouldRemoveBlackQueenSideRight() {
        CastlingRights initial = new CastlingRights(
            true, true, true, true
        );

        CastlingRights updated = initial.withoutRookRight(
            Color.BLACK,
            Position.fromAlgebraic("a8")
        );

        assertEquals(
            new CastlingRights(true, true, true, false),
            updated
        );

        assertEquals(
            new CastlingRights(true, true, true, true),
            initial
        );
    }
}