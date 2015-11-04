package poo.khet.gameutils;

import static org.junit.Assert.*;

import org.junit.Test;

public class DirectionTest {

    @Test
    public void testGetClockwiseDirection() {
        assertEquals(Direction.EAST, Direction.NORTH.getClockwiseDir());
        assertEquals(Direction.SOUTH, Direction.EAST.getClockwiseDir());
        assertEquals(Direction.WEST, Direction.SOUTH.getClockwiseDir());
        assertEquals(Direction.NORTH, Direction.WEST.getClockwiseDir());
    }

    @Test
    public void testGetCounterClockwiseDirection() {
        assertEquals(Direction.EAST, Direction.SOUTH.getCounterClockwiseDir());
        assertEquals(Direction.SOUTH, Direction.WEST.getCounterClockwiseDir());
        assertEquals(Direction.WEST, Direction.NORTH.getCounterClockwiseDir());
        assertEquals(Direction.NORTH, Direction.EAST.getCounterClockwiseDir());
    }

    @Test
    public void testGetOppositeDirection() {
        assertEquals(Direction.EAST, Direction.WEST.getOppositeDir());
        assertEquals(Direction.SOUTH, Direction.NORTH.getOppositeDir());
        assertEquals(Direction.WEST, Direction.EAST.getOppositeDir());
        assertEquals(Direction.NORTH, Direction.SOUTH.getOppositeDir());
    }

    @Test
    public void testIsClockwiseDirection() {
        assertTrue(Direction.NORTH.isClockwiseDir(Direction.EAST));
        assertTrue(Direction.WEST.isClockwiseDir(Direction.NORTH));
        assertTrue(Direction.EAST.isClockwiseDir(Direction.SOUTH));
        assertTrue(Direction.SOUTH.isClockwiseDir(Direction.WEST));

        assertFalse(Direction.WEST.isClockwiseDir(Direction.SOUTH));
        assertFalse(Direction.SOUTH.isClockwiseDir(Direction.SOUTH));
    }

    @Test
    public void testIsCounterClockwiseDirection() {
        assertTrue(Direction.NORTH.isCounterClockwiseDir(Direction.WEST));
        assertTrue(Direction.WEST.isCounterClockwiseDir(Direction.SOUTH));
        assertTrue(Direction.EAST.isCounterClockwiseDir(Direction.NORTH));
        assertTrue(Direction.SOUTH.isCounterClockwiseDir(Direction.EAST));

        assertFalse(Direction.WEST.isCounterClockwiseDir(Direction.NORTH));
        assertFalse(Direction.SOUTH.isCounterClockwiseDir(Direction.SOUTH));
    }

    @Test
    public void testIsOppositeDirection() {
        assertTrue(Direction.NORTH.isOppositeDir(Direction.SOUTH));
        assertTrue(Direction.WEST.isOppositeDir(Direction.EAST));
        assertTrue(Direction.EAST.isOppositeDir(Direction.WEST));
        assertTrue(Direction.SOUTH.isOppositeDir(Direction.NORTH));

        assertFalse(Direction.WEST.isOppositeDir(Direction.NORTH));
        assertFalse(Direction.SOUTH.isOppositeDir(Direction.WEST));
    }

}
