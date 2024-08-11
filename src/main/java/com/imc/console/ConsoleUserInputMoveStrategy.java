package com.imc.console;

import com.imc.core.player.moves.Move;
import com.imc.core.player.moves.MoveConstants;
import com.imc.core.player.moves.MoveStrategy;

import java.util.Scanner;

public class ConsoleUserInputMoveStrategy implements MoveStrategy {
    private final Scanner scanner;

    public ConsoleUserInputMoveStrategy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Move getMove() {
        Move move = null;
        while (move == null) {
            System.out.println("Enter your move [Rock, Paper, Scissors] or 1 for Rock, 2 for Paper, 3 for Scissors): ");
            try {
                String input = scanner.nextLine().trim().toUpperCase();
                move = parseMove(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid move. Please enter Rock, Paper, Scissors, or their corresponding numbers.");
            }
        }
        return move;
    }

    private Move parseMove(String input) {
        return switch (input) {
            case MoveConstants.ROCK_STRING, MoveConstants.ROCK_NUMBER -> Move.ROCK;
            case MoveConstants.PAPER_STRING, MoveConstants.PAPER_NUMBER -> Move.PAPER;
            case MoveConstants.SCISSORS_STRING, MoveConstants.SCISSORS_NUMBER -> Move.SCISSORS;
            default -> throw new IllegalArgumentException("Invalid move: " + input);
        };
    }
}
