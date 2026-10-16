package com.javachess.core;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public final class Board {

    private final Map<Position, Piece> squares;

    public Board(Map<Position, Piece> squares) {
        this.squares = Map.copyOf(squares);
    }

    public Optional<Piece> pieceAt(Position position) {
        return Optional.ofNullable(squares.get(position));
    }

    public boolean isEmpty(Position position) {
        return !squares.containsKey(position);
    }

    public Board move(Position from, Position to) {
        Piece source = this.squares.get(from);
        
        if(source == null) 
            throw new IllegalArgumentException("No piece present in the specified position");

        Piece target = this.squares.get(to);

        if(target != null && target.color() == source.color()) 
            throw new IllegalArgumentException("Cannot capture own piece");

        Map<Position, Piece> next = new HashMap<>(this.squares);

        next.remove(from);

        next.put(to, source);

        return new Board(next);
    }

    public static Board initial() {
        Map<Position, Piece> start = new HashMap<>();

        PieceType[] backRank = {
            PieceType.ROOK, 
            PieceType.KNIGHT, 
            PieceType.BISHOP,
            PieceType.QUEEN,
            PieceType.KING,
            PieceType.BISHOP,
            PieceType.KNIGHT,
            PieceType.ROOK
        };

        for(int i = 0; i < 8; i++){
            start.put(new Position(i, 0), new Piece(Color.WHITE, backRank[i]));
            start.put(new Position(i, 7), new Piece(Color.BLACK, backRank[i]));

            start.put(new Position(i, 1), new Piece(Color.WHITE, PieceType.PAWN));
            start.put(new Position(i, 6), new Piece(Color.BLACK, PieceType.PAWN));
        }

        return new Board(start);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof Board other)) return false;

        return squares.equals(other.squares);
    }

    @Override
    public int hashCode() {
        return squares.hashCode();
    }
}