package com.javachess.core;

import java.util.List;
import java.util.ArrayList;

public final class KnightMoveGenerator implements MoveGenerator {

    public static boolean isInside(int value){
        return 0 <= value && value < 8;
    }

    @Override
    public List<Move> generate(Board board, Position from) {
        List<Move> possible = new ArrayList<>();

        int fileSource = from.file();
        int rankSource = from.rank();

        Piece knight = board.pieceAt(from).orElseThrow();

        if(isInside(fileSource + 2) && isInside(rankSource + 1)) {
            Position to = new Position(fileSource + 2, rankSource + 1);

            Optional<Piece> dest = board.pieceAt(to);

            if(dest.isEmpty() || !dest.get().color().equals(knight.color())) 
                possible.add(new Move(from, to));                
        }

        return possible;
    }

    @Override 
    public List<Move> generate(Board board, Position from){
        List<Move> possible = new ArrayList<>();

        int fileSource = from.file();
        int rankSource = from.rank();

        int[] fileVisit = {-2,-1,1,2};
        int[] rankSigns = {-1, 1};
        for(int df : fileVisit){
            int dr = 3 - Math.abs(f);
            
            for(int sr : rankSigns){
                int newFile = fileSource + df;
                int newRank = rankSource + dr;

                if(!isInside(newFile) || !isInside(newRank)) continue;

                Position to = new Position(newFile, newRank);

                Optional<Piece> dest = board.pieceAt(to);

                if(dest.isEmpty() || !dest.get().color().equals(knight.color())) 
                    possible.add(new Move(from, to)); 
            }
            
            if(isInside(fileSource + f) && isInside(rankSource + r)) {
                Position to = new Position(fileSource + f, rankSource + r);

                Optional<Piece> dest = board.pieceAt(to);

                if(dest.isEmpty() || !dest.get().color().equals(knight.color())) 
                    possible.add(new Move(from, to)); 
            }
            if(isInside(fileSource + f) && isInside(rankSource - r) ){
                Position to = new Position(fileSource + f, rankSource - r);

                Optional<Piece> dest = board.pieceAt(to);

                if(dest.isEmpty() || !dest.get().color().equals(knight.color())) 
                    possible.add(new Move(from, to)); 
            }

        }
        
        return possible;
    }
}
