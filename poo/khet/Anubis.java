package poo.khet;

import poo.khet.gameutils.Direction;

/**
 * La pieza Anubis tiene un {@link Shield} que determina su orientacion,
 * y puede bloquear rayos recibidos por el mismo. Esta pieza puede ser enrocada
 * @see Shield
 */
public class Anubis extends Piece {

    private static final long serialVersionUID = 1L;


    /**
     * Construye un nuevo Anubis del equipo y orientaci&oacute;n inicial especificadas
     * @param team - el equipo al que pertenece
     * @param facing - la orientaci&oacute;n inicial
     */
    public Anubis(Team team, Direction facing) {
        super(team);
        Shield shield = new Shield(facing);
        addAccessory(shield);
    }

    @Override
    boolean canBeSwapped() {
        return true;
    }

}
