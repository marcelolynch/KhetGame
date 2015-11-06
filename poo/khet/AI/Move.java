package poo.khet.AI;

import poo.khet.Board;
import poo.khet.Game;
import poo.khet.Piece;
import poo.khet.gameutils.Position;

class Move extends Action {
    private Position end;

    public Move(Position start, Position end) {
        super(start);
        this.end = end;
    }

    public Position getDest() {
        return end;
    }

    @Override
    void executeActionIn(Board board) {
        Piece moved = board.withdrawFrom(this.getStart());
        
        if (!board.isEmptyPosition(getDest())) {
            Piece swapped = board.withdrawFrom(getDest());
            board.placePiece(getStart(), swapped);
        }
        board.placePiece(this.getDest(), moved);
    }

    @Override
    Action getRevertedAction(Action action) {
        return new Move(this.getDest(), this.getStart());
    }

	@Override
	void updateGame(Game game) {
	    //TODO: Sacar syso
	    System.out.println("AI -MOVIO- DE " + "("+ this.getStart().getRow() + ", " + this.getStart().getCol() + ") a ("
	            + this.getDest().getRow() + ", " + this.getDest().getCol() + ")");
		game.move(this.getStart(), this.getDest());
	}
       
}
