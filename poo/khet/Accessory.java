package poo.khet;

import poo.khet.gameutils.Direction;

public abstract class Accessory {
	String s = "laslaskjsdkjh";
	private Direction facing;
	
	Accessory(Direction dir) {
		facing = dir;
	}
	
	Direction getFacing() {
		return facing;
	}
	
	void rotateClockwise() {
		facing = getFacing().getClockwiseDirection();
	}
	
	void rotateCounterClockwise() {
		facing = getFacing().getCounterClockwiseDirection();
	}

	abstract boolean canProcessBeam(Beam beam);
	abstract Beam processBeam(Beam beam); // ya que modifica el estado interno de Beam, estaría bien que devuelva void?
}
