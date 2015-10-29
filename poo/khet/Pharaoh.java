package poo.khet;

public class Pharaoh extends Piece {
	
	private static final long serialVersionUID = 1L;

	public Pharaoh(Team team) {
		super(team);
	}

	@Override
	boolean receiveBeam(Beam beam) {
		beam.deactivate();
		setChanged();
		notifyObservers(this.getTeam()); // No estoy segura si esto es asi
		return false;
	}

	@Override
	boolean canBeSwapped() {
		return false;
	}

	@Override
	void rotateClockwise() {
	}

	@Override
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
