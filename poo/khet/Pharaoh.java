package poo.khet;

//TODO: Notificación al morir
public class Pharaoh extends Piece {

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
