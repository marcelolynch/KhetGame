package poo.khet;

import poo.khet.gameutils.Position;
/**
 * Mi idea que es una alteracion de la de lucho es que un objeto move sea un movimiento 
 * de una casilla a otra(creyendo que esto facilitara la parte grafica).
 * @author NIQUITO
 *
 */
public class Move {
	private Position start;
	private Position end;
	
	
	public Move(Position start, Position end) {
		if(start.getRow()>Board.ROWS || start.getRow()<0 || start.getCol()>Board.COLUMNS || start.getCol()<0){
			throw new RuntimeException();//desp veo cual
		}
		if(!start.isAdjacent(end)){
			throw new RuntimeException();//desp veo cual
		}
		if(end.getRow()>Board.ROWS|| end.getRow()<0 || end.getCol()>Board.COLUMNS || end.getCol()<0){
			throw new RuntimeException();//desp veo cual
		}
		this.start = start;
		this.end = end;
	}


	public Position getStart() {
		return start;
	}


	public void setStart(Position start) {
		this.start = start;
	}
	
	


	

	
	
	
}
