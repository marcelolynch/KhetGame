package poo.khet;

/**
 * Un Pharaoh es una pieza que no permite enroque, y no posee accesorios
 */
public class Pharaoh extends Piece {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construye un nuevo Pharaoh del equipo indicado
	 * @param team - el equipo al que pertenece
	 */
	public Pharaoh(Team team) {
		super(team);
	}

	@Override
	boolean canBeSwapped() {
		return false;
	}

}
