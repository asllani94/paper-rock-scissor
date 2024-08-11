package com.imc;

import com.imc.core.Game;
import com.imc.console.ConsoleGameSetup;
import com.imc.core.setup.GameSetup;
import com.imc.core.state.GameState;
import com.imc.console.ConsoleGameStateDisplay;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        GameSetup gameSetup = new ConsoleGameSetup(new Scanner(System.in));

        Game game = new Game(gameSetup);
        GameState gameState = new GameState(new ConsoleGameStateDisplay());
        game.registerObserver(gameState);

        
        game.start();
    }
}