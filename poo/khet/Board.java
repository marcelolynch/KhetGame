package poo.khet;

import java.util.HashMap;
import java.util.Map;

import poo.khet.gameutils.BoardDimensions;
import poo.khet.gameutils.Position;

/**
 * Un {@link Board} representa al tablero de Juego.
 * <p>
 * Un {@link Board} tiene un total de 78 {@link Square}s, de los cuales existen
 * algunos reservados para un {@link Team} específico.({@link ReservedSquare}).
 * <p>
 * Los casilleros reservados para el equipo <code>SILVER</code> ocupan la primer columna del
 * tablero (excepto la posicion (0,0)) y las {@link Position} (0,8) y (7,8).
 * Los casilleros reservados para el equipo <code>RED</code> ocupan la última columna del
 * tablero (excepto la posicion (9,9)) y las {@link Position} (0,1) y (7,1)
 * <p>
 * Nota: No existen <code>Squares</code> en las posiciones (0,0) y (9,9) pues corresponden a las posiciones
 * de los cañones. {@link CannonPositions}.
 *
 * @see poo.khet.gameutils.BoardDimensions
 * @see Piece
 * @see Square
 * @see poo.khet.CannonPositions
 */
public class Board implements CannonPositions, BoardDimensions {

    /**
     * Este mapa guarda un {@link Square} por cada {@link Position} existente.
     */
    private Map<Position, Square> grid;

    public Board(Map<Position, Piece> pieces) {
        generateBoard();
        placePieces(pieces);
    }

    /**
     * Construye un Mapa  colocando en cada {@link Position}
     * el tipo de {@link Square} que corresponda.
     * @see ReservedSquare
     */
    private void generateBoard() {
        grid = new HashMap<Position, Square>();
        Position position;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                position = new Position(i, j);
                if (!(position.equals(RED_CANNON_POSITION)
                        || position.equals(SILVER_CANNON_POSITION))) {
                    if (j == 0) {
                        grid.put(position, new ReservedSquare(Team.SILVER));
                    } else if (j == COLUMNS - 1) {
                        grid.put(position, new ReservedSquare(Team.RED));
                    } else {
                        grid.put(position, new Square());
                    }
                }
            }
        }

        // Casillas Reservadas. Se pisan las casillas ya asignadas.
        grid.put(new Position(0, 8), new ReservedSquare(Team.SILVER));
        grid.put(new Position(7, 8), new ReservedSquare(Team.SILVER));
        grid.put(new Position(0, 1), new ReservedSquare(Team.RED));
        grid.put(new Position(7, 1), new ReservedSquare(Team.RED));

    }

    /**
     * Consulta si es valido mover una {@link Piece} (teniendo en cuenta
     * {@link Team} y tipo) a una <code>Position</code> dada.
     * 
     * @param piece - la pieza a mover
     * @param position - la posición que se quiere consultar
     * @return <tt>true</tt> si es licito moverla ahi; <tt>false</tt> de otro modo
     * @see Square
     * @see ReservedSquare
     */
    public boolean canPlace(Piece piece, Position position) {
    	assertNotNullPosition(position);
        if (!isInBounds(position)) {
            return false;
        }
        Square selected = grid.get(position);
        return selected.canAccomodate(piece);
    }

    /**
     * Consulta si una <code>Position</code> se encuentra dentro de los límites del tablero.
     * @param position la posición sobre la cual se quiere consultar.
     * @return <tt>true</tt> si se encuentra dentro de los límites del tablero; <tt>false</tt> de otro modo
     * @see #assertNotNullPosition(Position)
     */
    public boolean isInBounds(Position position) {
    	assertNotNullPosition(position);
        return grid.containsKey(position);
    }

    /**
     * Retira una pieza de una posición dada.
     * @param position la <code>Position</code> desde la cual se le quiere retirar la pieza
     * @return la <code>Piece</code> retirada
     */
    public Piece withdrawFrom(Position position) {
    	assertOccupiedPosition(position);
        return grid.get(position).withdrawOccupant();
    }

    /**
     * Consulta si una {@link Position} esta vacía, es decir, si hay una <code>Piece</code> o no.
     * @param position la posición sobre la cual se quiere consultar
     * @return <tt>true</tt> si no hay ninguna pieza; <tt>false</tt> en caso contrario.
     */
    public boolean isEmptyPosition(Position position) {
    	assertInBounds(position);
        return grid.get(position).isEmpty();
    }

    /**
     * Consulta que pieza ocupa una posición.
     * @param position la <code>Position</code> desde la cual se quiere obtener la pieza.
     * @return la pieza que ocupa la posición
     */
    public Piece getOccupantIn(Position position) {
    	assertOccupiedPosition(position);
        return grid.get(position).getOccupant();
    }

    /**
     * Coloca una Pieza en una <code>Position</code> dada.
     * @param position la <code>Piece</code> a colocar
     * @param piece la posición donde se quiere colocar la pieza.
     */
    public void placePiece(Position position, Piece piece) {
    	assertEmptyPosition(position);
        grid.get(position).setOccupant(piece);
    }

    /**
     * Genera un Mapa que contiene como <i>key</i> una {@link Position} y <i>value</i> la {@link Piece}
     * que se encuentra en esa posición.
     * @return Map <<code>Position</code>,<code>Piece</code>> con la ubicacion de todas las piezas en el tablero.
     */
    public Map<Position, Piece> getPiecesPosition() {
        Map<Position, Piece> boardConfig = new HashMap<Position, Piece>();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Position pos = new Position(i, j);
                if (isInBounds(pos) && !isEmptyPosition(pos)) {
                    boardConfig.put(pos, getOccupantIn(pos));
                }
            }
        }

        return boardConfig;
    }

    /**
     * Coloca varias piezas en el tablero.
     * @param pieces mapa con las las posiciones de todas las piezas
     * @see #placePiece(Position, Piece)
     */
    public void placePieces(Map<Position, Piece> pieces) {
        for (Position pos : pieces.keySet()) {
            placePiece(pos, pieces.get(pos));
        }
    }

    /**
     * Valida si una <code>Position</code> dada es null.
     * @param position posicion sobre la cual se quiere consultar
     * @throws IllegalArgumentException si la posición es null
     */
    private void assertNotNullPosition(Position position) {
       	if (position == null) {
    		throw new IllegalArgumentException("Null position");
    	}
    }

    /**
     * Valida si una <code>Position</code> esta fuera de los limites del tablero.
     * @param position posición sobre la cual se quiere consultar
     * @throws IllegalArgumentException si la posicion esta fuera de los limites del tablero
     * @see #isInBounds(Position)
     */
    private void assertInBounds(Position position) {
    	if (!isInBounds(position)) {
    		throw new IllegalArgumentException("Not a valid (in-board) position");
    	}
    }

    /**
     * Valida si una <code>Position</code> está ocupada por una <code>Piece</code>
     * @param position posición sobre la cual se quiere consultar.
     * @throws IllegalStateException si la posicion no está libre.
     * @see #assertInBounds(Position)
     * @see #assertNotNullPosition(Position)
     */
    private void assertEmptyPosition(Position position) {
    	assertNotNullPosition(position);
    	assertInBounds(position);
    	if (!isEmptyPosition(position)) {
    		throw new IllegalStateException("The square in " + position + " is not empty");
    	}
    }

    /**
     * Valida si una <code>Position</code> está libre (No tiene ninguna <code>Piece</code>).
     * @param position posicion sobre la cual se quiere consultar.
     * @throws IllegalStateException si la posicion está libre.
     * @see #assertInBounds(Position)
     * @see #assertNotNullPosition(Position)
     * @see #isEmptyPosition(Position)
     */
    private void assertOccupiedPosition(Position position) {
    	assertNotNullPosition(position);
    	assertInBounds(position);
        if (isEmptyPosition(position)) {
            throw new IllegalStateException("The square in " + position + " is empty");
        }
    }
}
