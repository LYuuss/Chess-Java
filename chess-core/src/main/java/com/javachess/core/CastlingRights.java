package com.javachess.core;

import java.util.Objects;

public record CastlingRights(
    boolean whiteKingSide,
    boolean whiteQueenSide,
    boolean blackKingSide,
    boolean blackQueenSide
) {
    public CastlingRights afterKingMove(Color color) {
        if(color == Color.WHITE)
            return new CastlingRights(
                false,
                false,
                this.blackKingSide,
                this.blackQueenSide
            );

        return new CastlingRights(
            this.whiteKingSide,
            this.whiteQueenSide,
            false,
            false
        );
    }

    public CastlingRights withoutRookRight(Color color, Position from) {
        Objects.requireNonNull(color, "Color must not be null");
        Objects.requireNonNull(from, "Position must not be null");
        
        if(color == Color.WHITE){
            if(from.equals(Position.fromAlgebraic("a1")))
                return this.whiteQueenSide == false ? 
                    this : 
                    new CastlingRights(
                        this.whiteKingSide,
                        false,
                        this.blackKingSide,
                        this.blackQueenSide
                    );

            if(from.equals(Position.fromAlgebraic("h1")) )
                return this.whiteKingSide == false ? 
                    this : 
                    new CastlingRights(
                        false,
                        this.whiteQueenSide,
                        this.blackKingSide,
                        this.blackQueenSide
                    );

            return this;
        }

        if(from.equals(Position.fromAlgebraic("a8")))
            return this.blackQueenSide == false ?
                this :
                new CastlingRights(
                    this.whiteKingSide,
                    this.whiteQueenSide,
                    this.blackKingSide,
                    false
                );
        if(from.equals(Position.fromAlgebraic("h8")))
            return this.blackKingSide == false ? 
                this :
                new CastlingRights(
                    this.whiteKingSide,
                    this.whiteQueenSide,
                    false,
                    this.blackQueenSide
                );

        return this;
    }
    
}