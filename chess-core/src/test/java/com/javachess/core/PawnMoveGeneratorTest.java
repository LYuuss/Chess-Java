package com.javachess.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

class PawnMoveGeneratorTest {
    private final PawnMoveGenerator generator = new PawnMoveGenerator();

    @ParameterizedTest
    @EnumSource(Color.class)
    void shouldMoveOneSquareForwardWhenEmpty(Color color){
        Position from = color == Color.WHITE ? 
            Position.fromAlgebraic("d4") : Position.fromAlgebraic("e5");

        Board board = new Board(Map.of(
            from, new Piece(color, PieceType.PAWN)
        ));

        List<Move> actual = generator.generate(board, from);

        Set<Move> expected;

        if(color == Color.WHITE) {
            expected = Set.of(
                new Move(from, Position.fromAlgebraic("d5"))
                );
        } else {
            expected = Set.of(
                new Move(from, Position.fromAlgebraic("e4"))
            );
        }

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }

    @ParameterizedTest
    @EnumSource(Color.class)
    void shouldMoveTwoSquaresFromInitialRank(Color color) {
        Position from = color == Color.WHITE ? 
            Position.fromAlgebraic("d2") : Position.fromAlgebraic("e7");

        Board board = new Board(Map.of(
            from, new Piece(color, PieceType.PAWN)
        ));
        
        

        List<Move> actual = generator.generate(board, from);

        Set<Move> expected;

        if(color == Color.WHITE) {
            expected = Set.of(
                new Move(from, Position.fromAlgebraic("d3")),
                new Move(from, Position.fromAlgebraic("d4"))
                );
        } else {
            expected = Set.of(
                new Move(from, Position.fromAlgebraic("e6")),
                new Move(from, Position.fromAlgebraic("e5"))
            );
        }

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());

    }

    @Test
    void shouldNotMoveForwardWhenBlocked() {
        Position from = Position.fromAlgebraic("d4");

        Board board = new Board(Map.of(
            from, new Piece(Color.WHITE, PieceType.PAWN),
            Position.fromAlgebraic("d5"), new Piece(Color.WHITE, PieceType.QUEEN)
        ));

        
        List<Move> actual = generator.generate(board, from);

        assertTrue(actual.isEmpty());

    }

    @Test
    void shouldCaptureOpponentPieceDiagonally() {
        Position from = Position.fromAlgebraic("d4");

        Board board = new Board(Map.of(
            from, new Piece(Color.WHITE, PieceType.PAWN),
            Position.fromAlgebraic("e5"), new Piece(Color.BLACK, PieceType.PAWN)
        ));

        
        List<Move> actual = generator.generate(board, from);

        Set<Move> expected = Set.of(
            new Move(from, Position.fromAlgebraic("d5")),
            new Move(from, Position.fromAlgebraic("e5"))
        );

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }

    @ParameterizedTest
    @EnumSource(Color.class)
    void shouldNotCaptureFriendlyPiece(Color color) {
        Position from = Position.fromAlgebraic("d4");

        Board board = new Board(Map.of(
            from, new Piece(color, PieceType.PAWN),
            Position.fromAlgebraic("e5"), new Piece(color, PieceType.PAWN),
            Position.fromAlgebraic("c5"), new Piece(color, PieceType.PAWN),
            Position.fromAlgebraic("c3"), new Piece(color, PieceType.PAWN),
            Position.fromAlgebraic("e3"), new Piece(color, PieceType.PAWN)
        ));

        
        List<Move> actual = generator.generate(board, from);

        Position destination = color == Color.WHITE
            ? Position.fromAlgebraic("d5")
            : Position.fromAlgebraic("d3");

        Set<Move> expected = Set.of(
            new Move(from, destination)
        );

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }
    
    @ParameterizedTest
    @EnumSource(Color.class)
    void shouldNotCaptureForward(Color color){
        Position from = Position.fromAlgebraic("d4");

        Board board = new Board(Map.of(
            from, new Piece(color, PieceType.PAWN),
            Position.fromAlgebraic("d5"), new Piece(color.opposite(), PieceType.PAWN),
            Position.fromAlgebraic("d3"), new Piece(color.opposite(), PieceType.PAWN)
        ));

        
        List<Move> actual = generator.generate(board, from);

        assertTrue(actual.isEmpty());
    }
   
    @ParameterizedTest
    @EnumSource(Color.class)
    void shouldNotMoveTwoSquaresAfterLeavingInitialRank(Color color) {
        Position from = Position.fromAlgebraic("e4");

        Board board = new Board(Map.of(
            from, new Piece(color, PieceType.PAWN)
        ));

        PawnMoveGenerator generator = new PawnMoveGenerator();
        List<Move> actual = generator.generate(board, from);

        Position destination = color == Color.WHITE
            ? Position.fromAlgebraic("e5")
            : Position.fromAlgebraic("e3");

        Set<Move> expected = Set.of(
            new Move(from, destination)
        );

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void shouldNotJumpToOccupiedSquare() {
        Position from = Position.fromAlgebraic("e2");
        Position blocked = Position.fromAlgebraic("e4");

        Board board = new Board(Map.of(
            from, new Piece(Color.WHITE, PieceType.PAWN),
            blocked, new Piece(Color.BLACK, PieceType.KNIGHT)
        ));

        PawnMoveGenerator generator = new PawnMoveGenerator();
        List<Move> actual = generator.generate(board, from);

        Set<Move> expected = Set.of(
            new Move(from, Position.fromAlgebraic("e3"))
        );

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }

    @ParameterizedTest
    @EnumSource(Color.class)
    void shouldHandlePawnCapturesAtBoardEdges(Color color) {
        Position from = Position.fromAlgebraic("h5");

        Position capture = color == Color.WHITE
            ? Position.fromAlgebraic("g6")
            : Position.fromAlgebraic("g4");

        Position forward = color == Color.WHITE
            ? Position.fromAlgebraic("h6")
            : Position.fromAlgebraic("h4");

        Board board = new Board(Map.of(
            from, new Piece(color, PieceType.PAWN),
            capture, new Piece(color.opposite(), PieceType.KNIGHT)
        ));

        PawnMoveGenerator generator = new PawnMoveGenerator();
        List<Move> actual = generator.generate(board, from);

        Set<Move> expected = Set.of(
            new Move(from, forward),
            new Move(from, capture)
        );

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }
}