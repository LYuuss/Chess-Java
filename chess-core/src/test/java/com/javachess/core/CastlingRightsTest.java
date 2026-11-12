package com.javachess.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

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

    @ParameterizedTest
    @EnumSource(Color.class)
    void shouldPreserveRightsWhenRookMovesFromOtherSquare(Color color) {
        CastlingRights initial = new CastlingRights(true, true, true, true);
        Position from = Position.fromAlgebraic("h4");

        CastlingRights updated = initial.withoutRookRight(color, from);

        assertEquals(initial, updated);
    }

    @Test
    void shouldNotReenablePreviouslyLostRight() {
        CastlingRights initial = new CastlingRights(
            false, true, true, true
        );

        CastlingRights updated = initial.withoutRookRight(
            Color.WHITE,
            Position.fromAlgebraic("h1")
        );

        assertEquals(
            new CastlingRights(false, true, true, true),
            updated
        );

        assertSame(initial, updated);
    }

    @ParameterizedTest
    @EnumSource(Color.class)
    void shouldRemoveBothRightsAfterKingMove(Color color) {
        CastlingRights initial = new CastlingRights(
            true, true, true, true
        );

        CastlingRights updated = initial.afterKingMove(color);

        CastlingRights expected = color == Color.WHITE
            ? new CastlingRights(false, false, true, true)
            : new CastlingRights(true, true, false, false);

        assertEquals(expected, updated);
        assertEquals(
            new CastlingRights(true, true, true, true),
            initial
        );
    }
}