package com.javachess.core;

import java.util.Map;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

public final class MoveGeneratorFactory {

    private static final Map<PieceType, MoveGenerator> GENERATORS;

    static {
        Map<PieceType, MoveGenerator> registry =
            new EnumMap<>(PieceType.class);

        registry.put(
            PieceType.KNIGHT, new KnightMoveGenerator()
        );

        registry.put(
            PieceType.BISHOP,
            new SlidingMoveGenerator(List.of(
                    new Direction(1, 1),
                    new Direction(-1, 1),
                    new Direction(-1, -1),
                    new Direction(1, -1)
            ))
        );

        registry.put(
            PieceType.ROOK,
            new SlidingMoveGenerator(List.of(
                    new Direction(1, 0),
                    new Direction(0, 1),
                    new Direction(-1, 0),
                    new Direction(0, -1)
            ))
        );

        registry.put(
            PieceType.QUEEN,
            new SlidingMoveGenerator(List.of(
                    new Direction(1, 0),
                    new Direction(0, 1),
                    new Direction(-1, 0),
                    new Direction(0, -1),
                    new Direction(1, 1),
                    new Direction(-1, 1),
                    new Direction(-1, -1),
                    new Direction(1, -1)
            ))
        );

        GENERATORS = Map.copyOf(registry);
    }

    public static MoveGenerator forPiece(PieceType type) {
        Objects.requireNonNull(type, "Piece type cannot be null");

        MoveGenerator generator = GENERATORS.get(type);

        if (generator == null) {
            throw new IllegalArgumentException(
                "Unsupported piece type: " + type
            );
        }

        return generator;
    }
}