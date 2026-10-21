package com.javachess.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public final class SlidingMoveGenerator implements MoveGenerator {

    private static final List<PieceType> sliders = List.of(
        PieceType.ROOK,
        PieceType.BISHOP,
        PieceType.QUEEN
    );

    private final List<Direction> directions;

    public SlidingMoveGenerator(List<Direction> directions) {
        this.directions = List.copyOf(directions);
    }

    @Override
    public List<Move> generate(Board board, Position from) {
        Piece slider = board.pieceAt(from).orElseThrow();

        if(!sliders.contains(slider.type())){ 
            throw new IllegalArgumentException("Expected a slider");
        }

        List<Move> possible = new ArrayList<>();

        for(Direction direction : this.directions){
            Position current = from;

            while(true){
                int newFile = current.file() + direction.df();
                int newRank = current.rank() + direction.dr();

                if (!Position.isInside(newFile, newRank)) break;
                
                Position step = new Position(
                    newFile,
                    newRank
                );

                Optional<Piece> place = board.pieceAt(step); 

                if(place.isEmpty()){
                    possible.add(
                        new Move(from, step)
                    );
                } 
                else if (!place.get().color().equals(slider.color())) {
                    possible.add(
                        new Move(from, step)
                    );
                    break;
                }
                else {
                    break;
                }

                current = step;
            }
        }

        return possible;
    }
}