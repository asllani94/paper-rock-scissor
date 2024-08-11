package imc.console;

import com.imc.console.ConsoleGameStateDisplay;
import com.imc.core.state.GameState;
import com.imc.core.player.PlayerIdentifiers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConsoleGameStateDisplayTest {

    private ConsoleGameStateDisplay display;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        display = new ConsoleGameStateDisplay();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void testDisplayGameStart() {
        // Given
        GameState mockState = mock(GameState.class);
        when(mockState.getTotalRounds()).thenReturn(3);
        when(mockState.getPlayer1Id()).thenReturn("Player1");
        when(mockState.getPlayer2Id()).thenReturn("Player2");

        // When
        display.displayGameStart(mockState);

        // Then
        String expectedOutput = "Starting 3 round game Player1 vs Player2.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testDisplayRoundInfo() {
        // Given
        GameState mockState = mock(GameState.class);
        when(mockState.getCurrentRound()).thenReturn(1);

        // When
        display.displayRoundInfo(mockState);

        // Then
        String expectedOutput = "Round 1 starts.\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testDisplayMoveInfo() {
        // Given
        String playerId = "Player1";
        String move = "ROCK";

        // When
        display.displayMoveInfo(playerId, move);

        // Then
        String expectedOutput = "Player1 chose ROCK\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testDisplayRoundEndInfo_WithLeader() {
        // Given
        GameState mockState = mock(GameState.class);
        when(mockState.getCurrentRound()).thenReturn(1);
        when(mockState.getPlayer1Id()).thenReturn("Player1");
        when(mockState.getPlayer1Wins()).thenReturn(1);
        when(mockState.getPlayer2Id()).thenReturn("Player2");
        when(mockState.getPlayer2Wins()).thenReturn(0);
        when(mockState.getTies()).thenReturn(0);
        when(mockState.getCurrentLeader()).thenReturn("Player1");

        // When
        display.displayRoundEndInfo(mockState);

        // Then
        String expectedOutput = """
                *********************
                Round 1 ended. Scores:
                Player1: 1 wins
                Player2: 0 wins
                Ties: 0
                Current Leader: Player1
                *********************
                """;
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testDisplayRoundEndInfo_Tied() {
        // Given
        GameState mockState = mock(GameState.class);
        when(mockState.getCurrentRound()).thenReturn(1);
        when(mockState.getPlayer1Id()).thenReturn("Player1");
        when(mockState.getPlayer1Wins()).thenReturn(1);
        when(mockState.getPlayer2Id()).thenReturn("Player2");
        when(mockState.getPlayer2Wins()).thenReturn(1);
        when(mockState.getTies()).thenReturn(1);
        when(mockState.getCurrentLeader()).thenReturn(PlayerIdentifiers.NONE);

        // When
        display.displayRoundEndInfo(mockState);

        // Then
        String expectedOutput = """
                *********************
                Round 1 ended. Scores:
                Player1: 1 wins
                Player2: 1 wins
                Ties: 1
                The game is currently tied!
                *********************
                """;
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testDisplayFinalResults_WithWinner() {
        // Given
        GameState mockState = mock(GameState.class);
        when(mockState.getPlayer1Id()).thenReturn("Player1");
        when(mockState.getPlayer1Wins()).thenReturn(2);
        when(mockState.getPlayer2Id()).thenReturn("Player2");
        when(mockState.getPlayer2Wins()).thenReturn(1);
        when(mockState.getTies()).thenReturn(1);
        when(mockState.getCurrentLeader()).thenReturn("Player1");

        // When
        display.displayFinalResults(mockState);

        // Then
        String expectedOutput = """
                *********************
                Final results:
                Player1: 2 wins
                Player2: 1 wins
                Ties: 1
                WINNER is: Player1
                *********************
                """;
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    void testDisplayFinalResults_Tied() {
        // Given
        GameState mockState = mock(GameState.class);
        when(mockState.getPlayer1Id()).thenReturn("Player1");
        when(mockState.getPlayer1Wins()).thenReturn(1);
        when(mockState.getPlayer2Id()).thenReturn("Player2");
        when(mockState.getPlayer2Wins()).thenReturn(1);
        when(mockState.getTies()).thenReturn(2);
        when(mockState.getCurrentLeader()).thenReturn(PlayerIdentifiers.NONE);

        // When
        display.displayFinalResults(mockState);

        // Then
        String expectedOutput = """
                *********************
                Final results:
                Player1: 1 wins
                Player2: 1 wins
                Ties: 2
                The game is TIED!
                *********************
                """;
        assertEquals(expectedOutput, outputStream.toString());
    }
}
