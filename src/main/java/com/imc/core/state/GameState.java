package com.imc.core.state;

import com.imc.core.events.*;
import com.imc.core.player.PlayerIdentifiers;
import com.imc.core.display.GameStateDisplay;

public class GameState implements GameObserver {
    private String player1Id = PlayerIdentifiers.NONE;
    private String player2Id = PlayerIdentifiers.NONE;

    private int player1Wins = GameStateConstants.DEFAULT_PLAYER_WINS;
    private int player2Wins = GameStateConstants.DEFAULT_PLAYER_WINS;
    private int ties = GameStateConstants.DEFAULT_TIES;
    private int totalRounds = GameStateConstants.DEFAULT_ROUND;
    private int currentRound = GameStateConstants.DEFAULT_ROUND;

    private final GameStateDisplay presenter;

    public GameState(GameStateDisplay presenter) {
        this.presenter = presenter;
    }

    @Override
    public void update(GameEvent event) {
        switch (event.getEventType()) {
            case GAME_START -> {
                GameStartEvent gameStartEvent = (GameStartEvent) event;
                this.player1Id = gameStartEvent.getPlayerId1();
                this.player2Id = gameStartEvent.getPlayerId2();
                this.totalRounds = gameStartEvent.getRounds();
                presenter.displayGameStart( this);
            }
            case ROUND_START -> {
                RoundStartEvent roundStartEvent = (RoundStartEvent) event;
                currentRound = roundStartEvent.getRoundNumber();

                presenter.displayRoundInfo( this);
            }
            case PLAYER_MOVE -> {
                PlayerMoveEvent playerMoveEvent = (PlayerMoveEvent) event;
                presenter.displayMoveInfo(playerMoveEvent.getPlayerId(), playerMoveEvent.getMove().name());
            }
            case ROUND_END -> {
                RoundEndEvent roundEndEvent = (RoundEndEvent) event;
                if (roundEndEvent.getWinnerId().equals(PlayerIdentifiers.NONE)) {
                    ties++;
                } else if (roundEndEvent.getWinnerId().equals(player1Id)) {
                    player1Wins++;
                } else if (roundEndEvent.getWinnerId().equals(player2Id)) {
                    player2Wins++;
                } else {
                    throw new IllegalArgumentException("Unknown playerId");
                }
                presenter.displayRoundEndInfo(this);
            }
            case GAME_END -> presenter.displayFinalResults(this);
        }
    }

    public int getTies() {
        return ties;
    }

    public int getPlayer2Wins() {
        return player2Wins;
    }

    public int getPlayer1Wins() {
        return player1Wins;
    }

    public String getPlayer1Id() {
        return player1Id;
    }

    public String getPlayer2Id() {
        return player2Id;
    }

    public String getCurrentLeader() {
        if (player1Wins > player2Wins) {
            return player1Id;
        } else if (player2Wins > player1Wins) {
            return player2Id;
        } else {
            return PlayerIdentifiers.NONE;
        }
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public int getCurrentRound() {
        return currentRound;
    }
}