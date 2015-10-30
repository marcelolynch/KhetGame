package poo.khet;

import poo.khet.gameutils.Position;

public abstract class Action {
	private Position start;
	
	// Es responsabilidad de Move ver si el movimiento es valido? me parece que no
	Action (Position start) {
		this.start = start;
	}


	Position getStart() {
		return start;
	}


	void setStart(Position start) {
		this.start = start;
	}
	abstract void executeActionIn(Board board) ;
	
	/**
	 * es solo una idea jaja
	 * @param action
	 * @return
	 */
	abstract Action getRevertedAction(Action action); 
	
}
