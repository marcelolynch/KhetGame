package poo.khet;


import poo.khet.gameutils.Direction;

/**
 * Un {@code Mirror} puede reflejar un {@code Beam} desde dos direcciones especificas (desviando de
 * una a la otra) La orientacion esta dada por las cuatro {@link Direction} posibles, y la reflexion
 * esta dada siempre entre la direccion que tiene la orientacion del espejo y la direccion a 90
 * grados en el sentido horario.
 * 
 * @see {@link Direction}
 * @see {@link Beam}
 */
public class Mirror extends Accessory {

    private static final long serialVersionUID = 1L;

    /**
     * Ademas de la direcci&oacute;n de orientaci&oacute;n del espejo como accesorio, existe esta
     * otra direcci&oacuten de donde se pueden recibir {@code Beam}s y hacia donde se reflejan en
     * caso de llegar en la direcci&oacuten de orientaci&oacute;n
     */
    private Direction otherFacing;

    Mirror(Direction facing) {
        super(facing);
        otherFacing = facing.getClockwiseDir();
    }

    @Override
    boolean canProcessBeam(Beam beam) {
        return getFacing().isOppositeDir(beam.getDirection())
                || getOtherFacing().isOppositeDir(beam.getDirection());
    }

    @Override
    Beam processBeam(Beam beam) {
        if (!canProcessBeam(beam))
            throw new IllegalArgumentException("No se púede procesar el rayo");
        if (getFacing().isOppositeDir(beam.getDirection())) {
            beam.setDirection(getOtherFacing());
        } else {
            beam.setDirection(getFacing());
        }

        return beam;
    }

    @Override
    void rotateClockwise() {
        super.rotateClockwise();
        otherFacing = getOtherFacing().getClockwiseDir();
    }

    @Override
    void rotateCounterClockwise() {
        super.rotateCounterClockwise();
        otherFacing = getOtherFacing().getCounterClockwiseDir();
    }

    /**
     * Devuelve la otra direccion en la que puede recibir el rayo (o redirigirlo hacia ella, si
     * llega en la direccion de orientacion)
     * 
     * @return - la {@code Direction} correspondiente
     */
    Direction getOtherFacing() {
        return otherFacing;
    }

}
