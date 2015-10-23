package poo.khet;

import poo.khet.gameutils.Direction;

public class Mirror2 extends Accessory {
	
	private Direction otherFacing;
	
	Mirror2(Direction facing) {
		super(facing);
		otherFacing = facing.getClockwiseDirection();
	}

	@Override
	boolean canProcessBeam(Beam beam) {
		return getFacing().isOppositeDirection(beam.getDirection()) 
				|| getOtherFacing().isOppositeDirection(beam.getDirection());
	}
	
	@Override
	Beam processBeam(Beam beam) {
		if (!canProcessBeam(beam))
			throw new IllegalArgumentException("No se púede procesar el rayo");
		if (getFacing().isOppositeDirection(beam.getDirection())) {
			beam.redirectCounterClockwise();
		} else {
			beam.redirectClockwise();
		}
		
		return beam;
	}
	
	@Override
	void rotateClockwise() {
		super.rotateClockwise();
		otherFacing = getOtherFacing().getClockwiseDirection();
	}
	
	@Override
	void rotateCounterClockwise() {
		super.rotateCounterClockwise();
		otherFacing = getOtherFacing().getCounterClockwiseDirection();
	}
	
	Direction getOtherFacing() {
		return otherFacing;
	}

}
