package com.javachess.core;

import java.util.Optional; 
import java.util.Objects;

public final class GameState {

    private final Board board;
    private final Color sideToMove;
    private final CastlingRights castlingRights;
    private final Optional<Position> enPassant;
    private final int halfMoveClock;
    private final int fullMoveNumber;

    public GameState(
        Board board, 
        Color sideToMove,
        CastlingRights castlingRights,
        Optional<Position> enPassant,
        int halfMoveClock,
        int fullMoveNumber
        ) {
            Objects.requireNonNull(board, "Board must not be null");
            Objects.requireNonNull(sideToMove, "Color must not be null");
            Objects.requireNonNull(castlingRights, "CastlingRights must not be null");
            Objects.requireNonNull(enPassant, "enPassant must not be null");
            if(halfMoveClock < 0) 
                throw new IllegalArgumentException("HalfMoveClock must be >= 0");
            if(fullMoveNumber < 1)
                throw new IllegalArgumentException("fullMoveNumber must be >= 1");

            this.board = board;
            this.sideToMove = sideToMove;
            this.enPassant = enPassant;
            this.castlingRights = castlingRights;
            this.halfMoveClock = halfMoveClock;
            this.fullMoveNumber = fullMoveNumber;
    }
    
    public static GameState initial() {
        return new GameState(
            Board.initial(),
            Color.WHITE,
            new CastlingRights(true, true , true, true),
            Optional.empty(),
            0,
            1
        );

    }

    public Board getBoard() { return this.board;}
    public Color getSideToMove() { return this.sideToMove;}
    public CastlingRights getCastlingRights() { return this.castlingRights;}
    public Optional<Position> getEnPassant() { return this.enPassant;}
    public int getHalfMoveClock() { return this.halfMoveClock;}
    public int getFullMoveNumber() { return this.fullMoveNumber;}
}