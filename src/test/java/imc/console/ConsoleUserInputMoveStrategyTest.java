package imc.console;
import com.imc.console.ConsoleUserInputMoveStrategy;
import com.imc.core.player.moves.Move;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleUserInputMoveStrategyTest {

    private ConsoleUserInputMoveStrategy moveStrategy;

    @Test
    void testGetMove_RockByName() {
        // Given
        Scanner scanner = new Scanner("Rock");
        moveStrategy = new ConsoleUserInputMoveStrategy(scanner);

        // When
        Move move = moveStrategy.getMove();

        // Then
        assertEquals(Move.ROCK, move);
    }

    @Test
    void testGetMove_RockByNumber() {
        // Given
        Scanner scanner = new Scanner("1");
        moveStrategy = new ConsoleUserInputMoveStrategy(scanner);

        // When
        Move move = moveStrategy.getMove();

        // Then
        assertEquals(Move.ROCK, move);
    }

    @Test
    void testGetMove_PaperByName() {
        // Given
        Scanner scanner = new Scanner("Paper");
        moveStrategy = new ConsoleUserInputMoveStrategy(scanner);

        // When
        Move move = moveStrategy.getMove();

        // Then
        assertEquals(Move.PAPER, move);
    }

    @Test
    void testGetMove_PaperByNumber() {
        // Given
        Scanner scanner = new Scanner("2");
        moveStrategy = new ConsoleUserInputMoveStrategy(scanner);

        // When
        Move move = moveStrategy.getMove();

        // Then
        assertEquals(Move.PAPER, move);
    }

    @Test
    void testGetMove_ScissorsByName() {
        // Given
        Scanner scanner = new Scanner("Scissors");
        moveStrategy = new ConsoleUserInputMoveStrategy(scanner);

        // When
        Move move = moveStrategy.getMove();

        // Then
        assertEquals(Move.SCISSORS, move);
    }

    @Test
    void testGetMove_ScissorsByNumber() {
        // Given
        Scanner scanner = new Scanner("3");
        moveStrategy = new ConsoleUserInputMoveStrategy(scanner);

        // When
        Move move = moveStrategy.getMove();

        // Then
        assertEquals(Move.SCISSORS, move);
    }

    @Test
    void testGetMove_InvalidInputThenValidInput() {
        // Given
        Scanner scanner = new Scanner("Invalid\nRock");
        moveStrategy = new ConsoleUserInputMoveStrategy(scanner);

        // When
        Move move = moveStrategy.getMove();

        // Then
        assertEquals(Move.ROCK, move);
    }

    @Test
    void testGetMove_EmptyInputThenValidInput() {
        // Given
        Scanner scanner = new Scanner("\nPaper");
        moveStrategy = new ConsoleUserInputMoveStrategy(scanner);

        // When
        Move move = moveStrategy.getMove();

        // Then
        assertEquals(Move.PAPER, move);
    }
}

