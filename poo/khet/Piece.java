package poo.khet;

import java.io.Serializable;

/**
 * Una pieza es un componente del juego que tiene un equipo,
 * y puede recibir rayos y procesarlos y modificarlos de distintas
 * maneras segun los accesorios
 * 
 * @see Beam
 */
public abstract class Piece implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Equipo al que pertenece la pieza
     */
    private final Team team;

    /**
     * Construye una nueva pieza del equipo especificado
     * @param team - el equipo al que pertenece la pieza
     * @see Team
     */
    Piece(Team team) {
        if (team == null) {
            throw new IllegalArgumentException();
        }

        this.team = team;
    }

    /**
     * Indica el equipo al que pertenece esta pieza
     * @return - el equipo
     */
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
     * Procesa el <code>Beam</code> que se pasa como par&aacute;metro, modific&aacute;ndolo
     * de ser necesario
     * 
     * @param beam - el rayo a procesar
     * @returns <tt>true</tt> si la <tt>Pieza</tt> sigue en juego; <tt>false</tt> si murio por el
     *          rayo
     */
    abstract boolean receiveBeam(Beam beam);

    /**
     * Indica si la pieza es intercambiable (enrocable) ante una pieza que puede intercambiar
     * (como {@link Scarab})
     * @return - <code>true</code> si la pieza es enrocable, <code>false</code> de lo contrario
     */
    abstract boolean canBeSwapped();

    /**
     * Rota la pieza en el sentido de las agujas del reloj
     */
    public abstract void rotateClockwise();

    /**
     * Rota la pieza en el sentido contrario a las agujas del reloj
     */
    public abstract void rotateCounterClockwise();

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
