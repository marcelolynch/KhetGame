package poo.khet;

import java.io.Serializable;

import poo.khet.gameutils.Direction;

/**
 * Un {@code Accessory} puede ser un componente de una pieza del tablero y tiene la caracteristica
 * de poder recibir rayos de alguna direcci&oacute;n y procesarlos de alguna manera (modificando
 * eventualmente el estado del rayo).
 * 
 * Un accesorio tiene una orientacion dada por una {@link Direction}, que incide en si puede o no
 * procesar el {@link Beam} (segun la direccion que lleva este)
 * 
 * @see {@link Direction}
 * @see {@link Beam}
 */
public abstract class Accessory implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * La orientacion del accesorio
     */
    private Direction facing;

    /**
     * Crea un nuevo {@code Accessory} con la orientaci&oacute;n especificada por par&aacute;metro
     * 
     * @param dir - Direccion a donde estar&aacute; orientado el accesorio
     */
    Accessory(Direction dir) {
        facing = dir;
    }

    /**
     * @return la direcci&oacute;n hacia donde se orienta el accesorio
     */
    Direction getFacing() {
        return facing;
    }

    /**
     * Rota el accesorio (cambia la direccion en la que se orienta) en el sentido de las agujas del
     * reloj
     */
    void rotateClockwise() {
        facing = getFacing().getClockwiseDir();
    }

    /**
     * Rota el accesorio (cambia la direccion en la que se orienta) en el sentido contrario a las
     * agujas del reloj
     */
    void rotateCounterClockwise() {
        facing = getFacing().getCounterClockwiseDir();
    }

    /**
     * Indica si puede procesar un rayo como el que se pasa como p&aacute;ametro (teniendo en
     * cuenta, por ejemplo, la direcci&oacute;n del {@code Beam})
     * 
     * @param beam - el rayo sobre el que se quiere consultar
     * @return - <code>true</code> si el rayo puede ser recibido por el accesorio,
     *         <code>false</code> en caso contrario
     */
    abstract boolean canProcessBeam(Beam beam);

    /**
     * Recibe y procesa el {@code Beam}, eventualmente modific&aacute;ndolo.
     * 
     * @param beam - el {@code Beam} a procesar
     * @return una referencia al mismo {@code Beam}, ya procesado
     */
    abstract Beam processBeam(Beam beam);

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o.getClass().equals(this.getClass()))) {
            return false;
        }
        Accessory other = (Accessory) o;
        return other.getFacing().equals(this.getFacing());
    }

    public int hashCode() {
        return 1;
    }

}
