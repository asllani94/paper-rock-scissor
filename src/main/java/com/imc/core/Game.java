package com.imc.core;

import com.imc.core.setup.GameSetup;
import com.imc.core.events.*;
import com.imc.core.state.GameObserver;
import com.imc.core.state.GameSubject;
import com.imc.core.player.GamePlayer;
import com.imc.core.player.PlayerIdentifiers;
import com.imc.core.player.factory.PlayerFactory;
import com.imc.core.player.moves.Move;

import java.util.ArrayList;
import java.util.List;

public class Game implements GameSubject {
    private final List<GameObserver> observers = new ArrayList<>();
    private final GamePlayer player1;
    private final GamePlayer player2;

    private final int rounds;

    public Game(GameSetup gameSetup) {
        this.rounds = gameSetup.getRounds();

        PlayerFactory factory = gameSetup.getPlayerFactory();
        this.player1 = factory.createPlayer1();
        this.player2 = factory.createPlayer2();
    }

    public void start() {
        notifyObservers(new GameStartEvent(player1.getPlayerId(), player2.getPlayerId(), this.rounds));

        for (int currentRound = 1; currentRound <= rounds; currentRound++) {
            playRound(currentRound);
        }

        notifyObservers(new GameEndEvent());
    }


    private void playRound(int roundNumber) {
        notifyObservers(new RoundStartEvent(roundNumber));

        Move player1Move = player1.getMove();
        notifyObservers(new PlayerMoveEvent(player1.getPlayerId(), player1Move));

        Move player2Move = player2.getMove();
        notifyObservers(new PlayerMoveEvent(player2.getPlayerId(), player2Move));

        notifyObservers(determineRoundWinner(roundNumber, player1Move, player2Move));
    }

    @Override
    public void registerObserver(GameObserver observer) {
        observers.add(observer);

    }

    @Override
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);

    }

    @Override
    public void notifyObservers(GameEvent event) {
        for (GameObserver observer : observers) {
            observer.update(event);
        }
    }

    private GameEvent determineRoundWinner(int roundNumber, Move player1Move, Move player2Move) {
        String winnerId = PlayerIdentifiers.NONE;

        if (player1Move == player2Move) {
            return new RoundEndEvent(winnerId, roundNumber, player1Move, player2Move);
        }

        winnerId = switch (player1Move) {
            case ROCK -> (player2Move == Move.SCISSORS) ? player1.getPlayerId() : player2.getPlayerId();
            case PAPER -> (player2Move == Move.ROCK) ? player1.getPlayerId() : player2.getPlayerId();
            case SCISSORS -> (player2Move == Move.PAPER) ? player1.getPlayerId() : player2.getPlayerId();
        };

        return new RoundEndEvent(winnerId,roundNumber, player1Move, player2Move);
    }

}
