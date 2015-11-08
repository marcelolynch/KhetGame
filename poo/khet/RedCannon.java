package poo.khet;

import poo.khet.gameutils.Direction;

/**
 * Ca&ntilde;on del equipo rojo. Siempre comienza con orientacion <i><code>NORTH</code></i>
 * @see BeamCannon
 */
public class RedCannon extends BeamCannon {

	private static final long serialVersionUID = 1L;

	/**
	 * Construye un nuevo ca&ntilde;on rojo
	 */
	public RedCannon() {
		super(Direction.NORTH);
	}
}
