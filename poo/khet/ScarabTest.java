package poo.khet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import poo.khet.gameutils.Direction;

public class ScarabTest {

    private Scarab scarab;

    private Beam northBeam;
    private Beam eastBeam;
    private Beam southBeam;
    private Beam westBeam;

    @Before
    public void setUp() {
        scarab = new Scarab(Team.RED, Direction.NORTH);

        northBeam = new Beam(Direction.NORTH);
        eastBeam = new Beam(Direction.EAST);
        southBeam = new Beam(Direction.SOUTH);
        westBeam = new Beam(Direction.WEST);
    }

    @Test
    public void testReceiveBeam() {
        // el Scarab siempre refleja
        assertTrue(scarab.receiveBeam(northBeam)); // cambió la dirección del rayo hacia el oeste
        assertTrue(scarab.receiveBeam(eastBeam)); // cambió la dirección del rayo hacia el sur
        assertTrue(scarab.receiveBeam(southBeam)); // cambió la dirección del rayo hacia el este
        assertTrue(scarab.receiveBeam(westBeam)); // cambió la dirección del rayo hacia el norte

        assertEquals(Direction.WEST, northBeam.getDirection());
        assertEquals(Direction.SOUTH, eastBeam.getDirection());
        assertEquals(Direction.EAST, southBeam.getDirection());
        assertEquals(Direction.NORTH, westBeam.getDirection());

    }
    @Test
    
    public void testEq() {
    	Scarab scarabA = new Scarab(Team.RED, Direction.EAST);
    	Scarab scarabB = new Scarab(Team.RED, Direction.EAST);
    	assertEquals(scarabA, scarabB);
    	
    	scarabA.rotateClockwise();
    	assertNotEquals(scarabA, scarabB);
    	
    	scarabB.rotateClockwise();
    	assertEquals(scarabA, scarabB);

    }

    @Test
    public void testCanMove() {
        Square nonSwapSq = new Square();
        nonSwapSq.setOccupant(new Scarab(Team.RED, Direction.NORTH));
        Square swapSq = new Square();
        swapSq.setOccupant(new Pyramid(Team.RED, Direction.NORTH));

        assertFalse(scarab.canMove(nonSwapSq));
        assertTrue(scarab.canMove(swapSq));
    }

    @Test
    public void testRotateClockwise() {
        scarab.rotateClockwise();

        // el Scarab siempre refleja
        assertTrue(scarab.receiveBeam(northBeam)); // cambió la dirección del rayo hacia el este
        assertTrue(scarab.receiveBeam(eastBeam)); // cambió la dirección del rayo hacia el norte
        assertTrue(scarab.receiveBeam(southBeam)); // cambió la dirección del rayo hacia el oeste
        assertTrue(scarab.receiveBeam(westBeam)); // cambió la dirección del rayo hacia el sur

        assertEquals(Direction.EAST, northBeam.getDirection());
        assertEquals(Direction.NORTH, eastBeam.getDirection());
        assertEquals(Direction.WEST, southBeam.getDirection());
        assertEquals(Direction.SOUTH, westBeam.getDirection());
    }

    @Test
    public void testRotateCounterClockwise() {
        // debería ser idéntico a cuando se rota de forma horaria
        scarab.rotateCounterClockwise();

        // el Scarab siempre refleja
        assertTrue(scarab.receiveBeam(northBeam)); // cambió la dirección del rayo hacia el este
        assertTrue(scarab.receiveBeam(eastBeam)); // cambió la dirección del rayo hacia el norte
        assertTrue(scarab.receiveBeam(southBeam)); // cambió la dirección del rayo hacia el oeste
        assertTrue(scarab.receiveBeam(westBeam)); // cambió la dirección del rayo hacia el sur

        assertEquals(Direction.EAST, northBeam.getDirection());
        assertEquals(Direction.NORTH, eastBeam.getDirection());
        assertEquals(Direction.WEST, southBeam.getDirection());
        assertEquals(Direction.SOUTH, westBeam.getDirection());
    }

}
