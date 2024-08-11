package imc.console;

import com.imc.console.ConsoleUserInputMoveStrategy;
import com.imc.console.HumanVsAgentFactory;
import com.imc.core.player.GamePlayer;
import com.imc.core.player.PlayerIdentifiers;
import com.imc.core.player.moves.RandomMoveStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HumanVsAgentFactoryTest {

    private HumanVsAgentFactory factory;

    @BeforeEach
    void setUp() {
        factory = new HumanVsAgentFactory();
    }

    @Test
    void testCreatePlayer1() {
        // Given
        // HumanVsAgentFactory is initialized in setup

        // When
        GamePlayer player1 = factory.createPlayer1();

        // Then
        assertEquals(PlayerIdentifiers.PLAYER, player1.getPlayerId());
        assertInstanceOf(ConsoleUserInputMoveStrategy.class, player1.getMoveStrategy());
    }

    @Test
    void testCreatePlayer2() {
        // Given
        // HumanVsAgentFactory is initialized in setup

        // When
        GamePlayer player2 = factory.createPlayer2();

        // Then
        assertEquals(PlayerIdentifiers.AGENT, player2.getPlayerId());
        assertInstanceOf(RandomMoveStrategy.class, player2.getMoveStrategy());
    }
}
