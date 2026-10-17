package com.javachess.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

/*
    SlidingMoveGeneratorTest
        1. Une tour en d4, sur un plateau autrement vide, possède 14 coups pseudo-légaux.
        2. Un fou en d4, sur un plateau autrement vide, possède 13 coups pseudo-légaux.
        3. Une tour blanche en d4 avec un pion blanc en d6 ne peut atteindre ni d6, ni d7, ni d8.
        4. Une tour blanche en d4 avec un pion noir en d6 peut capturer ce pion, mais ne peut pas atteindre d7 ou d8.
*/ 

class SlidingMoveGeneratorTest {

    @Test
    void shouldGenerateAllFourteenRookMovesFromCenter(){
        Position from = Position.fromAlgebraic("d4");


        Board board = new Board( Map.of(
            from, new Piece(Color.WHITE, PieceType.ROOK)
        ));

        SlidingMoveGenerator generator = new SlidingMoveGenerator(List.of(
            new Direction(1, 0),
            new Direction(0, 1),
            new Direction(-1, 0),
            new Direction(0, -1)
        ));

        List<Move> actual = generator.generate(board, from);

        Set<Move> expected = new HashSet<>();

        for(int r = 0; r < 8; ++r ) {
            if (r == from.rank()) continue;
            expected.add(new Move(
                from, new Position(from.file(), r)
            ));
        }
        for(int f = 0; f < 8; ++f){
            if(f == from.file()) continue;
            expected.add(new Move(
                from, new Position(f, from.rank())
            ));
        }
        

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void shouldGenerateAllThirteenBishopMovesFromCenter(){
        Position from = Position.fromAlgebraic("d4");


        Board board = new Board( Map.of(
            from, new Piece(Color.WHITE, PieceType.BISHOP)
        ));

        SlidingMoveGenerator generator = new SlidingMoveGenerator(List.of(
            new Direction(1, 1),
            new Direction(-1, 1),
            new Direction(-1, -1),
            new Direction(1, -1)
        ));

        List<Move> actual = generator.generate(board, from);

        Set<Move> expected = new HashSet<>();
 
        for(int diagL = 0; diagL < 8; ++diagL ) {
            if (diagL == 3) continue;
            expected.add(new Move(
                from, new Position(diagL, diagL)
            ));
        }
        for(int diagR = 0; diagR < 7; ++diagR){
            if(diagR == 3) continue;
            expected.add(new Move(
                from, new Position(diagR, 6 - diagR)
            ));
        }
        
        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void shouldNotCaptureOrJumpOverFriendlyPiece() {
        Position from = Position.fromAlgebraic("d4");
        Position d6 = Position.fromAlgebraic("d6");

        Board board = new Board( Map.of(
            from, new Piece(Color.WHITE, PieceType.ROOK),
            d6, new Piece(Color.WHITE, PieceType.PAWN)
        ));

        SlidingMoveGenerator generator = new SlidingMoveGenerator(List.of(
            new Direction(1, 0),
            new Direction(0, 1),
            new Direction(-1, 0),
            new Direction(0, -1)
        ));

        List<Move> actual = generator.generate(board, from);

        Set<Move> expected = new HashSet<>();

        for(int r = 0; r < 8; ++r ) {
            if (r == from.rank() || r >= d6.rank()) continue;
            expected.add(new Move(
                from, new Position(from.file(), r)
            ));
        }
        for(int f = 0; f < 8; ++f){
            if(f == from.file()) continue;
            expected.add(new Move(
                from, new Position(f, from.rank())
            ));
        }

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void shouldCaptureOpponentPieceWithoutJumpingOver() {
        Position from = Position.fromAlgebraic("d4");
        Position d6 = Position.fromAlgebraic("d6");

        Board board = new Board( Map.of(
            from, new Piece(Color.WHITE, PieceType.ROOK),
            d6, new Piece(Color.BLACK, PieceType.PAWN)
        ));

        SlidingMoveGenerator generator = new SlidingMoveGenerator(List.of(
            new Direction(1, 0),
            new Direction(0, 1),
            new Direction(-1, 0),
            new Direction(0, -1)
        ));

        List<Move> actual = generator.generate(board, from);

        Set<Move> expected = new HashSet<>();

        for(int r = 0; r < 8; ++r ) {
            if (r == from.rank() || r > d6.rank()) continue;
            expected.add(new Move(
                from, new Position(from.file(), r)
            ));
        }
        for(int f = 0; f < 8; ++f){
            if(f == from.file()) continue;
            expected.add(new Move(
                from, new Position(f, from.rank())
            ));
        }

        assertEquals(expected, new HashSet<>(actual));
        assertEquals(expected.size(), actual.size());
    }
}