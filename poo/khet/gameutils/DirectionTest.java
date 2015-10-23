package poo.khet.gameutils;

import static org.junit.Assert.*;

import org.junit.Test;

public class DirectionTest {

	@Test
	public void testGetClockwiseDirection() {
		assertEquals(Direction.EAST, Direction.NORTH.getClockwiseDirection());
		assertEquals(Direction.SOUTH, Direction.EAST.getClockwiseDirection());
		assertEquals(Direction.WEST, Direction.SOUTH.getClockwiseDirection());
		assertEquals(Direction.NORTH, Direction.WEST.getClockwiseDirection());
	}

	@Test
	public void testGetCounterClockwiseDirection() {
		assertEquals(Direction.EAST, Direction.SOUTH.getCounterClockwiseDirection());
		assertEquals(Direction.SOUTH, Direction.WEST.getCounterClockwiseDirection());
		assertEquals(Direction.WEST, Direction.NORTH.getCounterClockwiseDirection());
		assertEquals(Direction.NORTH, Direction.EAST.getCounterClockwiseDirection());
	}

	@Test
	public void testGetOppositeDirection() {
		assertEquals(Direction.EAST, Direction.WEST.getOppositeDirection());
		assertEquals(Direction.SOUTH, Direction.NORTH.getOppositeDirection());
		assertEquals(Direction.WEST, Direction.EAST.getOppositeDirection());
		assertEquals(Direction.NORTH, Direction.SOUTH.getOppositeDirection());
	}

	@Test
	public void testIsClockwiseDirection() {
		assertTrue(Direction.NORTH.isClockwiseDirection(Direction.EAST));
		assertTrue(Direction.WEST.isClockwiseDirection(Direction.NORTH));
		assertTrue(Direction.EAST.isClockwiseDirection(Direction.SOUTH));
		assertTrue(Direction.SOUTH.isClockwiseDirection(Direction.WEST));
		
		assertFalse(Direction.WEST.isClockwiseDirection(Direction.SOUTH));
		assertFalse(Direction.SOUTH.isClockwiseDirection(Direction.SOUTH));
	}

	@Test
	public void testIsCounterClockwiseDirection() {
		assertTrue(Direction.NORTH.isCounterClockwiseDirection(Direction.WEST));
		assertTrue(Direction.WEST.isCounterClockwiseDirection(Direction.SOUTH));
		assertTrue(Direction.EAST.isCounterClockwiseDirection(Direction.NORTH));
		assertTrue(Direction.SOUTH.isCounterClockwiseDirection(Direction.EAST));
		
		assertFalse(Direction.WEST.isCounterClockwiseDirection(Direction.NORTH));
		assertFalse(Direction.SOUTH.isCounterClockwiseDirection(Direction.SOUTH));
	}

	@Test
	public void testIsOppositeDirection() {
		assertTrue(Direction.NORTH.isOppositeDirection(Direction.SOUTH));
		assertTrue(Direction.WEST.isOppositeDirection(Direction.EAST));
		assertTrue(Direction.EAST.isOppositeDirection(Direction.WEST));
		assertTrue(Direction.SOUTH.isOppositeDirection(Direction.NORTH));
		
		assertFalse(Direction.WEST.isOppositeDirection(Direction.NORTH));
		assertFalse(Direction.SOUTH.isOppositeDirection(Direction.WEST));
	}

}
