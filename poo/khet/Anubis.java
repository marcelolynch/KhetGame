package poo.khet;

import poo.khet.gameutils.Direction;

public class Anubis extends Piece implements BoardAccepter{

	private Shield shield;

	Anubis(Team team, Direction facing) {
		super(team);
		shield = new Shield(facing);
	}

	@Override
	boolean receiveBeam(Beam beam) {
		boolean blockable = shield.canProcessBeam(beam);
		if (blockable) {
			shield.processBeam(beam);
		} else {
			beam.deactivate(); // no se pudo bloquear; el rayo choca con la pieza y finaliza su recorrido
		}

		return blockable;
	}

	@Override
	boolean canBeSwapped() {
		return true;
	}
	
	@Override
	void rotateClockwise() {
		shield.rotateClockwise();
	}
	
	@Override
	void rotateCounterClockwise() {
		shield.rotateCounterClockwise();
	}

    @Override
    public boolean accept(BoardVisitor visitor) {
        return visitor.visit(this);
    }

}
