package com.imc.console;

import com.imc.core.player.GamePlayer;
import com.imc.core.player.PlayerIdentifiers;
import com.imc.core.player.factory.PlayerFactory;
import com.imc.core.player.moves.RandomMoveStrategy;

import java.util.Scanner;

public class HumanVsAgentFactory implements PlayerFactory {

    @Override
    public GamePlayer createPlayer1() {
        return new GamePlayer(PlayerIdentifiers.PLAYER, new ConsoleUserInputMoveStrategy(new Scanner(System.in)));
    }

    @Override
    public GamePlayer createPlayer2() {
        return new GamePlayer(PlayerIdentifiers.AGENT, new RandomMoveStrategy());
    }
}
