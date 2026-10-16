package com.javachess.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

class BoardTest {
    
    @Test
    void moveMustNotModifyOriginalBoard() {
        Board initial = Board.initial();

        Position e2 = Position.fromAlgebraic("e2");
        Position e4 = Position.fromAlgebraic("e4");

        Board next = initial.move(e2, e4);

        assertTrue(initial.pieceAt(e2).isPresent());
        assertTrue(initial.pieceAt(e4).isEmpty());

        assertTrue(next.pieceAt(e2).isEmpty());
        assertTrue(next.pieceAt(e4).isPresent());
    }

    @Test
    void shouldCaptureOpponentPiece() {
        Position e4 = Position.fromAlgebraic("e4");
        Position d5 = Position.fromAlgebraic("d5");

        Piece whitePawn = new Piece(Color.WHITE, PieceType.PAWN);
        Piece blackPawn = new Piece(Color.BLACK, PieceType.PAWN);

        Board board = new Board(Map.of(
            e4, whitePawn,
            d5, blackPawn
        ));

        Board next = board.move(e4, d5);

        assertTrue(next.pieceAt(e4).isEmpty());
        
        assertEquals(whitePawn, next.pieceAt(d5).orElseThrow());

        assertEquals(blackPawn, board.pieceAt(d5).orElseThrow());
    }

    @Test
    void shouldRejectMoveFromEmptySquare() {
        Map<Position, Piece> empty = new HashMap<>();

        Position a1 = Position.fromAlgebraic("a1");
        Position a3 = Position.fromAlgebraic("a3");

        Board board = new Board(empty);

        assertThrows(IllegalArgumentException.class, () -> board.move(a1, a3));
    
    }

    @Test
    void shouldRejectCaptureOfFriendlyPiece() {
        Position e4 = Position.fromAlgebraic("e4");
        Position d5 = Position.fromAlgebraic("d5");

        Piece whitePawn1 = new Piece(Color.WHITE, PieceType.PAWN);
        Piece whitePawn2 = new Piece(Color.WHITE, PieceType.PAWN);

        Board board = new Board(Map.of(
            e4, whitePawn1,
            d5, whitePawn2
        ));

        assertThrows(IllegalArgumentException.class, () -> board.move(e4, d5));
    }

    @Test
    void shouldPreserveOriginalBoardAfterMove() {
        Position e4 = Position.fromAlgebraic("e4");
        Position d5 = Position.fromAlgebraic("d5");

        Piece whitePawn = new Piece(Color.WHITE, PieceType.PAWN);
        Piece blackPawn = new Piece(Color.BLACK, PieceType.PAWN);

        Board beforeBoard = new Board(Map.of(
            e4, whitePawn,
            d5, blackPawn
        ));

        Piece originalWhitePawn = beforeBoard.pieceAt(e4).orElseThrow();
        Piece originalBlackPawn = beforeBoard.pieceAt(d5).orElseThrow();

        Board afterBoard = beforeBoard.move(e4, d5);

        assertEquals(originalWhitePawn, beforeBoard.pieceAt(e4).orElseThrow());
        assertEquals(originalBlackPawn, beforeBoard.pieceAt(d5).orElseThrow());
        assertTrue(afterBoard.pieceAt(e4).isEmpty());
        assertEquals(originalWhitePawn, afterBoard.pieceAt(d5).orElseThrow());
    }

    static Stream<Arguments> backRankPieces() {
        return Stream.of(
            Arguments.of(0, PieceType.ROOK),
            Arguments.of(1, PieceType.KNIGHT),
            Arguments.of(2, PieceType.BISHOP),
            Arguments.of(3, PieceType.QUEEN),
            Arguments.of(4, PieceType.KING),
            Arguments.of(5, PieceType.BISHOP),
            Arguments.of(6, PieceType.KNIGHT),
            Arguments.of(7, PieceType.ROOK)
        );
    }

    @ParameterizedTest
    @MethodSource("backRankPieces")
    void shouldInitializeBoardCorrectly(int file, PieceType type) {

        Board board = Board.initial();

        Piece whitePawn = new Piece(Color.WHITE, PieceType.PAWN);
        Piece blackPawn = new Piece(Color.BLACK, PieceType.PAWN);

        assertEquals(
            new Piece(Color.WHITE, type),
            board.pieceAt(new Position(file, 0)).orElseThrow() 
        );

        assertEquals(
            new Piece(Color.BLACK, type),
            board.pieceAt(new Position(file, 7)).orElseThrow() 
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7})
    void shouldInitializePawns(int file) {
        Board board = Board.initial();

        Piece whitePawn = new Piece(Color.WHITE, PieceType.PAWN);
        Piece blackPawn = new Piece(Color.BLACK, PieceType.PAWN);

        assertEquals(
                whitePawn, 
                board.pieceAt(new Position(file, 1)).orElseThrow()
            );

            assertEquals(
                blackPawn,
                board.pieceAt(new Position(file, 6)).orElseThrow()
            );
    }

    @Test
    void shouldLeaveMiddleSquaresEmpty(){
        Board board = Board.initial();

        for(int i = 0; i < 8; i++){
            for(int j = 2; j < 6; j++){
                Position pos = new Position(i, j);
                assertTrue(board.pieceAt(pos).isEmpty());
            }
        }
    }

}