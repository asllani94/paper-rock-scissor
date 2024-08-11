package imc.core.player.moves;


import com.imc.core.player.moves.Move;
import com.imc.core.player.moves.MoveStrategy;
import com.imc.core.player.moves.RandomMoveStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomMoveStrategyTest {

    private MoveStrategy randomMoveStrategy;

    @BeforeEach
    void setUp() {
        randomMoveStrategy = new RandomMoveStrategy();
    }

    @Test
    void testGetMoveReturnsValidMove() {
        // When
        Move move = randomMoveStrategy.getMove();

        // Then
        assertTrue(EnumSet.allOf(Move.class).contains(move), "Move should be one of the valid Move enum values.");
    }
}