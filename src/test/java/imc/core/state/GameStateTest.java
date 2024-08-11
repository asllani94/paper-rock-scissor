package imc.core.state;
import com.imc.core.player.moves.Move;
import com.imc.core.display.GameStateDisplay;
import com.imc.core.events.*;
import com.imc.core.state.GameState;
import com.imc.core.state.GameStateConstants;
import com.imc.core.player.PlayerIdentifiers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class GameStateTest {

    private GameState gameState;
    private GameStateDisplay mockDisplay;

    @BeforeEach
    void setUp() {
        mockDisplay = mock(GameStateDisplay.class);
        gameState = new GameState(mockDisplay);
    }

    @Test
    void testGameStartEventUpdatesPlayerIds() {
        // Given
        GameStartEvent event = new GameStartEvent("Player1", "Player2", 3);

        // When
        gameState.update(event);

        // Then
        assertEquals("Player1", gameState.getPlayer1Id());
        assertEquals("Player2", gameState.getPlayer2Id());
        assertEquals(3, gameState.getTotalRounds());

        // No changes
        assertEquals(GameStateConstants.DEFAULT_PLAYER_WINS, gameState.getPlayer1Wins());
        assertEquals(GameStateConstants.DEFAULT_PLAYER_WINS, gameState.getPlayer2Wins());
        assertEquals(GameStateConstants.DEFAULT_TIES, gameState.getTies());
        assertEquals(GameStateConstants.DEFAULT_ROUND, gameState.getCurrentRound());
        assertEquals(PlayerIdentifiers.NONE, gameState.getCurrentLeader());

        verify(mockDisplay, times(1)).displayGameStart(gameState);
    }

    @Test
    void testRoundStartEventCallsDisplayRoundInfo() {
        // Given
        RoundStartEvent event = new RoundStartEvent(1);

        // When
        gameState.update(event);

        // Then
        assertEquals(1, gameState.getCurrentRound());

        assertEquals(GameStateConstants.DEFAULT_ROUND, gameState.getTotalRounds());
        assertEquals(GameStateConstants.DEFAULT_PLAYER_WINS, gameState.getPlayer1Wins());
        assertEquals(GameStateConstants.DEFAULT_PLAYER_WINS, gameState.getPlayer2Wins());
        assertEquals(GameStateConstants.DEFAULT_TIES, gameState.getTies());
        assertEquals(PlayerIdentifiers.NONE, gameState.getCurrentLeader());
        verify(mockDisplay, times(1)).displayRoundInfo(gameState);
    }

    @Test
    void testPlayerMoveEventCallsDisplayMoveInfo() {
        // Given
        PlayerMoveEvent event = new PlayerMoveEvent("Player1", Move.ROCK);

        // When
        gameState.update(event);

        // Then
        assertEquals(GameStateConstants.DEFAULT_ROUND, gameState.getCurrentRound());
        assertEquals(GameStateConstants.DEFAULT_ROUND, gameState.getTotalRounds());
        assertEquals(GameStateConstants.DEFAULT_PLAYER_WINS, gameState.getPlayer1Wins());
        assertEquals(GameStateConstants.DEFAULT_PLAYER_WINS, gameState.getPlayer2Wins());
        assertEquals(GameStateConstants.DEFAULT_TIES, gameState.getTies());
        assertEquals(PlayerIdentifiers.NONE, gameState.getCurrentLeader());
        verify(mockDisplay, times(1)).displayMoveInfo("Player1", "ROCK");
    }

    @Test
    void testRoundEndEventUpdatesWinsAndCallsDisplayRoundEndInfo() {
        // Given
        GameStartEvent startEvent = new GameStartEvent("Player1", "Player2", 3);
        gameState.update(startEvent);

        RoundEndEvent event1 = new RoundEndEvent("Player1", 1, Move.ROCK, Move.SCISSORS);
        RoundEndEvent event2 = new RoundEndEvent("Player2", 2, Move.ROCK, Move.PAPER);
        RoundEndEvent event3 = new RoundEndEvent(PlayerIdentifiers.NONE, 3, Move.ROCK, Move.ROCK);

        // When
        gameState.update(event1);
        gameState.update(event2);
        gameState.update(event3);

        // Then
        assertEquals(1, gameState.getPlayer1Wins());
        assertEquals(1, gameState.getPlayer2Wins());
        assertEquals(1, gameState.getTies());
        assertEquals(PlayerIdentifiers.NONE, gameState.getCurrentLeader());

        verify(mockDisplay, times(3)).displayRoundEndInfo(gameState);
    }



    @Test
    void testGameEndEventCallsDisplayFinalResults() {
        // Given
        GameEndEvent event = new GameEndEvent();

        // When
        gameState.update(event);

        // Then

        // No change in state
        assertEquals(GameStateConstants.DEFAULT_ROUND, gameState.getCurrentRound());
        assertEquals(GameStateConstants.DEFAULT_ROUND, gameState.getTotalRounds());
        assertEquals(GameStateConstants.DEFAULT_PLAYER_WINS, gameState.getPlayer1Wins());
        assertEquals(GameStateConstants.DEFAULT_PLAYER_WINS, gameState.getPlayer2Wins());
        assertEquals(GameStateConstants.DEFAULT_TIES, gameState.getTies());
        assertEquals(PlayerIdentifiers.NONE, gameState.getCurrentLeader());

        verify(mockDisplay, times(1)).displayFinalResults(gameState);
    }

    @Test
    void testRoundEndEventWithUnknownPlayerThrowsException() {
        // Given
        GameStartEvent startEvent = new GameStartEvent("Player1", "Player2", 3);
        gameState.update(startEvent);

        RoundEndEvent event = new RoundEndEvent("UnknownPlayer", 1, Move.ROCK, Move.SCISSORS);

        // When & Assert
        assertThrows(IllegalArgumentException.class, () -> gameState.update(event));
    }

    @Test
    void testGetCurrentLeaderReturnsCorrectLeader() {
        // Given
        GameStartEvent startEvent = new GameStartEvent("Player1", "Player2", 3);
        gameState.update(startEvent);

        RoundEndEvent event1 = new RoundEndEvent("Player1", 1, Move.ROCK, Move.SCISSORS);
        RoundEndEvent event2 = new RoundEndEvent("Player1", 2, Move.PAPER, Move.ROCK);
        GameEndEvent event3 = new GameEndEvent();

        // When
        gameState.update(event1);
        gameState.update(event2);
        gameState.update(event3);

        // Then
        assertEquals("Player1", gameState.getCurrentLeader());
    }

    @Test
    void testGetCurrentLeaderWhenTiedReturnsNone() {
        // Given
        GameStartEvent startEvent = new GameStartEvent("Player1", "Player2", 3);
        gameState.update(startEvent);

        RoundEndEvent event1 = new RoundEndEvent("Player1", 1, Move.ROCK, Move.SCISSORS);
        RoundEndEvent event2 = new RoundEndEvent("Player2", 2, Move.PAPER, Move.ROCK);
        GameEndEvent event3 = new GameEndEvent();

        // When
        gameState.update(event1);
        gameState.update(event2);
        gameState.update(event3);

        // Then
        assertEquals(PlayerIdentifiers.NONE, gameState.getCurrentLeader());
    }
}