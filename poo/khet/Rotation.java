package poo.khet;

import poo.khet.gameutils.Position;

public class Rotation extends Action {
	private boolean isClockwise;
	// esto es feo, habria que hacer un enum

	public Rotation(Position start, boolean isClockwise) {
		super(start);
		this.isClockwise = isClockwise;
	}

	public boolean isClockwise() {
		return isClockwise;
	}

	public void setClockwise(boolean isClockwise) {
		this.isClockwise = isClockwise;
	}
}
