package com.imc.core.events;

public class RoundStartEvent extends GameEvent {
    private final int roundNumber;

    public RoundStartEvent(int roundNumber) {
        super(EventType.ROUND_START);
        this.roundNumber = roundNumber;
    }

    public int getRoundNumber() {
        return roundNumber;
    }
}
