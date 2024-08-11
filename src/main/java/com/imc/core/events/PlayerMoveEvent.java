package com.imc.core.events;

import com.imc.core.player.moves.Move;

public class PlayerMoveEvent extends GameEvent {
    private final String playerId;
    private final Move move;

    public PlayerMoveEvent(String playerId, Move move) {
        super(EventType.PLAYER_MOVE);
        this.playerId = playerId;
        this.move = move;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Move getMove() {
        return move;
    }
}