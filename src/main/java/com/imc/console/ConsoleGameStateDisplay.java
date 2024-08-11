package com.imc.console;

import com.imc.core.display.GameStateDisplay;
import com.imc.core.state.GameState;
import com.imc.core.player.PlayerIdentifiers;

public class ConsoleGameStateDisplay implements GameStateDisplay {

    @Override
    public void displayGameStart(GameState state) {
        System.out.printf("Starting %d round game %s vs %s.%n", state.getTotalRounds(), state.getPlayer1Id(), state.getPlayer2Id());
    }

    @Override
    public void displayRoundInfo(GameState state) {
        System.out.printf("Round %d starts.%n", state.getCurrentRound());
    }

    @Override
    public void displayMoveInfo(String playerId, String move) {
        System.out.printf("%s chose %s%n", playerId, move);
    }

    @Override
    public void displayRoundEndInfo(GameState state) {
        System.out.println("*********************");
        System.out.printf("Round %d ended. Scores:%n", state.getCurrentRound());
        System.out.printf("%s: %d wins%n", state.getPlayer1Id(), state.getPlayer1Wins());
        System.out.printf("%s: %d wins%n", state.getPlayer2Id(), state.getPlayer2Wins());
        System.out.printf("Ties: %d%n", state.getTies());

        String currentLeader = state.getCurrentLeader();
        if (currentLeader.equals(PlayerIdentifiers.NONE)) {
            System.out.printf("The game is currently tied!%n");
        } else {
            System.out.printf("Current Leader: %s%n", currentLeader);
        }

        System.out.println("*********************");
    }

    @Override
    public void displayFinalResults(GameState state) {
        System.out.println("*********************");
        System.out.printf("Final results:%n");
        System.out.printf("%s: %d wins%n", state.getPlayer1Id(), state.getPlayer1Wins());
        System.out.printf("%s: %d wins%n", state.getPlayer2Id(), state.getPlayer2Wins());
        System.out.printf("Ties: %d%n", state.getTies());

        String currentLeader = state.getCurrentLeader();
        if (currentLeader.equals(PlayerIdentifiers.NONE)) {
            System.out.printf("The game is TIED!%n");
        } else {
            System.out.printf("WINNER is: %s%n", currentLeader);
        }

        System.out.println("*********************");
    }
}
