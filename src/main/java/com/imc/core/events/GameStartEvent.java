package com.imc.core.events;

public class GameStartEvent extends GameEvent {
    private final String playerId1;
    private final String playerId2;
    private  int rounds;

    public GameStartEvent(String playerId1, String playerId2, int rounds) {
        super(EventType.GAME_START);
        this.playerId1 = playerId1;
        this.playerId2 = playerId2;
        this.rounds = rounds;
    }

    public String getPlayerId1() {
        return playerId1;
    }

    public String getPlayerId2() {
        return playerId2;
    }

    public int getRounds() {
        return rounds;
    }
}

