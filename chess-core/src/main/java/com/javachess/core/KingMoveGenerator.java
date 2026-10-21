package com.javachess.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public final class KingMoveGenerator implements MoveGenerator {
    private static final List<Direction> KING_DIRECTIONS = List.of(
        new Direction(1, 0),
        new Direction(0, 1),
        new Direction(-1, 0),
        new Direction(0, -1),
        new Direction(1, 1),
        new Direction(-1, 1),
        new Direction(-1, -1),
        new Direction(1, -1)
    );

    @Override
    public List<Move> generate(Board board, Position from){
        Piece king = board.pieceAt(from).orElseThrow();

        if(king.type() != PieceType.KING){
            throw new IllegalArgumentException("Expected a king");
        }

        List<Move> possible = new ArrayList<>();

        for(Direction d : KING_DIRECTIONS){
            int newFile = from.file() + d.df();
            int newRank = from.rank() + d.dr();

            if(!Position.isInside(newFile, newRank)) continue;
            
            Position to = new Position(
                newFile,
                newRank
            );

            Optional<Piece> dest = board.pieceAt(to);

            if(dest.isEmpty() || !dest.get().color().equals(king.color()))
                possible.add(new Move(from, to));
        }

        return possible;

    }

}