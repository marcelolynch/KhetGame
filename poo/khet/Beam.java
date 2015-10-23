package poo.khet;

import poo.khet.gameutils.Direction;

public class Beam {
	/**
	 * Indica si el rayo esta vivo
	 */
	private boolean isActive;
	private Direction direction;

	Beam (Direction initialDir) {
		isActive = true;
		direction = initialDir;
	}

	void deactivate(){
		isActive = false;
	}
	
	public boolean isActive(){
		return isActive;
	}

	/**
	 * Devuelve la direccion en la que esta viajando el rayo
	 */
	Direction getDirection(){
		return direction;
    }
	
	/**
	 * Cambia la dirección del rayo
	 * @param newDirection - nueva dirección del rayo
	 */
	void setDirection(Direction newDirection){
	    this.direction = newDirection;
	}
	
}
