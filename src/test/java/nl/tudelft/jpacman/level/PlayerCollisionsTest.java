package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PlayerCollisionsTest {
    PlayerCollisions cmap;
    PointCalculator pc;
    private Player player1 = mock(Player.class);
    private Player player2 = mock(Player.class);
    private Pellet pellet1 = mock(Pellet.class);
    private Pellet pellet2 = mock(Pellet.class);
    private Ghost ghost1 = mock(Ghost.class);
    private Ghost ghost2 = mock(Ghost.class);

    @BeforeEach
    void setUp() {
        pc = mock(PointCalculator.class);
        cmap = new PlayerCollisions(pc);

    }

    @Test
    void testCollidePelletVsPellet () {
        cmap.collide(pellet1, pellet2);

        verifyZeroInteractions(pc);
    }

    @Test
    void testCollideGhostVsPellet () {
        cmap.collide(ghost1, pellet1);

        verifyZeroInteractions(pc);
    }

    @Test
    void testCollidePlayerVsPlayer () {
        cmap.collide(player1, player2);

        verifyZeroInteractions(pc);
    }

    @Test
    void testCollidePlayerVsGhost () {
        cmap.collide(player1, ghost1);

        verify(pc).collidedWithAGhost(player1, ghost1);
        verify(player1).setAlive(false);
        verify(player1).setKiller(ghost1);
    }

    @Test
    void testCollidePlayerVsPellet () {
        cmap.collide(player1, pellet1);

        verify(pc).consumedAPellet(player1, pellet1);
        verify(pellet1).leaveSquare();
    }

    @Test
    void testCollideGhostVsGhost () {
        cmap.collide(ghost1, ghost2);

        verifyZeroInteractions(pc);
    }
}