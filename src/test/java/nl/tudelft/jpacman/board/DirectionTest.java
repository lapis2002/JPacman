package nl.tudelft.jpacman.board;
import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * A very simple (and not particularly useful)
 * test class to have a starting point where to put tests.
 *
 * @author Arie van Deursen
 */
public class DirectionTest {
    /**
     * Do we get the correct delta when moving north?
     */

    @Test
    void testNorthX() {
        Direction north = Direction.valueOf("NORTH");

        assertThat(north.getDeltaX()).isEqualTo(0);
//        assertEquals(-1, result); // JUnit syntax
    }

    @Test
    void testNorthY() {
        Direction north = Direction.valueOf("NORTH");

        assertThat(north.getDeltaY()).isEqualTo(-1);
    }

    /**
     * Test direction South
     * X coordinate
     */
    @Test
    void testSouthX() {
        Direction north = Direction.valueOf("SOUTH");

        int result = north.getDeltaX();

        assertThat(result).isEqualTo(0);
    }

    /**
     * Test direction South
     * Y coordinate
     */
    @Test
    void testSouthY() {
        Direction north = Direction.valueOf("SOUTH");

        int result = north.getDeltaY();

        assertThat(result).isEqualTo(1);
    }

    /**
     * Test direction West
     * X Coordinate
     */
    @Test
    void testWestX() {
        Direction north = Direction.valueOf("WEST");

        int result = north.getDeltaX();

        assertThat(result).isEqualTo(-1);
    }

    /**
     * Test direction West
     * Y Coordinate
     */
    @Test
    void testWestY() {
        Direction north = Direction.valueOf("WEST");

        int result = north.getDeltaY();

        assertThat(result).isEqualTo(0);
    }

    /**
     * Test direction East
     * X Coordinate
     */
    @Test
    void testEastX() {
        Direction north = Direction.valueOf("EAST");

        int result = north.getDeltaX();

        assertThat(result).isEqualTo(1);
    }

    /**
     * Test direction East
     * Y Coordinate
     */
    @Test
    void testEastY() {
        Direction north = Direction.valueOf("EAST");

        int result = north.getDeltaY();

        assertThat(result).isEqualTo(0);
    }
}
