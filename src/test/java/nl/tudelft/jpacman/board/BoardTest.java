package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private MapParser parser;
    private Level level;
    private Board board;

    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        PointCalculator pointCalculator = new DefaultPointCalculator();
        GhostFactory ghostFactory = new GhostFactory(sprites);
        BoardFactory boardFactory = new BoardFactory(sprites);
        LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory, pointCalculator);
        parser = new MapParser(levelFactory, boardFactory);
        ArrayList<String> map = new ArrayList<>(List.of(
                "#####",
                "#...#",
                "#...#",
                "#####"
        ));
        level = parser.parseMap(map);
        board = level.getBoard();
    }

    @ParameterizedTest(name= "x={0}, y={1}, results={2}")
        @CsvSource({
                // On points
                "0, 0, TRUE", "4, 3, TRUE", "4, 0, TRUE", "0, 3, TRUE",
                // Off points of both x and y
                "-1, -1, FALSE", "5, 4, FALSE",
                // Off points of x
                "-1, 0, FALSE", "5, 0, FALSE",
                // Off points of y
                "0, -1, FALSE", "0, 4, FALSE"
        })
    void testBoardBoundaries (int x, int y, boolean result) {
        assertEquals(board.withinBorders(x, y), result);
    }
}