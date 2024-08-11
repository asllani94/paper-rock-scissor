package com.imc.core.setup;

import com.imc.core.player.factory.PlayerFactory;

public interface GameSetup {

     GameMode getGameMode();

     int getRounds();

     PlayerFactory getPlayerFactory();
}
