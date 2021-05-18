package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.LevelFactory;

import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MapParserTest {
//    void AssertThrows(PacmanConfigurationException.class, () -> {mapParser(List.of("!"))});
    private BoardFactory mockedBoardFactory = mock(BoardFactory.class);
    private LevelFactory mockedLevelFactory = mock(LevelFactory.class);
    private Pellet mockedPellet = mock(Pellet.class);
    private Square mockedSquare = mock(Square.class);
    private Ghost mockedGhost = mock(Ghost.class);

    private MapParser mapParser;

    @BeforeEach
    void setUp () {
        mapParser =  new MapParser(mockedLevelFactory, mockedBoardFactory);
    }
    @Test
    void testCreateLevelBoard () {
        mapParser.parseMap(List.of(" "));

        verify(mockedBoardFactory).createGround();
        verify(mockedLevelFactory).createLevel(any(), anyList(), anyList());
        verify(mockedBoardFactory).createBoard(any());
    }

    @Test
    void testParseBoardWithWall () {
        mapParser.parseMap(List.of("#"));

        verify(mockedBoardFactory, times(1)).createWall();
        verify(mockedLevelFactory, times(1)).createLevel(any(), anyList(), anyList());
        verify(mockedBoardFactory, times(1)).createBoard(any());
    }

    @Test
    void testParseBoardWithPlayer () {
        mapParser.parseMap(List.of(" P"));

        verify(mockedBoardFactory, times(2)).createGround();
    }

    @Test
    void testParseBoardWithPellet () {
        when(mockedLevelFactory.createPellet()).thenReturn(mockedPellet);

        mapParser.parseMap(List.of("."));

        verify(mockedLevelFactory, times(1)).createPellet();
        verify(mockedPellet).occupy(any());
    }

    @Test
    void testParseBoardWithGhost () {
        when(mockedLevelFactory.createGhost()).thenReturn(mockedGhost);
        mapParser.parseMap(List.of(" G"));

        verify(mockedLevelFactory, times(1)).createGhost();
        verify(mockedGhost).occupy(any());

    }

    @Test
    void testBoardOneSpace () {
        mapParser.parseMap(List.of(" "));

        verify(mockedBoardFactory).createGround();
        verify(mockedLevelFactory).createLevel(any(), anyList(), anyList());
        verify(mockedBoardFactory).createBoard(any());
        verifyNoMoreInteractions(mockedBoardFactory);
        verifyNoMoreInteractions(mockedLevelFactory);
    }

    @Test
    void testParseEmptyStringBoard () {
        assertThrows(PacmanConfigurationException.class, () -> mapParser.parseMap(List.of("")));
    }

    @Test
    void testParseInvalidMapBoard () {
        assertThrows(PacmanConfigurationException.class, () -> mapParser.parseMap(List.of("?")));
    }

    @Test
    void testInvalidLengthMap () {
        assertThrows(
                PacmanConfigurationException.class,
                () -> mapParser.parseMap(
                        List.of(
                                "############",
                                "#.....",
                                "############"
                        )
                )
        );
    }
}