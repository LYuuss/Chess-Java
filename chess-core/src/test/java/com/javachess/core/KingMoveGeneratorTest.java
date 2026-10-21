package com.javachess.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

class KingMoveGeneratorTest {
    
    @Test
    void shouldGenerateEightMovesFromCenter(){
        Position from = Position.fromAlgebraic("d4");

        Board board = new Board(Map.of(
            from, new Piece(Color.WHITE, PieceType.KING))
        );

        KingMoveGenerator generator = new KingMoveGenerator();

        List<Move> actual = generator.generate(board, from);

        Set<Move> expected = Set.of(
            new Move(from, Position.fromAlgebraic("d5")),
            new Move(from, Position.fromAlgebraic("d3")),
            new Move(from, Position.fromAlgebraic("c3")),
            new Move(from, Position.fromAlgebraic("c4")),
            new Move(from, Position.fromAlgebraic("c5")),
            new Move(from, Position.fromAlgebraic("e3")),
            new Move(from, Position.fromAlgebraic("e4")),
            new Move(from, Position.fromAlgebraic("e5"))
        );

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void shouldGenerateThreeMovesFromCorner(){
        Position from = Position.fromAlgebraic("a1");

        Board board = new Board(Map.of(
            from, new Piece(Color.WHITE, PieceType.KING)
        ));

        KingMoveGenerator generator = new KingMoveGenerator();

        List<Move> actual = generator.generate(board, from);

        Set<Move> expected = Set.of(
            new Move(from, Position.fromAlgebraic("a2")),
            new Move(from, Position.fromAlgebraic("b2")),
            new Move(from, Position.fromAlgebraic("b1"))
        );

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void shouldNotCaptureFriendlyPiece(){
        Position from = Position.fromAlgebraic("a1");
        Position friendly = Position.fromAlgebraic("a2");

        Board board = new Board(Map.of(
            from, new Piece(Color.WHITE, PieceType.KING),
            friendly, new Piece(Color.WHITE, PieceType.PAWN)
        ));

        KingMoveGenerator generator = new KingMoveGenerator();

        List<Move> actual = generator.generate(board, from);

        Set<Move> expected = Set.of(
            new Move(from, Position.fromAlgebraic("b2")),
            new Move(from, Position.fromAlgebraic("b1"))
        );

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }
    
    @Test
    void shouldAllowCapturingOpponentPiece(){
        Position from = Position.fromAlgebraic("a1");
        Position opponent = Position.fromAlgebraic("a2");

        Board board = new Board(Map.of(
            from, new Piece(Color.WHITE, PieceType.KING),
            opponent, new Piece(Color.BLACK, PieceType.PAWN)
        ));

        KingMoveGenerator generator = new KingMoveGenerator();

        List<Move> actual = generator.generate(board, from);

        Set<Move> expected = Set.of(
            new Move(from, Position.fromAlgebraic("a2")),
            new Move(from, Position.fromAlgebraic("b2")),
            new Move(from, Position.fromAlgebraic("b1"))
        );

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }
}