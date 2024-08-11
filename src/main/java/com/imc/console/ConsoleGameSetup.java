package com.imc.console;

import com.imc.core.setup.GameMode;
import com.imc.core.setup.GameSetup;
import com.imc.core.player.factory.PlayerFactory;

import java.util.Scanner;

import static com.imc.core.setup.GameMode.VS_AGENT;

public class ConsoleGameSetup implements GameSetup {
    private final Scanner scanner;

    public ConsoleGameSetup(Scanner scanner) {
        this.scanner = scanner;
    }


    @Override
    public GameMode getGameMode() {
        return GameMode.VS_AGENT;
    }

    @Override
    public int getRounds() {
        int rounds;
        do {
            System.out.println("Enter the number of rounds: ");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a valid number. Please enter a positive integer:");
                scanner.next(); // Clear the invalid input
            }
            rounds = scanner.nextInt();
        } while (rounds <= 0);

        return rounds;
    }

    @Override
    public PlayerFactory getPlayerFactory() {
        if (getGameMode() == VS_AGENT) {
            return new HumanVsAgentFactory();
        } else {
            throw new IllegalArgumentException("Invalid game mode");
        }
    }
}
