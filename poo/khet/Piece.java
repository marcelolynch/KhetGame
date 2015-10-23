package poo.khet;

public abstract class Piece {

	abstract boolean canBeSwapped();

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

	/**
	 * Procesa el <tt>Beam</tt> modificandolo de ser necesario
	 * 
	 * @param beam
	 *            - el rayo a procesar
	 * @returns <tt>true</tt> si la <tt>Pieza</tt> sigue en juego;
	 *          <tt>false</tt> si murio por el rayo
	 */
	abstract boolean receiveBeam(Beam beam);

	/**
	 * Chequea si la casilla es un destino valido
	 * 
	 * @param square
	 *            - la casilla destino
	 * @return <tt>true</tt> si es valido, <tt>false</tt> si no.
	 */
	boolean canMove(Square square) {
		return square.isEmpty();
	}

	abstract void rotateClockwise();

	abstract void rotateCounterClockwise();

	public Team getTeam() {
		return team;
	}
	
	@Override
	public boolean equals (Object obj) {
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
