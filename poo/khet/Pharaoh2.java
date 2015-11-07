package poo.khet;

/**
 * Un Pharaoh no permite enroque, y en caso de recibir un rayo se ve afectado
 * por el (no posee accesorios)
 *
 */
public class Pharaoh2 extends Piece {
	
	private static final long serialVersionUID = 1L;

	public Pharaoh2(Team team) {
		super(team);
	}

	@Override
	boolean canBeSwapped() {
		return false;
	}

}
