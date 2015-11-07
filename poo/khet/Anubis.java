package poo.khet;

import poo.khet.gameutils.Direction;

/**
 * La pieza Anubis puede bloquear un rayo si lo recibe en la direcci&oacute;n 
 * en la que esta orientado su {@link Shield}.
 * 
 * @see Shield
 */
public class Anubis extends Piece {

    private static final long serialVersionUID = 1L;

    private Shield shield;

    /**
     * Construye un nuevo Anubis del equipo y orientaci&oacute;n inicial especificadas
     * @param team - el equipo al que pertenece
     * @param facing - la orientaci&oacute;n inicial
     */
    public Anubis(Team team, Direction facing) {
        super(team);
        shield = new Shield(facing);
    }

    @Override
    boolean receiveBeam(Beam beam) {
        boolean blockable = shield.canProcessBeam(beam);
        if (blockable) {
            shield.processBeam(beam);
        } else {
            beam.deactivate(); // no se pudo bloquear; el rayo choca con la pieza y finaliza su
                               // recorrido
        }

        return blockable;
    }

    @Override
    boolean canBeSwapped() {
        return true;
    }

    @Override
	public
    void rotateClockwise() {
        shield.rotateClockwise();
    }

    @Override
	public
    void rotateCounterClockwise() {
        shield.rotateCounterClockwise();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Anubis)) {
            return false;
        }
        Anubis other = (Anubis) o;
        return other.getTeam().equals(this.getTeam()) && other.shield.equals(this.shield);
    }

    @Override
    public int hashCode() {
        return getTeam().hashCode() ^ shield.hashCode();
    }

}
