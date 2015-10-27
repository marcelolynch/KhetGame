package poo.khet;

import java.io.Serializable;

import poo.khet.gameutils.Direction;

public class Mirror2 extends Accessory implements Serializable {
	
	//TODO: 
	private static final long serialVersionUID = 1L;
	
	private Direction otherFacing;
	
	Mirror2(Direction facing) {
		super(facing);
		otherFacing = facing.getClockwiseDir();
	}

	@Override
	boolean canProcessBeam(Beam beam) {
		return getFacing().isOppositeDir(beam.getDirection()) 
				|| getOtherFacing().isOppositeDir(beam.getDirection());
	}
	
	@Override
	Beam processBeam(Beam beam) {
		if (!canProcessBeam(beam))
			throw new IllegalArgumentException("No se púede procesar el rayo");
		if (getFacing().isOppositeDir(beam.getDirection())) {
			beam.setDirection(getOtherFacing());
		} else {
			beam.setDirection(getFacing());
		}
		
		return beam;
	}
	
	@Override
	void rotateClockwise() {
		super.rotateClockwise();
		otherFacing = getOtherFacing().getClockwiseDir();
	}
	
	@Override
	void rotateCounterClockwise() {
		super.rotateCounterClockwise();
		otherFacing = getOtherFacing().getCounterClockwiseDir();
	}
	
	Direction getOtherFacing() {
		return otherFacing;
	}

}
