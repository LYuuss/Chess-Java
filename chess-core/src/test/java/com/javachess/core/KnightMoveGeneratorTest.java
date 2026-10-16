package com.javachess.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

class KnightMoveGeneratorTest {

    @Test
    void shouldGenerateAllEightMovesFromCenter() {
        Position from = Position.fromAlgebraic("d4");

        Board board = new Board(Map.of(
            from, new Piece(Color.WHITE, PieceType.KNIGHT)
        ));

        KnightMoveGenerator generator = new KnightMoveGenerator();

        List<Move> actual = generator.generate(board, from);

        Set<Move> expected = Set.of(
            new Move(from, Position.fromAlgebraic("b3")),
            new Move(from, Position.fromAlgebraic("b5")),
            new Move(from, Position.fromAlgebraic("c2")),
            new Move(from, Position.fromAlgebraic("c6")),
            new Move(from, Position.fromAlgebraic("e2")),
            new Move(from, Position.fromAlgebraic("e6")),
            new Move(from, Position.fromAlgebraic("f3")),
            new Move(from, Position.fromAlgebraic("f5"))
        );

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void shouldGenerateTwoMovesFromCorner(){
        Position from = Position.fromAlgebraic("a1");

        Board board = new Board(Map.of(
            from, new Piece(Color.WHITE, PieceType.KNIGHT)
        ));

        KnightMoveGenerator generator = new KnightMoveGenerator();

        List<Move> actual = generator.generate(board, from);

        Set<Move> expected = Set.of(
            new Move(from, Position.fromAlgebraic("b3")),
            new Move(from, Position.fromAlgebraic("c2"))
        );

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void shouldNotCaptureFriendlyPiece(){
        Position from = Position.fromAlgebraic("a1");
         Position to = Position.fromAlgebraic("c2");

        Board board = new Board(Map.of(
            from, new Piece(Color.WHITE, PieceType.KNIGHT),
            to, new Piece(Color.WHITE, PieceType.PAWN)
        ));

        KnightMoveGenerator generator = new KnightMoveGenerator();

        List<Move> actual = generator.generate(board, from);

        Set<Move> expected = Set.of(
            new Move(from, Position.fromAlgebraic("b3"))
        );

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }
    
    @Test
    void shouldAllowCapturingOpponentPiece(){
        Position from = Position.fromAlgebraic("a1");
        Position to = Position.fromAlgebraic("c2");

        Board board = new Board(Map.of(
            from, new Piece(Color.WHITE, PieceType.KNIGHT),
            to, new Piece(Color.BLACK, PieceType.PAWN)
        ));

        KnightMoveGenerator generator = new KnightMoveGenerator();

        List<Move> actual = generator.generate(board, from);

        Set<Move> expected = Set.of(
            new Move(from, Position.fromAlgebraic("b3")),
            new Move(from, Position.fromAlgebraic("c2"))
        );

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }

    @Test 
    void shouldNotModifyBoardDuringGeneration(){
        Piece knight = new Piece(Color.WHITE, PieceType.KNIGHT);
        Position d4 = Position.fromAlgebraic("d4");

        Board generatorBoard = new Board(Map.of(
            d4, knight
        ));

        Board before = new Board(Map.of(
            d4, knight
        ));

        KnightMoveGenerator generator = new KnightMoveGenerator();
        generator.generate(generatorBoard, d4);

        assertEquals(
            before,
            generatorBoard
        );
    }
}