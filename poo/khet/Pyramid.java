package poo.khet;

import poo.khet.gameutils.Direction;

/**
 * La {@link Pyramid} es una pieza que posee un espejo,
 * que puede reflejar rayos desde dos direcciones a 90 grados
 * 
 * @see Mirror
 */
public class Pyramid extends Piece {

    private static final long serialVersionUID = 1L;

    /**
     * Construye una {@link Pyramid} con su espejo en la direccion proporcionada
     * 
     * @param team - el equipo de la pieza
     * @param facing - la orientacion de su espejo
     */
    public Pyramid(Team team, Direction facing) {
        super(team);
        Mirror mirror = new Mirror(facing);
        addAccessory(mirror);
    }

    @Override
    boolean canBeSwapped() {
        return true;
    }

}
