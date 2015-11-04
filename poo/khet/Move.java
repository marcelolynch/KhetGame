package poo.khet;

import poo.khet.gameutils.Position;

public class Move extends Action {
    private Position end;

    public Move(Position start, Position end) {
        super(start);
        this.end = end;
    }

    public Position getDest() {
        return end;
    }

    public void setDest(Position dest) {
        this.end = dest;
    }

    @Override
    void executeActionIn(Board board) {
        Piece moved = board.withdrawFrom(this.getStart());
        board.placePiece(this.getDest(), moved);

    }

    @Override
    Action getRevertedAction(Action action) {
        return new Move(this.getDest(), this.getStart());
    }

	@Override
	void updateGame(Game game) {
		game.move(this.getStart(), this.getDest());
	}
       
}
