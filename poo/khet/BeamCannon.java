package poo.khet;

import java.io.Serializable;

import poo.khet.gameutils.Direction;

/**
 * Un {@link BeamCannon} es un elemento de un {@link Game} que emite un {@link Beam}
 * en una {@link Direction}, dependiendo de su orientación.
 * <p>
 * Cada {@link BeamCannon} solo tiene dos posibles {@link Direction}s a las cuales estar orientado.
 * @see Beam
 * @see CannonPositions
 * @see Direction
 *
 */
public abstract class BeamCannon implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Indica la orientacion del cañón
     */
    private Direction facing;

    /**
     * Indica si debe alternarse su orientacion en sentido horario o antihorario.
     */
    private boolean clockwiseSwitch;

    public BeamCannon(Direction direction) {
        clockwiseSwitch = false;
        facing = direction;
    }

    /**
     * Devuelve hacia adonde esta orientado el {@link BeamCannon}
     * @return <code>Direction</code> a la cual esta orientado
     */
    public Direction getFacing() {
        return facing;
    }

    /**
     * Genera un nuevo {@link Beam} en una <code>Direction</code> que coincide
     * con la dirección hacia donde esta orientado el cañon.
     * @return el <code>Beam</code> generado
     * @see #getFacing()
     */
    public Beam generateBeam() {
        return new Beam(getFacing());
    }

    /**
     * Alterna la orientación del cañon (entre las dos posibles)
     * @see Direction
     */
    public void switchFacing() {
        if (clockwiseSwitch) {
            facing = facing.getClockwiseDir();
        } else {
            facing = facing.getCounterClockwiseDir();
        }
        clockwiseSwitch = !clockwiseSwitch;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o.getClass() == this.getClass())) {
            return false;
        }
        BeamCannon b = (BeamCannon) o;
        return b.getFacing().equals(this.getFacing());
    }

    @Override
    public int hashCode() {
        return getFacing().hashCode();
    }
}
