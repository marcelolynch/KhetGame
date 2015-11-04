package poo.khet;

import poo.khet.gameutils.Direction;

public class Beam {
    /**
     * Indica si el rayo esta vivo
     */
    private boolean isActive;
    private Direction direction;

    Beam(Direction initialDir) {
        isActive = true;
        direction = initialDir;
    }

    void deactivate() {
        isActive = false;
    }

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
     * Cambia la dirección del rayo
     * 
     * @param newDirection - nueva dirección del rayo
     * @throws IllegalStateException si el rayo no se encuentra activo
     */
    void setDirection(Direction newDirection) {
        if (!isActive()) {
            throw new IllegalStateException("El rayo no se encuentra activo");
        }

        this.direction = newDirection;
    }

}
