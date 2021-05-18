package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InkyTest {
    private GhostMapParser parser;
    private Player pacman;

    @BeforeEach
    void setUp() {
        PacManSprites sprites = new PacManSprites();
        PointCalculator pointCalculator = new DefaultPointCalculator();
        GhostFactory ghostFactory = new GhostFactory(sprites);
        BoardFactory boardFactory = new BoardFactory(sprites);
        LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory, pointCalculator);
        parser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        PlayerFactory playerFactory = new PlayerFactory(sprites);
        pacman = playerFactory.createPacMan();
    }
    @Test
    void testNotToMove () {
        ArrayList<String> map = new ArrayList<>(List.of(
                "############",
                "#.....I.....",
                "############")
        );
        Level level = parser.parseMap(map);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        Optional<Direction> next = inky.nextAiMove();
        assertTrue(next.isEmpty());
    }

    @Test
    void testMoveCloserPacman () {
        ArrayList<String> map = new ArrayList<>(List.of(
                "############",
                "#I......P.B#",
                "############")
        );

        Level level = parser.parseMap(map);

        level.registerPlayer(pacman);
        pacman.setDirection(Direction.WEST);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        Optional<Direction> next = inky.nextAiMove();

        assertTrue(next.isPresent());
        assertSame(Direction.EAST, next.get());
    }

    @Test
    void testMoveAwayPacman () {
        ArrayList<String> map = new ArrayList<>(List.of(
                "#################",
                "#..I..P........B#",
                "#################")
        );

        Level level = parser.parseMap(map);

        level.registerPlayer(pacman);
        pacman.setDirection(Direction.EAST);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        Optional<Direction> next = inky.nextAiMove();

        assertTrue(next.isPresent());
        assertSame(Direction.WEST, next.get());
    }
}