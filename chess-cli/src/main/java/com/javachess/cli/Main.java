package com.javachess.cli;

import com.javachess.core.Position;

public final class Main {
    private Main() { }

    public static void main(String[] args) {
        System.out.println("Java Chess — " + Position.fromAlgebraic("e2").toAlgebraic());
        System.out.println("Moteur en construction.");
    }
}
