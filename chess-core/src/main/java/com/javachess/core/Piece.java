package com.javachess.core;

import java.util.Objects;

public record Piece(Color color, PieceType type) {
    public Piece {
        Objects.requireNonNull(color, "color");
        Objects.requireNonNull(type, "type");
    }
}
