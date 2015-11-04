package poo.khet;

import java.io.Serializable;

public abstract class Piece implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Equipo al que pertenece la pieza
     */
    private final Team team;

    Piece(Team team) {
        if (team == null) {
            throw new IllegalArgumentException();
        }

        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    /**
     * Chequea si la casilla es un destino valido
     * 
     * @param square - la casilla destino
     * @return <tt>true</tt> si es valido, <tt>false</tt> si no.
     */
    boolean canMove(Square square) {
        return square.isEmpty();
    }

    /**
     * Procesa el <tt>Beam</tt> modificandolo de ser necesario
     * 
     * @param beam - el rayo a procesar
     * @returns <tt>true</tt> si la <tt>Pieza</tt> sigue en juego; <tt>false</tt> si murio por el
     *          rayo
     */
    abstract boolean receiveBeam(Beam beam);

    abstract boolean canBeSwapped();

    abstract void rotateClockwise();

    abstract void rotateCounterClockwise();

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }

        Piece p = (Piece) obj;
        return getTeam().equals(p.getTeam());
    }

}
