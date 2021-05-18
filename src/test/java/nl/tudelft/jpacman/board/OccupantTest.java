package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen 
 *
 */
class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;

    /**
     * Resets the unit under test.
     */
    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
    }

    /**
     * Asserts that a unit has no square to start with.
     */
    @Test
    void noStartSquare() {
        assertThat(unit.hasSquare()).isFalse();
    }

    /**
     * Tests that the unit indeed has the target square as its base after
     * occupation.
     */
    @Test
    void testOccupy() {
        Square square = new BasicSquare();

        unit.occupy(square);

        assertThat(square.getOccupants()).contains(unit);
        assertThat(unit.getSquare()).isEqualTo(square);
    }

    /**
     * Test that the unit indeed has the target square as its base after
     * double occupation.
     */
    @Test
    void testLeave() {
        Square square = new BasicSquare();

        unit.occupy(square);
        unit.leaveSquare();

        assertThat(square.getOccupants()).doesNotContain(unit);
        assertThat(unit.hasSquare()).isFalse();
    }

    /**
     * Test that the unit indeed has the target square as its base after
     * double occupation.
     */
    @Test
    void testReoccupy() {
        Square square = new BasicSquare();

        unit.occupy(square);
        unit.leaveSquare();
        unit.occupy(square);

        assertThat(square.getOccupants()).contains(unit);
        assertThat(unit.getSquare()).isEqualTo(square);
    }
}
