package com.imc.core.player.moves;

import java.util.Random;


public class RandomMoveStrategy implements MoveStrategy {
    private final Random random = new Random();

    @Override
    public Move getMove() {
        Move[] moves = Move.values();
        return moves[random.nextInt(moves.length)];
    }
}