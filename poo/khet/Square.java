package poo.khet;

/**
 * Representa un casillero.
 * 
 */
public class Square {
	private Piece occupant;

	/**
	 * Construye un casillero vacío.
	 */
	Square() {
		occupant = null;
	}

	/**
	 * Construye un casillero ocupado por un <tt>Piece</tt>.
	 * @param piece
	 *            - <tt>Piece</tt> a ocupar el casillero
	 */
	Square(Piece piece) {
		setOccupant(piece);
	}

	/**
	 * Coloca la pieza en el casillero.
	 * 
	 * @param piece
	 *            - la pieza a colocar
	 * @throws IllegalArgumentException
	 *             si la pieza especificada es null
	 * @throws IllegalStateException
	 *             si el casillero está ocupado
	 */
	void setOccupant(Piece piece) {
		if (piece == null) {
			throw new IllegalArgumentException("Colocando null Piece");
		}
		if (!isEmpty()) {
			throw new IllegalStateException("El casillero está ocupado; no se puede colocar una pieza");
		}
		occupant = piece;
	}

	/**
	 * Quita el ocupante del casillero.
	 * 
	 * @return el ocupante retirado
	 */
	Piece withdrawOccupant() {
		Piece piece = getOccupant();
		occupant = null;
		return piece;
	}

	/**
	 * Revisa si el casillero está vacío.
	 * 
	 * @return true si el casillero está vacío; false sino
	 */
	public boolean isEmpty() {
		return occupant == null;
	}

	/**
	 * Devuelve el ocupante del casillero.
	 * 
	 * @return el ocupante del casillero
	 * @throws IllegalStateException
	 *             si el casillero está vacío
	 */
	public Piece getOccupant() {
		if (isEmpty()) {
			throw new IllegalStateException("Casillero vacío");
		}
		return occupant;
	}
	
	/**
	 * Devuelve si el casillero puede ser ocupado por el equipo de la pieza dada
	 * @param piece la que quiere ocupar el casillero
	 * @return <tt>true</tt> si puede ser ocupado por la pieza <ff>false</ff> si no.
	 */
	
	
	/**
	 * Indica si la casilla puede acomodar una pieza como la <i>Piece</i> que se pasa
	 * @param piece - la pieza a acomodar
	 * @return <b>true</b> si es posible, <b>false</b> de lo contrario
	 * @author Chelo
	 */
	public boolean canAccomodate(Piece piece){
		//Mientras la pieza pueda mover hasta aca, todo bien
		//La casilla no tiene restricciones adicionales
		return piece.canMove(this);
	}

}
