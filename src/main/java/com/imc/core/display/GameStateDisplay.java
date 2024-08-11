package com.imc.core.display;

import com.imc.core.state.GameState;

public interface GameStateDisplay {
    void displayGameStart(GameState state);
    void displayRoundInfo(GameState state);
    void displayMoveInfo(String playerId, String move);
    void displayRoundEndInfo(GameState state);
    void displayFinalResults(GameState state);
}
