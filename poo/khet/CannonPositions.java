package poo.khet;

import poo.khet.gameutils.Position;

/**
 * Posiciones preestablecidas de los cañones para cada equipo
 */
public interface CannonPositions {

	/**
	 * Posicion del cañon del <code>Team</code> RED
	 */
	static public final Position RED_CANNON_POSITION = new Position(7, 9);

	/**
	 * Posicion de cañon del <code>Team</code> SILVER
	 */
	static public final Position SILVER_CANNON_POSITION = new Position(0,0);
}
