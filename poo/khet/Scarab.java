package poo.khet;

import poo.khet.gameutils.Direction;

/**
 * Un escarabajo posee dos {@link Mirror} en direcciones opuestas,
 * que por lo tanto pueden recibir y reflejar rayos desde cualquier direccion
 * 
 * La pieza no puede enrocar pasivamente (enroque generado por otra pieza)
 * pero si puede crear un enroque con las piezas que si pueden hacerlo
 * 
 * @see Mirror
 */
public class Scarab extends Piece {

    private static final long serialVersionUID = 1L;

    /**
     * Construye un nuevo Scarab del equipo especificado, con uno de sus espejos 
     * en la direcci&oacute;n indicada (y el otro est&aacute;, en la 
     * direcci&oacute;n opuesta). La orientacion esta dada por esta Direction e incide
     * en el {@link #equals(Object)}
     * 
     * @see Mirror
     * @param team - el equipo al que pertenece
     * @param facing - la direccion de uno de sus espejos
     */
    public Scarab(Team team, Direction facing) {
        super(team);
        Mirror mirror1 = new Mirror(facing);
        Mirror mirror2 = new Mirror(facing.getOppositeDir());
 
        addAccessory(mirror1);
        addAccessory(mirror2);
    }

    @Override
    boolean canMove(Square square) {
        if (super.canMove(square)) {
            return true;
        } else {
            return square.getOccupant().canBeSwapped(); //Si la pieza ocupante puede ser enrocada
        }
    }

    @Override
    boolean canBeSwapped() {
        return false;
    }

}
