package com.javachess.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

class MoveGeneratorFactoryTest {

    @Test
    void shouldReuseSameGeneratorInstance(){

        MoveGenerator gen1 = MoveGeneratorFactory.forPiece(PieceType.ROOK);
        MoveGenerator gen2 = MoveGeneratorFactory.forPiece(PieceType.ROOK);

        assertSame(gen1, gen2);
    }

    static Stream<Arguments> movesByPiece() {
        return Stream.of(
            Arguments.of(
                PieceType.KNIGHT,
                Set.of("b3", "b5", "c2", "c6", "e2", "e6", "f3", "f5")
            ),
            Arguments.of(
                PieceType.ROOK,
                Set.of(
                    "d1", "d2", "d3", "d5", "d6", "d7", "d8",
                    "a4", "b4", "c4", "e4", "f4", "g4", "h4"
                )
            ),
            Arguments.of(
                PieceType.BISHOP,
                Set.of(
                    "a1", "b2", "c3", "e5", "f6", "g7", "h8",
                    "a7", "b6", "c5", "e3", "f2", "g1"
                )
            ),
            Arguments.of(
                PieceType.QUEEN,
                Set.of(
                    "d1", "d2", "d3", "d5", "d6", "d7", "d8",
                    "a4", "b4", "c4", "e4", "f4", "g4", "h4",
                    "a1", "b2", "c3", "e5", "f6", "g7", "h8",
                    "a7", "b6", "c5", "e3", "f2", "g1"
                )
            )
        );
    }
    @ParameterizedTest
    @MethodSource("movesByPiece")
    void shouldReturnCorrectGeneratorForSupportedPieces(
            PieceType type,
            Set<String> expectedDestinations
    ) {
        Position from = Position.fromAlgebraic("d4");

        Board board = new Board(Map.of(
            from, new Piece(Color.WHITE, type)
        ));

        MoveGenerator generator = MoveGeneratorFactory.forPiece(type);

        List<Move> actual = generator.generate(board, from);

        Set<String> actualDestinations = actual.stream()
            .map(move -> move.to().toAlgebraic())
            .collect(Collectors.toSet());

        assertEquals(expectedDestinations, actualDestinations);
        assertEquals(expectedDestinations.size(), actual.size());
    }

    @Test
    void shouldRejectNullPieceType(){
        assertThrows(
            NullPointerException.class,
            () -> MoveGeneratorFactory.forPiece(null));
    }

    @Test
    void shouldRejectUnsupportedPieceType(){
        assertThrows(
            IllegalArgumentException.class,
            () -> MoveGeneratorFactory.forPiece(PieceType.KING));
    }

}