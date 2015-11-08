package poo.khet;

import poo.khet.gameutils.Direction;

/**
 * Un <code>Beam</code> es una entidad que, de estar activo, lleva una direcci&oacute;n
 * de movimiento. El rayo puede ser desactivado, en cuyo caso pierde la direcci&oacute;n
 * 
 * @see Piece
 */
public class Beam {
    /**
     * Indica si el rayo esta activo
     */
    private boolean isActive;
    
    /**
     * Indica la direcci&oacute;n de viaje del rayo
     */
    private Direction direction;

    /**
     * Construye un nuevo rayo activo, con la direcci&oacute;n de viaje especificada
     * @param initialDir
     */
    Beam(Direction initialDir) {
        isActive = true;
        direction = initialDir;
    }

    /**
     * Desactiva el rayo
     */
    void deactivate() {
        isActive = false;
    }

    /**
     * Indica si el rayo est&aacute; activo
     * @return <code>true</code> si el rayo esta vivo, <code>false</code> de lo contrario
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Devuelve la direccion en la que esta viajando el rayo
     * 
     * @throws IllegalStateException si el rayo no se encuentra activo
     */
    Direction getDirection() {
        if (!isActive()) {
            throw new IllegalStateException("El rayo no se encuentra activo");
        }

        return direction;
    }

    /**
     * Cambia la direcci&oacute;n actual del rayo por la direcci&oacute;n especificada
     * 
     * @param newDirection - nueva direcci&oacute;n del rayo
     * @throws IllegalStateException si el rayo no se encuentra activo
     */
    void setDirection(Direction newDirection) {
        if (!isActive()) {
            throw new IllegalStateException("El rayo no se encuentra activo");
        }

        this.direction = newDirection;
    }

}
