package com.javachess.core;

import java.util.List;

public interface MoveGenerator {
    List<Move> generate(Board board, Position from);
}