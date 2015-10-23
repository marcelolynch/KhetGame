package poo.khet;

//TODO: Notificación al morir
public class Pharaoh extends Piece {

	Pharaoh(Team team) {
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

}
