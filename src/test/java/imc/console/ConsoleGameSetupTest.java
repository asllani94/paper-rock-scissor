package imc.console;

import com.imc.console.ConsoleGameSetup;
import com.imc.console.HumanVsAgentFactory;
import com.imc.core.setup.GameMode;
import com.imc.core.player.factory.PlayerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConsoleGameSetupTest {

    @Mock
    private Scanner mockScanner;
    private ConsoleGameSetup consoleGameSetup;

    @BeforeEach
    void setUp() {
        mockScanner = mock(Scanner.class);
        consoleGameSetup = new ConsoleGameSetup(mockScanner);
    }

    @Test
    void testGetGameModeReturnsVsAgent() {
        // Given & When
        GameMode gameMode = consoleGameSetup.getGameMode();

        // Then
        assertEquals(GameMode.VS_AGENT, gameMode);
    }

    @Test
    void testGetRoundsValidInput() {
        // Given
        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt()).thenReturn(3);

        // When
        int rounds = consoleGameSetup.getRounds();

        // Then
        assertEquals(3, rounds);
        verify(mockScanner, times(1)).nextInt();
    }

    @Test
    void testGetRoundsInvalidThenValidInput() {
        // Given
        when(mockScanner.hasNextInt()).thenReturn(false, true);
        when(mockScanner.next()).thenReturn("invalid");
        when(mockScanner.nextInt()).thenReturn(3);

        // When
        int rounds = consoleGameSetup.getRounds();

        // Then
        assertEquals(3, rounds);
        verify(mockScanner, times(2)).hasNextInt();
        verify(mockScanner, times(1)).next(); // invalid input
        verify(mockScanner, times(1)).nextInt();
    }

    @Test
    void testGetRoundsWithNegativeInput() {
        // Given
        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt()).thenReturn(-1, 2);

        // When
        int rounds = consoleGameSetup.getRounds();

        // Then
        assertEquals(2, rounds);
        verify(mockScanner, times(2)).nextInt();
    }

    @Test
    void testGetPlayerFactoryReturnsHumanVsAgentFactory() {
        // Given & When
        PlayerFactory playerFactory = consoleGameSetup.getPlayerFactory();

        // Then
        assertEquals(HumanVsAgentFactory.class, playerFactory.getClass());
    }
}