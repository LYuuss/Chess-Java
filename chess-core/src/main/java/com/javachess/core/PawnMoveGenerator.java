package com.javachess.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public final class PawnMoveGenerator implements MoveGenerator {
    
    @Override
    public List<Move> generate(Board board, Position from) {
        Piece pawn = board.pieceAt(from).orElseThrow();

        if(pawn.type() != PieceType.PAWN){
            throw new IllegalArgumentException("Expected pawn");
        }

        List<Move> possible = new ArrayList<>();

        int x = pawn.color() == Color.WHITE ? 1 : -1;

        int nextRank = from.rank() + x;

        if(Position.isInside(from.file(), nextRank)){
            Position step = new Position(
                from.file(),
                nextRank
            );
            Optional<Piece> front = board.pieceAt(step);

            if(front.isEmpty()){
                possible.add(new Move(from, step));
                
                int initialRank = pawn.color() == Color.WHITE ? 1 : 6;
                if(from.rank() == initialRank){
                    Position doubleJump = new Position(from.file(), from.rank() + 2*x);
                    if(board.pieceAt(doubleJump).isEmpty())
                        possible.add(new Move(from, doubleJump));
                }
            }
        }

        
        for (int df : new int[]{-1, 1}) {
            int newFile = from.file() + df;
            int newRank = from.rank() + x;

            if (!Position.isInside(newFile, newRank)) {
                continue;
            }

            Position to = new Position(newFile, newRank);
            Optional<Piece> target = board.pieceAt(to);

            if (target.isPresent() && target.get().color() != pawn.color()) {
                possible.add(new Move(from, to));
            }
        }

        return possible;
    }
}