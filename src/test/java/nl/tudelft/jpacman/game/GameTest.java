package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Level.LevelObserver;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {
    private Player mockedPlayer = mock(Player.class);
    private Level mockedLevel = mock(Level.class);
    private PointCalculator mockedPointCalculator = mock(PointCalculator.class);
    private SinglePlayerGame mockedGame = spy(new SinglePlayerGame(mockedPlayer, mockedLevel, mockedPointCalculator));


    @Test
    void testGameStartInProgress () {
        when(mockedGame.isInProgress()).thenReturn(true);
        mockedGame.start();

        verify(mockedGame).isInProgress();
    }

    @Test
    void testGameStartHasPelletsAndAlivePlayer () {
        when(mockedGame.getLevel()).thenReturn(mockedLevel);
        when(mockedLevel.isAnyPlayerAlive()).thenReturn(true);
        when(mockedLevel.remainingPellets()).thenReturn(5);

        mockedGame.start();

        verify(mockedLevel).isAnyPlayerAlive();
        verify(mockedLevel).remainingPellets();
    }
}