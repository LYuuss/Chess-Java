package com.javachess.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public final class KnightMoveGenerator implements MoveGenerator {

    public static boolean isInside(int value){
        return 0 <= value && value < 8;
    }

    @Override 
    public List<Move> generate(Board board, Position from){
        List<Move> possible = new ArrayList<>();

        int fileSource = from.file();
        int rankSource = from.rank();

        Piece knight = board.pieceAt(from).orElseThrow();

        int[] fileVisit = {-2,-1,1,2};
        int[] rankSigns = {-1, 1};
        for(int df : fileVisit){
            int dr = 3 - Math.abs(df);
            
            for(int sr : rankSigns){
                int newFile = fileSource + df;
                int newRank = rankSource + dr;

                if(!isInside(newFile) || !isInside(newRank)) continue;

                Position to = new Position(newFile, newRank);

                Optional<Piece> dest = board.pieceAt(to);

                if(dest.isEmpty() || !dest.get().color().equals(knight.color())) 
                    possible.add(new Move(from, to)); 
            }
        }
        
        return possible;
    }
}
