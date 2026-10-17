package com.javachess.core;

/* A direction is defined by a 2-dimensional vector */
public record Direction(int df, int dr) {

    public Direction {
        assert(df <= 1 && df >= -1);
        assert(dr <= 1 && dr >= -1);
        
        if (df == 0 && dr == 0) {
            throw new IllegalArgumentException(
                "Direction cannot be zero"
            );
        }
    }
}