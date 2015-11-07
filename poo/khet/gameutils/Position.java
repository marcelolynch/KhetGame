package poo.khet.gameutils;

import java.io.Serializable;

/**
 * Representa una ubicacion respecto de un <code>Board</code> mediante coordenadas que
 * indican Fila y Columna.
 * <p>
 * Nota: La posición (0,0) es aquella que se encuentra en el extremo superior izquierdo de un Board.
 * @see poo.khet.Board
 */
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Numero de fila
     */
    private int row;

    /**
     * Numero de columna
     */
    private int col;

    /**
     * Crea una nueva posicion a partir de un numero de fila y columna dados.
     * @param row Numero de fila
     * @param col Numero de columna
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Crea la posicion (0,0)
     */
    public Position() {
        this(0, 0);
    }

    /**
     * Devuelve el numero de la fila
     * @return el numero de la fila
     */
    public int getRow() {
        return row;
    }

    /**
     * Devuelve el numero de la columna
     * @return el numero de la Columna
     */
    public int getCol() {
        return col;
    }

    /**
     * Verifica si una posicion es adyacente a otra, es decir si se encuentra
     * a una unidad (fila o columna) de distancia.
     * @param otherPos Posicion respecto de la cual se quiere comparar
     * @return <b><tt>true</tt></b> si la posicion es adyacente, <b><tt>false</tt></b> en caso contrario.
     */
    public boolean isAdjacent(Position otherPos) {
        if (this.equals(otherPos)) {
            return false;
        }
        if (Math.abs(this.getRow() - otherPos.getRow()) > 1
                || Math.abs(this.getCol() - otherPos.getCol()) > 1) {
            return false;
        }
        return true;
    }

    /**
     * Devuelve la coordenada que se encuentra a una unidad (fila o columna) de distancia segun la direccion
     * especificada.
     * 
     * @param direction Direccion en la cual cambia la coordenada
     */
    public Position getPosInDirection(Direction direction) {
        if (direction == null)
            throw new IllegalArgumentException("null Direction");

        Position pos;

        if (direction.equals(Direction.NORTH)) {
            pos = new Position(getRow() - 1, getCol());

        } else if (direction.equals(Direction.EAST)) {
            pos = new Position(getRow(), getCol() + 1);

        } else if (direction.equals(Direction.SOUTH)) {
            pos = new Position(getRow() + 1, getCol());

        } else { // WEST
            pos = new Position(getRow(), getCol() - 1);
        }

        return pos;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Position)) {
            return false;
        }
        Position c = (Position) obj;
        return (c.getRow() == this.getRow()) && (c.getCol() == this.getCol());
    }

    @Override
    public int hashCode() {
        return 31 * getRow() ^ getCol();
    }

    @Override
    public String toString() {
        return "Position: (" + getRow() + ", " + getCol() + ")";
    }
}
