package poo.khet;

public class Scarab extends Piece {

	private Mirror2[] mirrors;

	Scarab(Team team, Direction facing) {
		super(team);

		mirrors = new Mirror2[2];
		mirrors[0] = new Mirror2(facing);
		mirrors[1] = new Mirror2(facing.getOppositeDirection());
	}

	@Override
	void rotateClockwise() {
		mirrors[0].rotateClockwise();
		mirrors[1].rotateClockwise();
	}

	@Override
	void rotateCounterClockwise() {
		mirrors[0].rotateCounterClockwise();
		mirrors[1].rotateCounterClockwise();
	}

	@Override
	boolean receiveBeam(Beam beam) {
		if (mirrors[0].canProcessBeam(beam)) {
			mirrors[0].processBeam(beam);
		} else {
			mirrors[1].processBeam(beam);
		}
		return true;
	}
	
	@Override
	boolean canMove(Square square) {
		if (super.canMove(square)) {
			return true;
		} else {
			return square.getOccupant().canBeSwapped();
		}
	}

	@Override
	boolean canBeSwapped() {
		return false;
	}
}
