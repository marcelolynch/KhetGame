package poo.khet;

/**
 * Representa un casillero, que puede ser ocupado por una {@link Piece}
 * 
 */
public class Square {
    private Piece occupant;

    /**
     * Construye un casillero vac&iacute;o.
     */
    Square() {
        occupant = null;
    }

    /**
     * Construye un casillero ocupado por la <tt>Piece</tt> indicada
     * 
     * @param piece - <tt>Piece</tt> a ocupar el casillero
     */
    Square(Piece piece) {
        setOccupant(piece);
    }

    /**
     * Coloca la pieza en el casillero.
     * 
     * @param piece - la pieza a colocar
     * @throws IllegalArgumentException si la pieza especificada es null
     * @throws IllegalStateException si el casillero está ocupado
     */
    void setOccupant(Piece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("Colocando null Piece");
        }
        if (!isEmpty()) {
            throw new IllegalStateException(
                    "El casillero está ocupado; no se puede colocar una pieza");
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
     * Indica si el casillero est&aacute; vac&iacute;o.
     * 
     * @return <code>true</code> si el casillero est&aacute; vac&iacute;o, <code>false</code> de lo contrario
     */
    public boolean isEmpty() {
        return occupant == null;
    }

    /**
     * Devuelve la pieza que ocupa el casillero.
     * 
     * @return el ocupante del casillero
     * @throws IllegalStateException - si el casillero est&aacute; vac&iacute;o
     */
    public Piece getOccupant() {
        if (isEmpty()) {
            throw new IllegalStateException("Casillero vacio");
        }
        return occupant;
    }

    /**
     * Indica si la casilla puede acomodar una pieza como la <i>Piece</i> que se pasa
     * 
     * @param piece - la pieza a acomodar
     * @return <b>true</b> si es posible, <b>false</b> de lo contrario
     */
    public boolean canAccomodate(Piece piece) {
        // La casilla no tiene restricciones adicionales
        return piece.canMove(this);
    }

}
