package poo.khet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import poo.khet.gameutils.Direction;

public class PyramidTest {

    private Pyramid pyramid;

    private Beam northBeam;
    private Beam eastBeam;
    private Beam southBeam;
    private Beam westBeam;

    @Before
    public void setUp() {
        pyramid = new Pyramid(Team.RED, Direction.NORTH); // debería reflejar rayos dirigidos hacia
                                                          // el sur
                                                          // y hacía el oeste

        northBeam = new Beam(Direction.NORTH);
        eastBeam = new Beam(Direction.EAST);
        southBeam = new Beam(Direction.SOUTH);
        westBeam = new Beam(Direction.WEST);
    }

    @Test
    public void testReceiveBeam() {
        assertFalse(pyramid.receiveBeam(northBeam)); // se desactivó el rayo
        assertFalse(pyramid.receiveBeam(eastBeam)); // se desactivó el rayo

        assertFalse(northBeam.isActive());
        assertFalse(eastBeam.isActive());

        assertTrue(pyramid.receiveBeam(southBeam)); // cambió la dirección del rayo hacia el este
        assertTrue(pyramid.receiveBeam(westBeam)); // cambió la dirección del rayo hacia el norte

        assertEquals(Direction.EAST, southBeam.getDirection());
        assertEquals(Direction.NORTH, westBeam.getDirection());
    }

    @Test
    public void testRotateClockwise() {
        pyramid.rotateClockwise(); // debería reflejar rayos con dirección hacia el oeste y hacia el
                                   // norte

        assertFalse(pyramid.receiveBeam(eastBeam)); // se desactivó el rayo
        assertFalse(pyramid.receiveBeam(southBeam)); // se desactivó el rayo

        assertFalse(eastBeam.isActive());
        assertFalse(southBeam.isActive());

        assertTrue(pyramid.receiveBeam(northBeam)); // cambió la dirección del rayo hacia el este
        assertTrue(pyramid.receiveBeam(westBeam)); // cambió la dirección del rayo hacia el sur

        assertEquals(Direction.EAST, northBeam.getDirection());
        assertEquals(Direction.SOUTH, westBeam.getDirection());
    }

    @Test
    public void testRotateCounterClockwise() {
        pyramid.rotateCounterClockwise();// debería reflejar rayos con dirección hacia el este y
                                         // hacia el sur

        assertFalse(pyramid.receiveBeam(northBeam)); // se desactivó el rayo
        assertFalse(pyramid.receiveBeam(westBeam)); // se desactivó el rayo

        assertFalse(northBeam.isActive());
        assertFalse(westBeam.isActive());

        assertTrue(pyramid.receiveBeam(eastBeam)); // cambió la dirección del rayo hacia el norte
        assertTrue(pyramid.receiveBeam(southBeam)); // cambió la dirección del rayo hacia el oeste

        assertEquals(Direction.NORTH, eastBeam.getDirection());
        assertEquals(Direction.WEST, southBeam.getDirection());
    }
}
