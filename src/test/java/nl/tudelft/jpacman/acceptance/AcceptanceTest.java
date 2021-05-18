package nl.tudelft.jpacman.acceptance;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTest {
    private Launcher launcher;
    private Game game;
    private Player player;
    private BoardFactory b;

    /*** Start a launcher, which can display the user interface.*/
    @BeforeEach
    public void before() {
        launcher = new Launcher();
    }

    /*** Close the user interface.*/
    @AfterEach
    public void after() {
        launcher.dispose();
    }

    @Test
    void suspendGameTest () {
        launcher = launcher.withMapFile("/onePellet.txt");
        launcher.launch();

        game = launcher.getGame();
        game.start();

        // check that we've loaded the correct board
        assertEquals(3, game.getLevel().getBoard().getHeight());
        assertEquals(4, game.getLevel().getBoard().getWidth());
        assertEquals(1, game.getLevel().remainingPellets());

        // given the game has started
        assertTrue(game.isInProgress());

        game.stop();
        assertFalse(game.isInProgress());
    }

    @Test
    void playerConsumesPelletTest () {
        launcher = launcher.withMapFile("/onePellet.txt");
        launcher.launch();

        game = launcher.getGame();
        game.start();

        // check that we've loaded the correct board
        assertEquals(3, game.getLevel().getBoard().getHeight());
        assertEquals(4, game.getLevel().getBoard().getWidth());
        assertEquals(1, game.getLevel().remainingPellets());

        // given the game has started
        assertTrue(game.isInProgress());

        player = game.getPlayers().get(0);
        Square target = player.getSquare().getSquareAt(Direction.WEST);

        assertTrue(target.getOccupants().get(0) instanceof Pellet);

        // when I press arrow key toward that square, my pacman can move to
        // that square and my points increases by 1.
         assertTrue(target.isAccessibleTo(player));
         int pointsBeforeMoving = player.getScore();

         Pellet pellet = (Pellet) target.getOccupants().get(0);
         game.move(player, Direction.WEST);

         int pointsAfterMoving = player.getScore();
         assertEquals(pointsAfterMoving, pointsBeforeMoving + pellet.getValue());
         assertEquals(player.getSquare(), target);
    }

    @Test
    void trappedPlayerTest () {
        launcher = launcher.withMapFile("/trappedPlayer.txt");
        launcher.launch();

        game = launcher.getGame();
        game.start();

        // check that we've loaded the correct board
        assertEquals(3, game.getLevel().getBoard().getHeight());
        assertEquals(5, game.getLevel().getBoard().getWidth());
        assertEquals(1, game.getLevel().remainingPellets());

        // given the game has started
        assertTrue(game.isInProgress());

        player = game.getPlayers().get(0);
        Square oldPos = player.getSquare();

        Square target = player.getSquare().getSquareAt(Direction.WEST);


        // when I press arrow key toward that square, my pacman cannot move to
        // that square.
        assertFalse(target.isAccessibleTo(player));

        assertEquals(1, 1 );
        assertEquals(player.getSquare(), oldPos);
    }

    @Test
    void playerDiesTest () {
        launcher = launcher.withMapFile("/adjacentGhost.txt");
        launcher.launch();

        game = launcher.getGame();
        game.start();

        // check that we've loaded the correct board
        assertEquals(3, game.getLevel().getBoard().getHeight());
        assertEquals(7, game.getLevel().getBoard().getWidth());

        // given the game has started
        assertTrue(game.isInProgress());

        player = game.getPlayers().get(0);

        Square target = player.getSquare().getSquareAt(Direction.WEST);
        assertTrue(target.getOccupants().get(0) instanceof Ghost);

        // when I press arrow key toward that square, my pacman moves to
        // that square and die. The game ends.
        assertTrue(target.isAccessibleTo(player));
        game.move(player, Direction.WEST);

        assertFalse(player.isAlive());
        assertFalse(game.isInProgress());

    }

}