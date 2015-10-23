package poo.khet.gameutils;

public class Position {
	private int row;
	private int col;
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public Position() {
		this(0, 0);
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}


	public boolean isAdjacent(Position otherPos) {
		if (this.equals(otherPos)) {
			return false;
		}
		if (Math.abs(this.getRow() - otherPos.getRow()) > 1 || Math.abs(this.getCol() - otherPos.getCol()) > 1) {
			return false;
		}
		return true;
	}
	
	/**
	 * Devuelve la coordenada que se encuentra a una unidad de distancia 
	 * segun la direccion especificada.
	 * @param direction - Direccion en la cual cambia la coordenada
	 */
	public Position getPosInDirection(Direction direction){
		if (direction == null)
			throw new IllegalArgumentException("null Direction");
		
		Position pos;
		
		if (direction.equals(Direction.NORTH)) {
			pos = new Position(getRow()-1, getCol());
			
		} else if (direction.equals(Direction.EAST)) {
			pos = new Position(getRow(), getCol()+1);
			
		} else if (direction.equals(Direction.SOUTH)) {
			pos = new Position(getRow()+1, getCol());
			
		} else { // WEST
			pos = new Position(getRow(), getCol()-1);
		}
		
		return pos;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Position)){
			return false;
		}
		Position c = (Position)obj;
		return (c.getRow() == this.getRow()) && (c.getCol() == this.getCol());
	}
	
	
	public int hashCode(){
		return 31*getRow() ^ getCol();
	}
}
