package poo.khet;

/**
 * Un Pharaoh no permite enroque, y en caso de recibir un rayo se ve afectado
 * por el (no posee accesorios)
 *
 */
public class Pharaoh extends Piece {
	
	private static final long serialVersionUID = 1L;

	public Pharaoh(Team team) {
		super(team);
	}

	@Override
	boolean receiveBeam(Beam beam) {
		beam.deactivate();
		return false;
	}

	@Override
	boolean canBeSwapped() {
		return false;
	}

	@Override
	public
	void rotateClockwise() {
	}

	@Override
	public
	void rotateCounterClockwise() {
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null || !(o instanceof Pharaoh)){
			return false;
		}
		Pharaoh other = (Pharaoh)o;
		return other.getTeam().equals(this.getTeam());
	}
	
	@Override
	public int hashCode(){
		return getTeam().hashCode();
	}
}
