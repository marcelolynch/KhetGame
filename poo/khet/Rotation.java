package poo.khet;

import poo.khet.gameutils.Position;

public class Rotation extends Action {
    private boolean isClockwise;

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

    @Override
    void executeActionIn(Board board) {
        Piece rotated = board.getOccupantIn(this.getStart());
        if (this.isClockwise()) {
            rotated.rotateClockwise();
        } else {
            rotated.rotateCounterClockwise();
        }
    }

    @Override
    Action getRevertedAction(Action action) {
        return new Rotation(this.getStart(), !this.isClockwise());
    }

	@Override
	void updateGame(Game game) {
		game.rotate(getStart(), isClockwise());
	}
}
