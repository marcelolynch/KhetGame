package poo.khet.AI;

import poo.khet.Board;
import poo.khet.Game;
import poo.khet.Piece;
import poo.khet.gameutils.Position;

class Rotation extends Action {
    private boolean isClockwise;

    public Rotation(Position start, boolean isClockwise) {
        super(start);
        this.isClockwise = isClockwise;
    }

    public boolean isClockwise() {
        return isClockwise;
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
	    //TODO: Sacar syso
	    System.out.println("AI -ROTO- LA PIEZA EN" + "("+ this.getStart().getRow() + ", " + this.getStart().getCol() + ")" 
	            + "CLOCKWISE: " + isClockwise);
		game.rotate(getStart(), isClockwise());
	}
}
