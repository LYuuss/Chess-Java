package com.javachess.core;

import java.util.Locale;

/** File: a=0 ... h=7 ; rank: 1=0 ... 8=7. */
public record Position(int file, int rank) {
    public Position {
        if (file < 0 || file > 7 || rank < 0 || rank > 7) {
            throw new IllegalArgumentException("Position outside the board");
        }
    }

    public static Position fromAlgebraic(String notation) {
        if (notation == null || !notation.matches("(?i)[a-h][1-8]")) {
            throw new IllegalArgumentException("Invalid square: " + notation);
        }
        String square = notation.toLowerCase(Locale.ROOT);
        return new Position(square.charAt(0) - 'a', square.charAt(1) - '1');
    }

    public String toAlgebraic() {
        return "" + (char) ('a' + file) + (char) ('1' + rank);
    }

    public static boolean isInside(int file, int rank) {
        boolean state1 = 0 <= file && file < 8;
        boolean state2 = 0 <= rank && rank < 8;
        return  state1 && state2;
    }
}
