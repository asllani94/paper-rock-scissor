package imc.core;


import com.imc.core.Game;
import com.imc.core.player.moves.Move;
import com.imc.core.events.*;
import com.imc.core.setup.GameSetup;
import com.imc.core.state.GameObserver;
import com.imc.core.player.GamePlayer;
import com.imc.core.player.factory.PlayerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class GameTest {

    @Mock
    private GameSetup gameSetup;

    @Mock
    private GamePlayer player1;

    @Mock
    private GamePlayer player2;

    @Mock
    private PlayerFactory playerFactory;

    @Mock
    private GameObserver gameObserver;

    private Game game;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up mock behaviors
        when(gameSetup.getPlayerFactory()).thenReturn(playerFactory);

        when(playerFactory.createPlayer1()).thenReturn(player1);
        when(playerFactory.createPlayer2()).thenReturn(player2);

        when(player1.getPlayerId()).thenReturn("Player1");
        when(player2.getPlayerId()).thenReturn("Player2");

        when(gameSetup.getRounds()).thenReturn(1);
        // Instantiate the Game class
        game = new Game(gameSetup);
        game.registerObserver(gameObserver);
    }

    @Test
    void testStart_AllEventsTriggered() {
        // Given
        when(player1.getMove()).thenReturn(Move.ROCK);
        when(player2.getMove()).thenReturn(Move.SCISSORS);

        // When
        game.start();

        // Capture events sent to the observer
        ArgumentCaptor<GameEvent> captor = ArgumentCaptor.forClass(GameEvent.class);
        verify(gameObserver, times(6)).update(captor.capture());

        // Then GameStartEvent
        GameStartEvent startEvent = (GameStartEvent) captor.getAllValues().get(0);
        assertEquals(GameStartEvent.class, startEvent.getClass());
        assertEquals("Player1", startEvent.getPlayerId1());
        assertEquals("Player2", startEvent.getPlayerId2());
        assertEquals(1, startEvent.getRounds());

        // Then RoundStartEvent
        RoundStartEvent roundStartEvent = (RoundStartEvent) captor.getAllValues().get(1);
        assertEquals(RoundStartEvent.class, roundStartEvent.getClass());
        assertEquals(1, roundStartEvent.getRoundNumber());

        // Then PlayerMoveEvent for player1
        PlayerMoveEvent player1MoveEvent = (PlayerMoveEvent) captor.getAllValues().get(2);
        assertEquals(PlayerMoveEvent.class, player1MoveEvent.getClass());
        assertEquals("Player1", player1MoveEvent.getPlayerId());
        assertEquals(Move.ROCK, player1MoveEvent.getMove());

        // Then PlayerMoveEvent for player2
        PlayerMoveEvent player2MoveEvent = (PlayerMoveEvent) captor.getAllValues().get(3);
        assertEquals(PlayerMoveEvent.class, player2MoveEvent.getClass());
        assertEquals("Player2", player2MoveEvent.getPlayerId());
        assertEquals(Move.SCISSORS, player2MoveEvent.getMove());

        // Then RoundEndEvent
        RoundEndEvent roundEndEvent = (RoundEndEvent) captor.getAllValues().get(4);
        assertEquals(RoundEndEvent.class, roundEndEvent.getClass());
        assertEquals("Player1", roundEndEvent.getWinnerId()); // Based on the moves provided
        assertEquals(1, roundEndEvent.getRoundNumber());
        assertEquals(Move.ROCK, roundEndEvent.getPlayer1Move());
        assertEquals(Move.SCISSORS, roundEndEvent.getPlayer2Move());

        // Then GameEndEvent
        GameEndEvent gameEndEvent = (GameEndEvent) captor.getAllValues().get(5);
        assertEquals(GameEndEvent.class, gameEndEvent.getClass());
    }

    @ParameterizedTest
    @CsvSource({
            "PAPER, ROCK, Player1",
            "ROCK, SCISSORS, Player1",
            "SCISSORS, PAPER, Player1",
            "ROCK, PAPER, Player2",
            "SCISSORS, ROCK, Player2",
            "PAPER, SCISSORS, Player2",
            "PAPER, PAPER, none",
            "ROCK, ROCK, none",
            "SCISSORS, SCISSORS, none",
    })
    void testPlayRound_WinnerEventTriggered(Move player1Move, Move player2Move, String expectedWinnerId) {
        // Given
        when(player1.getMove()).thenReturn(player1Move);
        when(player2.getMove()).thenReturn(player2Move);

        // When
        game.start();

        ArgumentCaptor<GameEvent> captor = ArgumentCaptor.forClass(GameEvent.class);
        verify(gameObserver, times(6)).update(captor.capture());

        // Then
        assertEquals(RoundEndEvent.class, captor.getAllValues().get(4).getClass());
        RoundEndEvent roundEndEvent = (RoundEndEvent) captor.getAllValues().get(4);
        assertEquals(expectedWinnerId, roundEndEvent.getWinnerId());
    }

    @Test
    void testMultipleRoundsLogic() {
        // Given
        int NUMBER_OF_ROUNDS = 3;
        when(gameSetup.getRounds()).thenReturn(NUMBER_OF_ROUNDS);

        // Re-instantiate the Game class
        game = new Game(gameSetup);
        game.registerObserver(gameObserver);

        when(player1.getMove()).thenReturn(Move.ROCK);
        when(player2.getMove()).thenReturn(Move.SCISSORS);

        // When
        game.start();

        ArgumentCaptor<GameEvent> captor = ArgumentCaptor.forClass(GameEvent.class);
        verify(gameObserver, times(14)).update(captor.capture()); // 3 rounds * 4 events + 2 events for start and end = 14

        // Then
        GameStartEvent startEvent = (GameStartEvent) captor.getAllValues().get(0);
        assertEquals(GameStartEvent.class, startEvent.getClass());
        assertEquals("Player1", startEvent.getPlayerId1());
        assertEquals("Player2", startEvent.getPlayerId2());
        assertEquals(NUMBER_OF_ROUNDS, startEvent.getRounds());

        for (int i = 0; i < NUMBER_OF_ROUNDS; i++) {
            int baseIndex = 1 + i * 4; // Calculate base index for each round (4 events per round)

            // Then RoundStartEvent
            RoundStartEvent roundStartEvent = (RoundStartEvent) captor.getAllValues().get(baseIndex);
            assertEquals(RoundStartEvent.class, roundStartEvent.getClass());
            assertEquals(i + 1, roundStartEvent.getRoundNumber());

            // Then PlayerMoveEvent for player1
            PlayerMoveEvent player1MoveEvent = (PlayerMoveEvent) captor.getAllValues().get(baseIndex + 1);
            assertEquals(PlayerMoveEvent.class, player1MoveEvent.getClass());
            assertEquals("Player1", player1MoveEvent.getPlayerId());
            assertEquals(Move.ROCK, player1MoveEvent.getMove());

            // Then PlayerMoveEvent for player2
            PlayerMoveEvent player2MoveEvent = (PlayerMoveEvent) captor.getAllValues().get(baseIndex + 2);
            assertEquals(PlayerMoveEvent.class, player2MoveEvent.getClass());
            assertEquals("Player2", player2MoveEvent.getPlayerId());
            assertEquals(Move.SCISSORS, player2MoveEvent.getMove());

            // Then RoundEndEvent
            RoundEndEvent roundEndEvent = (RoundEndEvent) captor.getAllValues().get(baseIndex + 3);
            assertEquals(RoundEndEvent.class, roundEndEvent.getClass());
            assertEquals("Player1", roundEndEvent.getWinnerId());
            assertEquals(i + 1, roundEndEvent.getRoundNumber());
            assertEquals(Move.ROCK, roundEndEvent.getPlayer1Move());
            assertEquals(Move.SCISSORS, roundEndEvent.getPlayer2Move());
        }

        // Then GameEndEvent
        GameEndEvent gameEndEvent = (GameEndEvent) captor.getAllValues().get(13);
        assertEquals(GameEndEvent.class, gameEndEvent.getClass());
    }

    @Test
    void testNotifyObservers_MethodCalled() {
        // Given
        GameEvent event = new GameStartEvent("Player1", "Player2", 3);

        // When
        game.notifyObservers(event);

        // Then
        verify(gameObserver, times(1)).update(event);
    }

    @Test
    void testRegisterObserver() {
        // Given
        GameObserver newObserver = mock(GameObserver.class);

        // When
        game.registerObserver(newObserver);
        GameEvent event = new GameStartEvent("Player1", "Player2", 3);
        game.notifyObservers(event);

        // Then
        verify(newObserver, times(1)).update(event);
    }

    @Test
    void testRemoveObserver() {
        // Given
        game.removeObserver(gameObserver);
        GameEvent event = new GameStartEvent("Player1", "Player2", 3);

        // When
        game.notifyObservers(event);

        // Then
        verify(gameObserver, never()).update(event);
    }
}
