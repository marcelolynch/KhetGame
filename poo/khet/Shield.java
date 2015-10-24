package poo.khet;

import poo.khet.gameutils.Direction;

public class Shield extends Accessory {

	Shield (Direction dir) {
		super(dir);
	}
	
	@Override
	boolean canProcessBeam(Beam beam) {
		return getFacing().isOppositeDir(beam.getDirection());
	}
	
	@Override
	Beam processBeam(Beam beam) {
		if (!canProcessBeam(beam))
			throw new IllegalArgumentException("No se puede procesar el rayo");
		beam.deactivate();
		return beam;
	}
	
}
