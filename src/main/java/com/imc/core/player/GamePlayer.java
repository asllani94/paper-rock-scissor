package com.imc.core.player;

import com.imc.core.player.moves.Move;
import com.imc.core.player.moves.MoveStrategy;

public class GamePlayer {
    private final String playerId;

    private final MoveStrategy moveStrategy;

    public GamePlayer(String playerId, MoveStrategy moveStrategy) {
        this.playerId = playerId;
        this.moveStrategy = moveStrategy;
    }

    public Move getMove() {
        return moveStrategy.getMove();
    }

    public String getPlayerId() {
        return playerId;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }
}
