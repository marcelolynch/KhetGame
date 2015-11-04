package poo.khet;

import java.util.HashMap;
import java.util.Map;

import poo.khet.gameutils.Position;

public class Board implements CannonPositions {

    public static final int COLUMNS = 10;
    public static final int ROWS = 8;

    private Map<Position, Square> grid;

    public Board(Map<Position, Piece> pieces) {
        generateBoard();
        placePieces(pieces);
    }

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

        // Casillas Reservadas
        grid.put(new Position(0, 8), new ReservedSquare(Team.SILVER));
        grid.put(new Position(7, 8), new ReservedSquare(Team.SILVER));
        grid.put(new Position(0, 1), new ReservedSquare(Team.RED));
        grid.put(new Position(7, 1), new ReservedSquare(Team.RED));

    }

    /**
     * Consulta si es valido mover una pieza como la que se pasa como parametro (teniendo en cuenta
     * tipo y equipo)
     * 
     * @param piece - la pieza a mover
     * @param position - la posicion que se quiere consultar
     * @return <tt>true</tt> si es licito moverla ahi; <tt>false</tt> de otro modo
     */
    public boolean canPlace(Piece piece, Position position) {
        if (!isInBounds(position)) {
            return false;
        }
        Square selected = grid.get(position);
        return selected.canAccomodate(piece);
    }

    public boolean isInBounds(Position pos) {
        return grid.containsKey(pos);
    }

    public Piece withdrawFrom(Position position) {
        return grid.get(position).withdrawOccupant();
    }

    public boolean isEmptyPosition(Position position) {
        if (!isInBounds(position)) {
            throw new IllegalArgumentException("Not a valid (in-board) position");
        }
        return grid.get(position).isEmpty();
    }

    public Piece getOccupantIn(Position position) {
        if (isEmptyPosition(position)) {
            throw new IllegalStateException(); // TODO: Excepcion
        }
        return grid.get(position).getOccupant();
    }

    public void placePiece(Position pos, Piece piece) {
        if (isInBounds(pos) && isEmptyPosition(pos)) {
            grid.get(pos).setOccupant(piece);
        } else {
            throw new IllegalArgumentException();
        }
    }

    // Se podría sobrecargar con un método que sea getPiecesPosition(Team) y te devuelve solo las
    // piezas de Team
    // si es que esto ayudaría a la AI, sino parece no servir.
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

    private void placePieces(Map<Position, Piece> pieces) {
        for (Position pos : pieces.keySet()) {
            placePiece(pos, pieces.get(pos));
        }
    }
}
