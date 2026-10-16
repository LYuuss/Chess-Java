package com.javachess.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    @ParameterizedTest
    @CsvSource({"a1, 0, 0", "e2, 4, 1", "h8, 7, 7", "E4, 4, 3"})
    void parsesSquares(String square, int file, int rank) {
        assertEquals(new Position(file, rank), Position.fromAlgebraic(square));
    }

    @ParameterizedTest
    @CsvSource({"a1", "e2", "h8"})
    void notationRoundTrip(String square) {
        assertEquals(square, Position.fromAlgebraic(square).toAlgebraic());
    }

    @ParameterizedTest
    @ValueSource(strings = {"a0", "i1", "a9", "e22", "", "  e2"})
    void rejectsInvalidNotation(String square) {
        assertThrows(IllegalArgumentException.class, () -> Position.fromAlgebraic(square));
    }

    @Test
    void rejectsNullNotation() {
        assertThrows(IllegalArgumentException.class, () -> Position.fromAlgebraic(null));
    }

    @Test
    void rejectsOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> new Position(8, 0));
        assertThrows(IllegalArgumentException.class, () -> new Position(0, -1));
    }
}
