package com.imc.core.events;

import com.imc.core.player.moves.Move;

public class RoundEndEvent extends GameEvent {
    private final String winnerId;
    private final int roundNumber;
    private final Move player1Move;
    private final Move player2Move;

    public RoundEndEvent(String winnerId, int roundNumber, Move player1Move, Move player2Move) {
        super(EventType.ROUND_END);
        this.winnerId = winnerId;
        this.roundNumber = roundNumber;
        this.player1Move = player1Move;
        this.player2Move = player2Move;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public Move getPlayer1Move() {
        return player1Move;
    }

    public Move getPlayer2Move() {
        return player2Move;
    }
    public int getRoundNumber() {
        return roundNumber;
    }

}