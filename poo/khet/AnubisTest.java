package poo.khet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import poo.khet.gameutils.Direction;

public class AnubisTest {

	private Anubis anubis;
	
	private Beam northBeam;
	private Beam eastBeam;
	private Beam southBeam;
	private Beam westBeam;
	
	@Before
	public void setUp() {
		anubis = new Anubis(Team.RED, Direction.NORTH); // debería bloquear rayos dirigidos hacia el sur
		
		northBeam = new Beam(Direction.NORTH);
		eastBeam = new Beam(Direction.EAST);
		southBeam = new Beam(Direction.SOUTH);
		westBeam = new Beam(Direction.WEST);
	}

	@Test
	public void testReceiveBeam() {
		assertFalse(anubis.receiveBeam(northBeam));
		assertFalse(anubis.receiveBeam(eastBeam)); 
		assertFalse(anubis.receiveBeam(westBeam));
		assertTrue(anubis.receiveBeam(southBeam)); // se bloquea
		
		// Pase lo que pase, el rayo se desactiva
		assertFalse(northBeam.isActive());
		assertFalse(eastBeam.isActive());
		assertFalse(westBeam.isActive());
		assertFalse(southBeam.isActive());
	}
	
	@Test
	public void testRotateClockwise() {
		anubis.rotateClockwise(); // Debería bloquear rayos dirigidos hacia el oeste
		
		assertFalse(anubis.receiveBeam(northBeam));
		assertFalse(anubis.receiveBeam(eastBeam)); 
		assertFalse(anubis.receiveBeam(southBeam));
		assertTrue(anubis.receiveBeam(westBeam)); // se bloquea
		
		// Pase lo que pase, el rayo se desactiva
		assertFalse(northBeam.isActive());
		assertFalse(eastBeam.isActive());
		assertFalse(westBeam.isActive());
		assertFalse(southBeam.isActive());
	}

	@Test
	public void testRotateCounterClockwise() {
		anubis.rotateCounterClockwise(); // Debería bloquear rayos dirigidos hacia el este
		
		assertFalse(anubis.receiveBeam(northBeam));
		assertFalse(anubis.receiveBeam(westBeam)); 
		assertFalse(anubis.receiveBeam(southBeam));
		assertTrue(anubis.receiveBeam(eastBeam)); // se bloquea
		
		// Pase lo que pase, el rayo se desactiva
		assertFalse(northBeam.isActive());
		assertFalse(eastBeam.isActive());
		assertFalse(westBeam.isActive());
		assertFalse(southBeam.isActive());
	}

}
