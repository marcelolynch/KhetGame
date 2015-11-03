package poo.khet;

import poo.khet.gameutils.Direction;

/**
 * Un {@code Shield} es un {@code Accessory} que puede recibir y desactivar rayos en caso
 * de recibirlos desde su direccion de orientacion.
 */
public class Shield extends Accessory {

	private static final long serialVersionUID = 1L;
	/**
	 * Construye un nuevo {@code Shield} con la direcci&oacute;n de orientaci&oacute;n indicada
	 * @param dir - la {@code Direction} hacia donde se orienta el {@code Shield}
	 */
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
