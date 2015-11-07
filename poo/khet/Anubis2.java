package poo.khet;

import poo.khet.gameutils.Direction;

/**
 * La pieza Anubis puede bloquear un rayo si lo recibe en la direcci&oacute;n 
 * en la que esta orientado su {@link Shield}.
 * 
 * @see Shield
 */
public class Anubis2 extends Piece2 {

    private static final long serialVersionUID = 1L;


    /**
     * Construye un nuevo Anubis del equipo y orientaci&oacute;n inicial especificadas
     * @param team - el equipo al que pertenece
     * @param facing - la orientaci&oacute;n inicial
     */
    public Anubis2(Team team, Direction facing) {
        super(team);
        Shield shield = new Shield(facing);
        addAccessory(shield);
    }

    @Override
    boolean canBeSwapped() {
        return true;
    }

}
